import { Button, Checkbox, Container, FormControl, FormControlLabel, TextField, Grid, Link } from '@mui/material'
import { Box } from '@mui/system'
import { Typography } from '@mui/material'
import { api } from '../../api/api.js'
import { useContext, useEffect, useState, useRef } from 'react'
import { toast } from 'react-toastify';
import AuthContext from '../../context/AuthProvider.jsx';
import qs from "qs";
import { useNavigate } from 'react-router'




function LoginForm() {

    const { doLogin } = useContext(AuthContext);
    const userRef = useRef();
    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [isUsernameValid, setIsUsernameValid] = useState(true);
    const [usernameValidationMessage, setUsernameValidationMessage] = useState('');
    const [isPasswordValid, setIsPasswordValid] = useState(true);
    const [passwordValidationMessage, setPasswordValidationMessage] = useState('');


    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setUsernameValidationMessage('');
        setIsUsernameValid(true);
    }, [])

    useEffect(() => {
        setIsPasswordValid(true);
        setPasswordValidationMessage('');
    }, [])

    function validateForm() {

        let valid = true;
        if (!username || !password) {
            if (!username) {
                setIsUsernameValid(false);
                setUsernameValidationMessage('Username cannot be empty');
            } else {
                setIsUsernameValid(true);
                setUsernameValidationMessage('');
            }
            if (!password) {
                setIsPasswordValid(false);
                setPasswordValidationMessage('Password cannot be empty');
            } else {
                setIsPasswordValid(true);
                setPasswordValidationMessage("");
            }
            valid = false;
        } else {
            setIsUsernameValid(true);
            setUsernameValidationMessage('');
            setIsPasswordValid(true);
            setPasswordValidationMessage("");
        }
        return valid;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateForm()) {
            toast.error('Please Check Form');
            return;
        }

        try {

            let body = qs.stringify({ username, password: password })

            const response = await api.post(`${import.meta.env.VITE_LOGIN_URL}`,
                body,
                {
                    headers: { "Content-Type": 'application/x-www-form-urlencoded' },
                    withCredentials: true
                }

            );

            console.log(response.data);
            toast.success("Well come on insideee.");
            const accessToken = response?.data?.accessToken;
            doLogin(accessToken);

            // const roles = response?.data?.roles;
            // setAuth({ username, roles: "N/A", accessToken });
            // setUsername('');
            // setPassword('');
            // navigate("/home");
        } catch (err) {
            console.log("Error Flow " + err.response);
            console.log(err)
            if (err.code === "ERR_NETWORK") {
                toast.error("Network Error");

            } else {
                if (err.response.status === 401) {
                    toast.error("Invalid Login");
                } else {
                    toast.error("Opps. Something went wrong.");
                }

            }

        }
    }

    const rememberMeOnChange = () => {
        toast.info("Come back later and this might do something");
    }


    const forgotPasswordOnClick = () => {
        toast.info("Tough Shit.");
    }

    const requestAccountOnClick = () => {
        toast.info("Not tonight mate");
    }

    return (

        < Container disableGutters>
            <Box sx={(theme) => ({
                boxShadow: '0px 4px 10px rgba(0,0,0,0.2)',
                borderRadius: 2,
                px: 4,
                py: 6,
                display: "inline-flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                width: "100%",
                maxWidth: 500,
                backgroundColor: theme.palette.background.paper,
            })}
            >

                <Typography variant="h5" component="h1" gutterBottom sx={{
                    color: 'palette.text.primary'
                }}
                > Sign In </Typography>


                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                    <TextField
                        error={!isUsernameValid}
                        margin="normal"
                        required
                        fullWidth
                        inputRef={userRef}
                        id="username"
                        label="Username"
                        name="username"
                        autoComplete="username"
                        value={username}
                        helperText={usernameValidationMessage}
                        type="text"
                        onChange={(e) => setUsername(e.target.value)}
                        autoFocus
                    ></TextField>
                    <TextField
                        error={!isPasswordValid}
                        helperText={passwordValidationMessage}
                        margin="normal"
                        required
                        fullWidth
                        id="password"
                        label="Password"
                        name="password"
                        value={password}
                        type="password"
                        autoComplete="current=password"
                        onChange={(e) => setPassword(e.target.value)}></TextField>

                    <FormControlLabel
                        control={<Checkbox value="remember" color="primary" />
                        } label="Remember me"
                        onChange={rememberMeOnChange} />

                    <Button
                        type='submit'
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >Sign In</Button>

                    <Grid container justifyContent={"space-between"}>
                        <Grid mx={2} >
                            <Link variant="body2"
                                onClick={forgotPasswordOnClick}>Forgot Password?</Link>
                        </Grid>
                        <Grid mx={2}>
                            <Link variant="body2"
                                onClick={requestAccountOnClick}>Request An Account</Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Container >

    );
}


export default LoginForm
