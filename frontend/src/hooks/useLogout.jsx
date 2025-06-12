import axios from "../api/axios";
import useAuth from "./useAuth";


const useLogout = () => {
    const { doLogout } = useAuth();

    const logout = async () => {
        doLogout();
        try {
            const response = await axios.post("/auth/logout", {},
                {
                    withCredentials: true
                }
            );
        } catch (err) {
            console.log(err);
        }
    };

    return logout;
}

export default useLogout;