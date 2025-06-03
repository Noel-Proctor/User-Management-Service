import { createContext, useState, useMemo } from "react";

import { jwtDecode } from "jwt-decode";

const AuthContext = createContext({});


export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState({});

  function doLogin(token) {

    try {

      console.log(token);
      const decoded = jwtDecode(token)
      console.log(decoded);
      setAuth({
        username: decoded.sub,
        roles: decoded.roles || [],
        exp: decoded.exp,
        token: token
      });

    }
    catch (e) {
      console.log("Invalid Token", e);
      doLogout();


    }
  }

  function doLogout() {
    setAuth(null);
  }


  const value = useMemo(
    () => ({
      auth,
      doLogin,
      doLogout

    }), [auth]
  );



  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
