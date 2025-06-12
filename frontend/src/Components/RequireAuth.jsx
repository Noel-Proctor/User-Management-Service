import { useLocation, Navigate, Outlet } from "react-router";
import useAuth from "../hooks/useAuth";


const RequireAuth = ({ allowedRoles }) => {

    const { auth } = useAuth();
    const location = useLocation();

    // I also want to check first of all that the access token is valid. The access token should have a 15 minute expiry, and is refreshed every time
    // the user makes a request to the server. If the access token is invalid, it means that the user has likely left their computer, so we should
    //  log them out and force them to reauthenticate. 

    return (
        auth?.roles?.find(role => allowedRoles?.includes(role))
            ? <Outlet> </Outlet>
            : auth?.username
                ? <Navigate to="/unauthorised" state={{ from: location }} replace />
                : <Navigate to="/login" state={{ from: location }} replace />
    );

}

export default RequireAuth