package com.datn.user_service.service.account;

import com.datn.user_service.dto.kafka.AccountInfo;
import com.datn.user_service.dto.kafka.SendOTP;
import com.datn.user_service.dto.kafka.SendPassword;
import com.datn.user_service.dto.kafka.VerifyAccount;
import com.datn.user_service.dto.request.*;
import com.datn.user_service.dto.response.*;
import com.datn.user_service.exception.AppException;
import com.datn.user_service.exception.ErrorCode;
import com.datn.user_service.httpclient.OutboundIdentityClient;
import com.datn.user_service.httpclient.OutboundUserClient;
import com.datn.user_service.mapper.ParentMapper;
import com.datn.user_service.mapper.StudentMapper;
import com.datn.user_service.mapper.TeacherMapper;
import com.datn.user_service.model.*;
import com.datn.user_service.repository.*;
import com.datn.user_service.service.BridgeService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.Normalizer;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    @NonFinal
    protected final String GRANT_TYPE = "authorization_code";
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final OutboundIdentityClient outboundIdentityClient;
    private final OutboundUserClient outboundUserClient;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final ParentMapper parentMapper;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    private final RoleRepository roleRepository;
    private final BridgeService bridgeService;
    private final ParentStudentRepository parentStudentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UserRepository userRepository;
    @NonFinal
    @Value("${outbound.identity.client-id}")
    protected String CLIENT_ID;
    @NonFinal
    @Value("${outbound.identity.client-secret}")
    protected String CLIENT_SECRET;
    @NonFinal
    @Value("${outbound.identity.redirect-uri}")
    protected String REDIRECT_URI;
    @Value("${application.api.url}")
    private String apiUrl;
    @Value("${jwt.signer-key}")
    private String KEY;
    //    @Value("${jwt.io-stream-key}")
