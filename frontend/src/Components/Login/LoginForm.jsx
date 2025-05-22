import { Button, Checkbox, Container, FormControl, FormControlLabel, TextField, Grid, Link } from '@mui/material'
import { Box } from '@mui/system'
import { Typography } from '@mui/material'
import { api } from '../../api/api.js'


//This function handles the form submission
//It prevents the default form submission behavior and logs the email and password values to the console
const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log(data.get('email'));
    console.log(data.get('password'));
    const user = api.get('/v1/user/authentication/login');
    console.log(user.data);
};

function LoginForm() {
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
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Email Address"
                        name="email"
                        autoComplete="email"
                        autoFocus></TextField>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="password"
                        label="Password"
                        name="password"
                        autoComplete="password"
                        autoFocus></TextField>

                    <FormControlLabel
                        control={<Checkbox value="remember" color="primary" />} label="Remember me" />

                    <Button
                        type='submit'
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >Sign In</Button>

                    <Grid container justifyContent={"space-between"}>
                        <Grid mx={2} >
                            <Link variant="body2">Forgot Password?</Link>
                        </Grid>
                        <Grid mx={2}>
                            <Link variant="body2">Request An Account</Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Container >

    );
}

export default LoginForm;
// This is a simple login form component in React. It includes two input fields for username and password, and a submit button.