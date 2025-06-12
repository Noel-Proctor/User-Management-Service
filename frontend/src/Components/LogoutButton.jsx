import LogoutIcon from '@mui/icons-material/Logout';
import IconButton from '@mui/material/IconButton';
import useLogout from '../hooks/useLogout';


function LogoutButton() {

    const logout = useLogout();
    return (
        <>

            <IconButton color='primary' onClick={logout}>
                <LogoutIcon></LogoutIcon>
            </IconButton>
        </>
    )
}

export default LogoutButton;