import { axiosPrivate } from "../api/axios";
import useAuth from "./useAuth";
import useRefreshToken from "./useRefreshtoken";
import { useEffect } from "react";


const useAxiosPrivate = () => {

    const refresh = useRefreshToken();
    const { auth } = useAuth();

    useEffect(() => {

        const requestIntercept = axiosPrivate.interceptors.request.use(
            config => {
                if (!config.headers['Authorization']) {
                    config.headers['Authorization'] = `Bearer ${auth?.token}`;
                }
                return config;
            }, (error) => Promise.reject(error)

        );

        const responseIntercept = axiosPrivate.interceptors.response.use(
            response => response,
            async (error) => {
                const prevRequest = error?.config;

                if (error?.response?.status === 403 & !prevRequest?.sent) {
                    prevRequest.sent = true;
                    const newAccessToken = await refresh();
                    prevRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;
                    console.log(error);
                    return axiosPrivate(prevRequest);
                }
                return Promise.reject(error);
            }
        );


        return () => {
            axiosPrivate.interceptors.response.eject(responseIntercept);
            axiosPrivate.interceptors.response.eject(requestIntercept);
        }

    }, [auth, refresh]);

    return axiosPrivate;
}
export default useAxiosPrivate;