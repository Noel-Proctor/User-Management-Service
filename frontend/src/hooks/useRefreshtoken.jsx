import useAuth from "../hooks/useAuth";
import axios from "../api/axios";
import { jwtDecode } from "jwt-decode";


function useRefreshToken() {
    const { setAuth } = useAuth();


    const refresh = async () => {
        const response = await axios.get('/auth/refreshToken',
            { withCredentials: true }
        );

        const decoded = jwtDecode(response.data.accessToken);

        setAuth(prev => {
            console.log(JSON.stringify(prev));
            console.log(response.data.accessToken);
            return { ...prev, roles: decoded.roles, token: response.data.accessToken }
        });

        return response.data.accessToken;
    }

    return refresh;


}

export default useRefreshToken;