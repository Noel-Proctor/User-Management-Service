import { createContext, useState, useMemo, useCallback } from "react";
import { jwtDecode } from "jwt-decode";
import { useNavigate, useLocation } from 'react-router';

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState({});
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/";

  const doLogin = useCallback((token) => {
    console.log(from);
    try {
      // console.log(token);
      const decoded = jwtDecode(token)
      // console.log(decoded);
      setAuth({
        username: decoded.sub,
        roles: decoded.roles || [],
        exp: decoded.exp,
        token: token
      });
      navigate(from, { replace: true })
    }

    catch (e) {
      console.log("Invalid Token", e);
      doLogout();
    }
  }, [navigate]);


  const doLogout = useCallback(() => {
    setAuth(null);
    navigate("/login", { replace: true });
  }, [navigate]);


  const hasRole = useCallback((role) => {
    return auth.roles.includes(role)

  }, [auth]);

  const value = useMemo(
    () => ({
      auth,
      setAuth,
      doLogin,
      doLogout,
      hasRole

    }), [auth, from]
  );



  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
