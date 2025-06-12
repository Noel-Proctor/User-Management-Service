import './App.css'
import { Route, Routes } from 'react-router'
import LoginPage from './components/Login/LoginPage.jsx'
import Shell from './Layout/Shell.jsx'
import { ToastContainer, Slide } from "react-toastify";
import RequireAuth from "./components/RequireAuth.jsx";
import Unauthorised from './components/Unauthorised.jsx';
import { ROLES } from './Util/constants.js'
import Users from './Components/Users.jsx'
import PersistLogin from './components/Login/PersistLogin.jsx'

function App() {


  return (
    <>
      <ToastContainer
        position='top-center'
        autoClose={3000}
        hideProgressBar={true}
        draggable
        pauseOnHover
        theme='colored'
        transition={Slide}
        className='my-toast' />

      <Routes>
        {/* Public Routes */}
        <Route path="/login" element={<LoginPage />} />

        {/* Protected Routes */}

        {/* Main Flow. */}
        <Route element={<PersistLogin />}>
          <Route element={<RequireAuth allowedRoles={[ROLES[1], ROLES[2]]} />}>
            <Route element={<Shell />}>
              <Route path="/" element={<div>This is the home page</div>} />
              <Route path="/applications" element={<div>This is the applications page</div>} />
            </Route>
          </Route>

          {/* Admin Flow */}
          <Route element={<RequireAuth allowedRoles={[ROLES[1]]} />}>
            <Route path="/admin" element={<Shell />}>
              <Route path="MaintainUsers" element={<Users></Users>} />
            </Route>
          </Route>
        </Route>

        <Route path="/unauthorised" element={<Unauthorised></Unauthorised>} />
      </Routes>
    </>
  )
}

export default App
