import './App.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import RegisterPage from './pages/register-page/RegisterPage'
import LoginPage from './pages/login-page/LoginPage'

const router = createBrowserRouter([
  {
    path: '/login',
    element: <LoginPage />
  },
  {
    path: '/register',
    element: <RegisterPage />
  },
  {
    path: '/',
    element: <LoginPage />
  }
])

function App() {

  return <RouterProvider router={router}/>
}

export default App
