import { axios } from "/api/axios.js";

class AuthService {
  login(credentials) {
    return axios.post("/login", credentials);
  }
}

export default new AuthService();
