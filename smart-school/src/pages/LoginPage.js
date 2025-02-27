import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import {
    Button,
    Grid,
    Box,
    Typography,
    Paper,
    Checkbox,
    FormControlLabel,
    TextField,
    CssBaseline,
    IconButton,
    InputAdornment,
    CircularProgress,
    Backdrop,
} from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Visibility, VisibilityOff, Google } from '@mui/icons-material';

import bgpic from '../assets/School.jpg';
import { LightOrangeButton } from '../components/buttonStyles';
import styled from 'styled-components';
import { loginUser } from '../redux/userRelated/userHandle';
import Popup from '../components/Popup';
import ForgetPasswordModal from './FormModal';
import { ChakraProvider } from '@chakra-ui/react';
import FormModal from './FormModal';

const theme = createTheme({
    palette: {
        primary: {
            main: '#ff7f50', // Cam tươi
        },
        secondary: {
            main: '#ffa07a', // Cam nhạt
        },
    },
    typography: {
        fontFamily: '"Poppins", sans-serif',
    },
});

const LoginPage = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { status, currentUser, response, currentRole } = useSelector((state) => state.user);

    const [toggle, setToggle] = useState(false);
    const [guestLoader, setGuestLoader] = useState(false);
    const [loader, setLoader] = useState(false);
    const [showPopup, setShowPopup] = useState(false);
    const [message, setMessage] = useState("");

    const [emailError, setEmailError] = useState(false);
    const [passwordError, setPasswordError] = useState(false);
    const [rollNumberError, setRollNumberError] = useState(false);
    const [studentNameError, setStudentNameError] = useState(false);

    const [isModalOpen, setModalOpen] = useState(false);


    const [isOtpModalOpen, setIsOtpModalOpen] = useState(false);

    const getOtpFields = {
        fields1: [
            {
                name: "email",
                label: "Email",
                type: "email",
                placeholder: "Enter your email",
            }
        ],
        submitText: "Get OTP",
    };

    const handleGetOtp = async (data) => {
        console.log("Form Data:", data);
    };

    const handleSubmit = (event) => {
        event.preventDefault();

    };

    useEffect(() => {
        if (status === 'success' && currentUser) {
            navigate(`/${currentRole}/dashboard`);
        } else if (status === 'failed') {
            setMessage(response);
            setShowPopup(true);
            setLoader(false);
            setGuestLoader(false);
        }
    }, [status, currentRole, navigate, response, currentUser]);

    return (
        <ThemeProvider theme={theme}>
            <Grid container component="main" sx={{ height: '100vh' }}>
                <CssBaseline />
                <Grid item xs={12} sm={6} md={6} component={Paper} elevation={6} square>
                    <Box
                        sx={{
                            my: 8,
                            mx: 4,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                        }}
                    >
                        <Typography variant="h4" sx={{ mb: 2, color: '#ff7f50', fontWeight: 'bold' }}>
                            Login
                        </Typography>
                        <Typography variant="body1" sx={{ color: '#555', mb: 1 }}>
                            Welcome back! Please login to your account.
                        </Typography>
                        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 2 }}>

                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                id="email"
                                label="Username"
                                name="email"
                                error={emailError}
                                helperText={emailError && 'Please enter username'}
                                onChange={() => setEmailError(false)}
                            />

                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type={toggle ? 'text' : 'password'}
                                id="password"
                                error={passwordError}
                                helperText={passwordError && 'Please enter password'}
                                onChange={() => setPasswordError(false)}
                                InputProps={{
                                    endAdornment: (
                                        <InputAdornment position="end">
                                            <IconButton onClick={() => setToggle(!toggle)}>
                                                {toggle ? <Visibility /> : <VisibilityOff />}
                                            </IconButton>
                                        </InputAdornment>
                                    ),
                                }}
                            />
                            <Grid container justifyContent="space-between" sx={{ mt: 1 }}>
                                <FormControlLabel
                                    control={<Checkbox value="remember" color="primary" />}
                                    label="Remember"
                                />
                                <>
                                    <Button onClick={() => setIsOtpModalOpen(true)}>Forget Password?</Button>

                                </>

                            </Grid>
                            <LightOrangeButton type="submit" fullWidth variant="contained" sx={{ mt: 3 }}>
                                {loader ? <CircularProgress size={24} color="inherit" /> : 'Login'}
                            </LightOrangeButton>
                            <Button
                                fullWidth
                                variant="outlined"
                                sx={{ mt: 2, color: 'black', borderColor: '#ff7f50' }}
                                startIcon={<Box
                                    component="img"
                                    src="https://banner2.cleanpng.com/20190731/uqk/kisspng-google-icon-1713874997698.webp"
                                    alt="Google logo"
                                    sx={{ width: 20, height: 20 }}
                                />} // Thêm icon Google ở đầu button
                            >
                                Login with Google
                            </Button>
                        </Box>
                    </Box>
                </Grid>
                <Grid
                    item
                    xs={false}
                    sm={6}
                    md={6}
                    sx={{
                        backgroundImage: `url(${bgpic})`,
                        backgroundRepeat: 'no-repeat',
                        backgroundSize: 'center',
                        backgroundPosition: 'center',
                        height: '100%',
                    }}
                />
            </Grid>
            <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={guestLoader}>
                <CircularProgress color="primary" />
                <Typography variant="body1" sx={{ ml: 2 }}>
                    Vui lòng chờ...
                </Typography>
            </Backdrop>
            <Popup message={message} setShowPopup={setShowPopup} showPopup={showPopup} />
            <ChakraProvider>
                <FormModal
                    title="GET OTP"
                    fields={getOtpFields}
                    isOpen={isOtpModalOpen}
                    onClose={() => setIsOtpModalOpen(false)}
                    onSubmit={handleGetOtp}
                />
            </ChakraProvider>
        </ThemeProvider>
    );
};

export default LoginPage;

const StyledLink = styled(Link)`
  text-decoration: none;
  color: #ff7f50;
  font-weight: 500;
  &:hover {
    text-decoration: underline;
  }
`;
