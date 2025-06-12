import axios from "axios";

export default axios.create({
  baseURL: `${import.meta.env.VITE_USER_MANAGEMENT_BACKEND}`,
});

export const axiosPrivate = axios.create({
  baseURL: `${import.meta.env.VITE_USER_MANAGEMENT_BACKEND}`,
  headers: { "Content-Type": "application/json" },
  withCredentials: true,
});
