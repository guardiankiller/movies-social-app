import './App.css'
import { createBrowserRouter, Outlet, RouterProvider } from 'react-router-dom'
import RegisterPage from './pages/RegisterPage'
import LoginPage from './pages/LoginPage'
import { AuthProvider } from './utils/AuthProvider'
import { UserPage } from './pages/UserPage'
import { Snackbar } from './utils/Snackbar'
import { Footer } from './layouts/Footer'
import { Header } from './layouts/Header'
import { AboutUs } from './pages/AboutUs'
import { ContactUs } from './pages/ContectUs'
import { MovieDetails } from './pages/MovieDetails'
import { LanguageProvider } from './utils/LanguageProvider'
import { LoadingPage } from './pages/LoadingPage'
import { FAQ } from './pages/FAQ'

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
        element: <UserPage />
      },
      {
        path: '/about',
        element: <AboutUs />
      },
      {
        path: '/contact',
        element: <ContactUs />
      },
      {
        path: '/faq',
        element: <FAQ />
      },
      {
        path: '/movies/:id',
        element: <MovieDetails />
      }
    ]
  }
])

function Main() {
  return (
      <Snackbar>
        <AuthProvider>
          <LanguageProvider>
            <LoadingPage>
              <Header/>
              <Outlet/>
              <Footer/>
            </LoadingPage>
          </LanguageProvider>
        </AuthProvider>
      </Snackbar>
  )
}

function App() {
  return <RouterProvider router={router} />
}

export default App
