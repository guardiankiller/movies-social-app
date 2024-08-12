import { ActuatorInfo, AuthResponse, HttpError, RegisterForm, UserInfo, ViolationResponse } from "./models";

import axios, { AxiosError } from "axios";

export async function getServerInfo(): Promise<ActuatorInfo> {
  
    const response = await axios.get<ActuatorInfo>('/actuator/info');

    if(response.status !== 200) {
        console.log(response)
        throw new Error(`Server returned: (${response.status}) ${response.statusText}`)
    } 
    return response.data
}


export async function validateRegister(form: RegisterForm) {
    try {
      const response = await axios.post<ViolationResponse>('/api/users', form, { params: {dryRun: true}})
    console.log(response)
    if(response.status === 200) {
      return null;
    } else {
      return response.data
    }
    } catch (e: any) {
      if(e instanceof AxiosError) {
        if(e.response?.status === 400) {
          return e.response.data
        }
      }
    }
}

export async function submitRegister(form: RegisterForm) {
    try {
      await axios.post('/api/users', form);
      return null
    } catch(e) {
      const ex = e as AxiosError
      if(ex.response?.status === 400) {
        return ex.response.statusText
      }
    }
  }

  export async function generateToken(username: string, password: string): Promise<AuthResponse> {
    try {
      const resp = await axios.post<AuthResponse>('/api/auth', {username, password});
      return resp.data
    } catch(e) {
      const ex = e as AxiosError
      throw new Error(ex.response?.statusText || "Invalid credentials")
    }
  }

  export async function getUserByUsername(username: string): Promise<UserInfo> {
    try {
      const response = await axios.get<UserInfo>(`/api/users/${username}`)
      return response.data
    } catch(error) {
      const ex = error as AxiosError
      if(ex.response) {
        if(ex.response.status === 404) {
          throw new HttpError(404, ex.response.statusText || "Not Found", "User is not found")
        }
        const e = ex.response?.data as any
        throw new HttpError(ex.response.status, 
          ex.response.statusText || "Server Exception", 
          e.message || "Error")
      }
      throw new HttpError(500, "Sever error", "Cannot retrive response from server")
    }
  }