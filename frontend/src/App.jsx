import './App.css'
import { Route, Routes, useNavigate } from 'react-router'

import AdminLayout from './Components/AdminLayout.jsx'
import LoginPage from './Components/Login/LoginPage.jsx'
import Shell from './Layout/Shell.jsx'

import { createContext, useState, useContext } from 'react'

function App() {

  return (
    <>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route element={<Shell />}>
          <Route path="/home" element={<div>This is the home page</div>} />
          <Route path="/about" element={<div>This is the about page</div>} />
          <Route path="/contact" element={<div>This is the contact page</div>} />
        </Route>
        <Route path="/admin" element={<AdminLayout />}>
          <Route path="MaintainUsers" element={<div>This is where an admin will add and remove user managers</div>} />
        </Route>
      </Routes>
    </>
  )
}

export default App
