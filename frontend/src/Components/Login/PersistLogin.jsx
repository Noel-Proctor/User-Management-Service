import { Outlet } from 'react-router';
import { useState, useEffect } from 'react';
import useRefreshToken from '../../hooks/useRefreshtoken';
import useAuth from '../../hooks/useAuth';
import Spinner from '../../Theme/Spinner';
import { Box } from '@mui/material';



const PersistLogin = () => {
    const [isLoading, setIsLoading] = useState(true);
    const refresh = useRefreshToken();

    const { auth } = useAuth();


    useEffect(() => {

        const verifyRefreshToken = async () => {
            try {
                await refresh();
            } catch (err) {
                console.log(err);
            } finally {
                setIsLoading(false);
            }
        }

        !auth?.token ? verifyRefreshToken() : setIsLoading(false);
    }, []);

    useEffect(() => {
        console.log(`isLoading : ${isLoading}`)
        console.log(`"auth: ${JSON.stringify(auth?.token)}`);
    }, [isLoading])



    return (
        <>

            {isLoading ?

                <Box
                    sx={() => ({

                        display: "inline-flex",
                        flexDirection: "column",
                        alignItems: "center",
                        justifyContent: "center",
                    })}>

                    <p> Loading  </p>
                    <Spinner />
                </Box>
                : <Outlet></Outlet>
            }
        </>
    )
}

export default PersistLogin;