//    private String IO_STREAM_KEY;
//    @Value("${jwt.io-stream-secret}")
//    private String IO_STREAM_SECRET;
    @Value("${jwt.expiration-duration}")
    private long EXPIRATION_DURATION;
    @Value("${jwt.refreshable-duration}")
    private String REFRESHABLE_DURATION;
    @Value("${application.verify-redirect}")
    private String VERIFY_EMAIL_REDIRECT;

    public LoginResponse authenticate(LoginRequest request) throws JOSEException, MalformedURLException {
        String username = request.getUsername();
        String password = request.getPassword();

        Account authUser = accountRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean check = passwordEncoder.matches(password, authUser.getPassword());
        if (!check) {
            throw new AppException(ErrorCode.USERNAME_OR_PASSWORD_INCORRECT);
        }

        User user = userRepository.findById(authUser.getUserId()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (authUser.getStatus().equals(Account.Status.UNVERIFIED)) {
            sendVerificationEmail(authUser.getEmail(), user.getFullName());
            throw new AppException(ErrorCode.UnverifiedAccount);
        }

        var token = generateToken(authUser);
//        var ioStreamToken = generateIoStreamToken(authUser);
        Set<String> roles = authUser.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        return LoginResponse.builder()
                .token(token)
                .roles(roles)
                .username(username)
                .userId(authUser.getUserId())
//                .ioStreamToken(ioStreamToken)
                .build();
    }

//    public String generateIoStreamToken(User user) throws MalformedURLException {
//        Client client = Client.builder(
//                IO_STREAM_KEY,
//                IO_STREAM_SECRET
//        ).build();
//
//        Token token = client.frontendToken(user.getUserId());
//        return token.toString();
//    }

    private String generateToken(Account account) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet
                .Builder()
                .subject(account.getUserId())
                .issuer("Thaidq")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(EXPIRATION_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(new MACSigner(KEY));
        return jwsObject.serialize();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = (isRefresh) ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(Long.parseLong(REFRESHABLE_DURATION), ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (isTokenInBlacklist(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    public IntrospectResponse introspect(String token) {
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (Exception e) {
            isValid = false;
        }
        return IntrospectResponse.builder().valid(isValid).build();
    }

    private boolean isTokenInBlacklist(String jwtId) {
        return redisTemplate.opsForValue().get("bl-" + jwtId) != null;
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException, MalformedURLException {
        var signedJWT = verifyToken(request.getToken(), true);
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        addTokenToBlacklist(jit, expiryTime);

        var userId = signedJWT.getJWTClaimsSet().getSubject();
        var user = accountRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return RefreshTokenResponse.builder()
//                .ioStreamToken(generateIoStreamToken(user))
                .token(generateToken(user))
                .build();
    }

    @Transactional
    public RegisterResponse registerOne(RegisterUser request) {
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        String password = generateUniqueSuffix(8);
        String username = generateUsername(request.getFullName());
        log.info("username {}", username);
        log.info("pass {}", password);

        Account account = createAccount(request.getEmail(), username, password);

        if (request instanceof RegisterStudentRequest studentRequest) {
            processUserRegistration(
                    account,
                    "STUDENT",
                    () -> saveStudentAccount(account, studentRequest)
            );
        } else if (request instanceof RegisterTeacherRequest teacherRequest) {
            processUserRegistration(
                    account,
                    "TEACHER",
                    () -> saveTeacherAccount(account, teacherRequest)
            );
        } else if (request instanceof RegisterParentRequest parentRequest) {
            processUserRegistration(
                    account,
                    "PARENT",
                    () -> saveParentAccount(account, parentRequest)
            );
        }

        sendAccountInfo(request.getEmail(), request.getFullName(), username, password);

        sendVerificationEmail(request.getEmail(), request.getFullName());

        return RegisterResponse.builder()
                .success(true)
                .failedResults(null)
                .build();
    }

    private Account createAccount(String email, String username, String password) {
        return Account.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .status(Account.Status.UNVERIFIED)
                .build();
    }

    private void sendVerificationEmail(String email, String fullName) {
        redisTemplate.delete("verification-"+email);
        String token = UUID.randomUUID().toString();
        // Have 5 minutes to verify account
        redisTemplate.opsForValue().set("verification-"+email, token, 5, TimeUnit.MINUTES);

        String uriString = apiUrl + "/identity/verify?email=" + email + "&token=" + token;

        kafkaTemplate.send(
                "verification",
                VerifyAccount.builder()
                        .email(email)
                        .fullName(fullName)
                        .url(uriString)
                        .build()
        );
        log.info("Send verification email");
    }

    private void sendAccountInfo(String email, String fullName, String username, String password) {
        kafkaTemplate.send(
                "sendAccountInfo",
                AccountInfo.builder()
                        .email(email)
                        .fullName(fullName)
                        .username(username)
                        .password(password)
                        .build()
        );
    }

    public String verifyAccount(String email, String token) {
        // Verify account status
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.VerificationLinkCorrupted));
        switch (account.getStatus()) {
            case BLOCKED -> throw new AppException(ErrorCode.AccountIsBlocked);
            case ACTIVATED -> throw new AppException(ErrorCode.AccountAlreadyVerified);
        }

        String storedToken = redisTemplate.opsForValue().get("verification-"+email);
        if (storedToken == null) {
            sendVerificationEmail(email, "User");
            throw new AppException(ErrorCode.VerificationLinkExpired);
        }

        if (!token.equals(storedToken)) {
            throw new AppException(ErrorCode.VerificationLinkCorrupted);
        }

        account.setStatus(Account.Status.ACTIVATED);
        accountRepository.save(account);
        redisTemplate.delete("verification-"+email);
        return VERIFY_EMAIL_REDIRECT;
    }

    public String generateUsername(String fullName) {
        String normalizedFullName = removeDiacritics(fullName.toLowerCase().trim());

        String[] nameParts = normalizedFullName.split("\\s+");
        if (nameParts.length == 0) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }

        StringBuilder username = new StringBuilder();

        username.append(nameParts[nameParts.length - 1]);

        for (int i = 0; i < nameParts.length - 1; i++) {
            username.append(nameParts[i].charAt(0));
        }

        String uniqueSuffix = generateUniqueSuffix(3);

        return username.append(uniqueSuffix).toString();
    }

    private String generateUniqueSuffix(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder suffix = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            suffix.append(characters.charAt(index));
        }

        return suffix.toString();
    }

    private String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replace("đ", "d")
                .replaceAll("[^a-z\\s]", "")
                .replaceAll("\\s+", " ");
    }

    private void processUserRegistration(Account account, String roleId, Runnable specificSaveLogic) {
        Set<Role> roles = Set.of(roleRepository.findById(roleId).orElseThrow());
        account.setRoles(roles);

        accountRepository.save(account);

        specificSaveLogic.run();
    }

    @Transactional
    public void saveStudentAccount(Account account, RegisterStudentRequest studentRequest) {
        Student student = studentMapper.toStudent(studentRequest);
        student.setUserId(account.getUserId());
        studentRepository.save(student);
    }

    @Transactional
    public void saveTeacherAccount(Account account, RegisterTeacherRequest teacherRequest) {
        Teacher teacher = teacherMapper.toTeacher(teacherRequest);
        teacher.setUserId(account.getUserId());
        teacherRepository.save(teacher);
    }

    @Transactional
    public void saveParentAccount(Account account, RegisterParentRequest parentRequest) {
        Parent parent = parentMapper.toParent(parentRequest);
        parent.setUserId(account.getUserId());
        parentRepository.save(parent);
        Student student = studentRepository.findByEmail(parentRequest.getStudentEmail())
                .orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_EXISTED)
                );
        ParentStudent parentStudent = ParentStudent.builder()
                .student(student)
                .parent(parent)
                .parentType(parentRequest.getParentType())
                .build();
        parentStudentRepository.save(parentStudent);
    }

    public RegisterResponse registerBatch(MultipartFile file, String type) {
        List<Integer> failedRows = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                try {
                    RegisterUser request = parseRowToRegisterUser(row, type, sheet);

                    bridgeService.callRegisterUser(request);
                } catch (Exception e) {
                    failedRows.add(i);
                }
            }

            workbook.close();
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_ERROR);
        }

        return RegisterResponse.builder()
                .success(failedRows.isEmpty())
                .failedResults(failedRows)
                .build();
    }

    private RegisterUser parseRowToRegisterUser(Row row, String type, XSSFSheet sheet) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fullName = getCellValue(row, 1);
        String email = getCellValue(row, 2);
        String phoneNumber = getCellValue(row, 3);
        String address = getCellValue(row, 4);
        LocalDate dob = null;
        try {
            dob = LocalDate.parse(getCellValue(row, 5), dateFormatter);
        } catch (DateTimeParseException e) {
            log.error("Invalid date format in row {}: {}", row.getRowNum() + 1, getCellValue(row, 5));
            throw new AppException(ErrorCode.INVALID_DATE_FORMAT);
        }
        User.Gender gender = User.Gender.OTHER;
        if (getCellValue(row, 6).equalsIgnoreCase("nam")) {
            gender = User.Gender.MALE;
        } else if (getCellValue(row, 6).equalsIgnoreCase("nữ")) {
            gender = User.Gender.FEMALE;
        }
        String image = "";
        XSSFDrawing drawing = sheet.createDrawingPatriarch();

        List<XSSFPicture> pictures = drawing.getShapes().stream()
                .filter(shape -> shape instanceof XSSFPicture)
                .map(shape -> (XSSFPicture) shape)
                .toList();

        for (XSSFPicture picture : pictures) {
            XSSFClientAnchor anchor = picture.getClientAnchor();
            int row1 = anchor.getRow1();
            if (row1 == row.getRowNum()) {
                XSSFPictureData pictureData = picture.getPictureData();
                String ext = pictureData.suggestFileExtension();
                byte[] data = pictureData.getData();
                log.info("image data size: {}", data.length);
                log.info("image name: {}", picture.getShapeName());
                image = "avatar" + picture.getShapeName();
            }
        }

        return switch (type.toUpperCase()) {
            case "STUDENT" -> RegisterStudentRequest.builder()
                    .fullName(fullName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .address(address)
                    .gender(gender)
                    .dateOfBirth(dob)
                    .role("student")
                    .build();
            case "TEACHER" -> {
                String specialization = getCellValue(row, 7);
                yield RegisterTeacherRequest.builder()
                        .fullName(fullName)
                        .email(email)
                        .phoneNumber(phoneNumber)
                        .address(address)
                        .gender(gender)
                        .dateOfBirth(dob)
                        .role("teacher")
                        .specialization(specialization)
                        .build();
            }
            case "PARENT" -> {
                String studentEmail = getCellValue(row, 7);
                ParentStudent.ParentType parentType = ParentStudent.ParentType.valueOf(getCellValue(row, 8));
                yield RegisterParentRequest.builder()
                        .fullName(fullName)
                        .email(email)
                        .phoneNumber(phoneNumber)
                        .address(address)
                        .gender(gender)
                        .dateOfBirth(dob)
                        .role("parent")
                        .parentType(parentType)
                        .isNotificationOn(false)
                        .studentEmail(studentEmail)
                        .build();
            }
            default -> throw new IllegalArgumentException("Invalid user type: " + type);
        };
    }

    private String getCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        return cell != null ? cell.toString().trim() : "";
    }


    private void addTokenToBlacklist(String jwtId, Date expiryTime) {
        redisTemplate.opsForValue().set("bl-" + jwtId, jwtId);
        redisTemplate.expireAt("bl-" + jwtId, expiryTime);
    }

    public void logout(String token) throws Exception {
        var signedJWT = verifyToken(token, true);
        String jId = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        addTokenToBlacklist(jId, expiryTime);
    }

    public LoginResponse outboundAuthenticate(String code) throws JOSEException, MalformedURLException {
        var response = outboundIdentityClient.exchangeToken(ExchangeTokenRequest.builder()
                .code(code)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .grantType(GRANT_TYPE)
                .build());
        log.info("TOKEN RESPONSE {}", response);

        var userInfo = outboundUserClient.getUserInfo("json", response.getAccessToken());

        log.info("User Info {}", userInfo);

        var user = accountRepository.findByEmail(userInfo.getEmail()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        var token = generateToken(user);
//        var ioStreamToken = generateIoStreamToken(user);
        return LoginResponse.builder()
                .userId(user.getUserId())
                .token(token)
                .username(user.getUsername())
//                .ioStreamToken(ioStreamToken)
                .build();
    }

    public SendOTPResponse sendOTPForForgetPassword(String email) {
        if (!accountRepository.existsByEmail(email)) {
            return SendOTPResponse.builder().isSent(false).build();
        }
        User user = userRepository.findByEmail(email);
        String OTP = generate();
        redisTemplate.opsForValue().set("otp-"+email, OTP, 3, TimeUnit.MINUTES);
        SendOTP sendOtp = SendOTP.builder().email(email).otp(OTP).name(user.getFullName()).build();
        kafkaTemplate.send("sendOTP", sendOtp);
        return SendOTPResponse.builder().isSent(true).build();
    }

    public CheckOTPResponse checkOTP(String otp, String email) {
        if (otp.equals(redisTemplate.opsForValue().get("otp-"+email))) {
            redisTemplate.delete("otp-"+email);
            String newPassword = generateUniqueSuffix(8);
            var account = accountRepository.findByEmail(email).orElseThrow();
            account.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(account);
            User user = userRepository.findByEmail(email);
            SendPassword sendPassword = SendPassword.builder().email(email).username(account.getUsername()).password(newPassword).name(user.getFullName()).build();
            kafkaTemplate.send("sendNewPassword", sendPassword);
            return CheckOTPResponse.builder().isValid(true).build();
        } else {
            return CheckOTPResponse.builder().isValid(false).build();
        }
    }

    private String generate() {
        int OTP = new Random().nextInt(900000) + 100000;
        return String.valueOf(OTP);
    }
}
