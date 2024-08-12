import React, { ReactNode, useContext, useEffect, useState } from "react";
import { AuthResponse } from "./models";
import { generateToken } from "./api-functions";
import { useNavigate } from "react-router-dom";
import { useSnackbar } from "./Snackbar";
import axios, { AxiosError } from "axios";

export interface AuthContext {
    login: (username: string, password: string) => void
    logout: () => void
    isLoggedIn: () => boolean
}

const Ctx = React.createContext({} as AuthContext)
export function useAuth() { return useContext(Ctx); }
export function useAuthGuard() {
    const auth = useAuth()
    const nav = useNavigate()
    useEffect(() => {
        console.log(auth.isLoggedIn())
        if(!auth.isLoggedIn()) {
            nav('/login')
        }
    }, [])
}

export type Props = {children: ReactNode}
export function AuthProvider({ children }: Props) {

    axios.interceptors.request.use(function (config) {
        if(response) {
            config.headers.setAuthorization(`${response.type} ${response.accessToken}`)
        }
        return config;
      }, function (error) {
        // Do something with request error
        return Promise.reject(error);
      })

      axios.interceptors.response.use(function (response) {
        // Any status code that lie within the range of 2xx cause this function to trigger
        // Do something with response data
        return response;
      }, function (error) {
        const ex = error as AxiosError
        if(ex.response?.status === 401) {
            logout()
        }
        // Any status codes that falls outside the range of 2xx cause this function to trigger
        // Do something with response error
        return Promise.reject(error);
      });

    function getAuthFromLocalStorage() {
        const raw = localStorage.getItem('auth')
        if (raw) {
            const resp = JSON.parse(raw) as AuthResponse
            if(new Date().getTime() + 15000 >= new Date(resp.expirationTime).getTime()) {
                return undefined
            }
            return resp
        }
        return undefined
    }

    const [response, setAuth] = useState<AuthResponse | undefined>(getAuthFromLocalStorage())
    const snackbar = useSnackbar()
    const nav = useNavigate()

    async function login(username: string, password: string) {
        try {
            const response = await generateToken(username, password)
            setAuth(response)
            localStorage.setItem('auth', JSON.stringify(response))
            snackbar.show("Successful login")
            setTimeout(() => nav(`/users/${username}`))
        } catch(e) {
            const ex = e as Error
            snackbar.showError(ex.message)
        }
    }

    async function logout() {
        setAuth(undefined)
        localStorage.removeItem('auth')
        nav(`/login`)
    }

    function isLoggedIn() {
        return !!response
    }

    const value: AuthContext = {
        login, 
        logout, 
        isLoggedIn
    }
    
    return (
        <Ctx.Provider value={value}>
            {children}
        </Ctx.Provider>
    )
}


