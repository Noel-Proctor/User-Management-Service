import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { BrowserRouter, Routes, Route } from 'react-router'
import { ColorModeProvider } from './Theme/ColourModeProvider.jsx'
import { AuthProvider } from './context/AuthProvider.jsx'



createRoot(document.getElementById('root')).render(

  // <StrictMode>
  <BrowserRouter>
    <AuthProvider>
      <ColorModeProvider>
        <Routes>
          <Route path="/*" element={<App />}></Route>
        </Routes>
      </ColorModeProvider>
    </AuthProvider>


  </BrowserRouter>


  // </StrictMode>
)
