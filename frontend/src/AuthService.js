import api from "/api/api.js";

class AuthService {
  login(credentials) {
    return api.post("/login", credentials);
  }
}

export default new AuthService();
