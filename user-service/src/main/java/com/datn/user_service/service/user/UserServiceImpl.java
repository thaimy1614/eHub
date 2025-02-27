package com.datn.user_service.service.user;

import com.datn.user_service.dto.request.RegisterParentRequest;
import com.datn.user_service.dto.request.RegisterStudentRequest;
import com.datn.user_service.dto.request.RegisterTeacherRequest;
import com.datn.user_service.dto.request.RegisterUser;
import com.datn.user_service.dto.response.CountUserResponse;
import com.datn.user_service.dto.response.UserResponse;
import com.datn.user_service.exception.AppException;
import com.datn.user_service.exception.ErrorCode;
import com.datn.user_service.mapper.ParentMapper;
import com.datn.user_service.mapper.StudentMapper;
import com.datn.user_service.mapper.TeacherMapper;
import com.datn.user_service.mapper.UserMapper;
import com.datn.user_service.model.Parent;
import com.datn.user_service.model.Student;
import com.datn.user_service.model.Teacher;
import com.datn.user_service.model.User;
import com.datn.user_service.repository.ParentRepository;
import com.datn.user_service.repository.StudentRepository;
import com.datn.user_service.repository.TeacherRepository;
import com.datn.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final ParentMapper parentMapper;
    private final UserMapper userMapper;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;


    public Page<UserResponse> searchUsers(String keyword, Pageable pageable) {
        Page<User> users;
        if (keyword.contains("@")) {
            keyword = keyword.replace("@", "");
            users = userRepository.findByAddressContainingIgnoreCase(keyword, pageable);
        } else {
            users = userRepository.findByFullNameContainingIgnoreCase(keyword, pageable);
        }
        return users.map(userMapper::toUserResponse);
    }

    public String getFullName(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)).getFullName();
    }


    public UserResponse getMyInfo(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        if (user instanceof Student) {
            return studentMapper.toStudentResponse((Student) user);
        }

        if (user instanceof Teacher) {
            return teacherMapper.toTeacherResponse((Teacher) user);
        }

        if (user instanceof Parent) {
            return parentMapper.toParentResponse((Parent) user);
        }

        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse updateMyInfo(String userId, RegisterUser request) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        UserResponse updatedUser;

        if (request instanceof RegisterStudentRequest studentRequest) {
            updatedUser = updateStudent(studentRequest);

        } else if (request instanceof RegisterTeacherRequest teacherRequest) {
            updatedUser = updateTeacher(teacherRequest);

        } else if (request instanceof RegisterParentRequest parentRequest) {
            updatedUser = updateParent(parentRequest);

        } else {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        return updatedUser;
    }


    private UserResponse updateStudent(RegisterStudentRequest studentRequest) {
        Student student = studentMapper.toStudent(studentRequest);
        Student savedStudent = studentRepository.save(student);
        return userMapper.toUserResponse(savedStudent);
    }

    private UserResponse updateTeacher(RegisterTeacherRequest teacherRequest) {
        Teacher teacher = teacherMapper.toTeacher(teacherRequest);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return userMapper.toUserResponse(savedTeacher);
    }

    private UserResponse updateParent(RegisterParentRequest parentRequest) {
        Parent parent = parentMapper.toParent(parentRequest);
        Parent savedParent = parentRepository.save(parent);
        return userMapper.toUserResponse(savedParent);
    }

    public CountUserResponse countUsers() {
        Long numberOfStudents = studentRepository.count();
        Long numberOfTeachers = teacherRepository.count();
        Long numberOfParents = parentRepository.count();
        return CountUserResponse.builder()
                .numberOfStudents(numberOfStudents)
                .numberOfTeachers(numberOfTeachers)
                .numberOfParents(numberOfParents)
                .total(numberOfStudents + numberOfTeachers + numberOfParents)
                .build();
    }

}

