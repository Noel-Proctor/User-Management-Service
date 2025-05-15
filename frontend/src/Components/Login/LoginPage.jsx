import { Box } from "@mui/material";
import LoginForm from "./LoginForm";



function LoginPage() {
    return (

        <Box
            sx={(theme) => ({
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                height: "100vh",
                width: "100vw",
                backgroundColor: theme.palette.grey[200],

            })}>
            <LoginForm></LoginForm>
        </Box>



    );
}

export default LoginPage;
// This is a simple login page component in React. It includes a title and a paragraph prompting the user to enter their credentials to log in.