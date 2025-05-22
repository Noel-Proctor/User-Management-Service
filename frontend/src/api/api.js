import axios from "axios";

export const api = axios.create({
  baseURL: `${import.meta.env.VITE_USER_MANAGEMENT_BACKEND}/api`,
});
