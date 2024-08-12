import './App.css'
import { createBrowserRouter, Outlet, RouterProvider } from 'react-router-dom'
import RegisterPage from './pages/register-page/RegisterPage'
import LoginPage from './pages/login-page/LoginPage'
import { AuthProvider } from './utils/AuthProvider'
import { UserPage } from './pages/user-page/UserPage'
import { Snackbar } from './utils/Snackbar'

const router = createBrowserRouter([
  {
    path: "/",
    element: <Main />,
    children: [
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
      },
      {
        path: '/users/:username',
        element: <UserPage />
      }
    ]
  }
])

function Main() {
  return (
      <Snackbar>
        <AuthProvider>
          <Outlet/>
        </AuthProvider>
      </Snackbar>
  )
}

function App() {
  return <RouterProvider router={router} />
}

export default App
