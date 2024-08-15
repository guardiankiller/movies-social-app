import { ActuatorInfo, AuthResponse, Genre, HttpError, Movie, Page, RegisterForm, Settings, UserInfo, ViolationResponse } from "./models";

import axios, { AxiosError } from "axios";

export async function getServerInfo(): Promise<ActuatorInfo> {
  
    const response = await axios.get<ActuatorInfo>('/actuator/info');

    if(response.status !== 200) {
        console.log(response)
        throw new Error(`Server returned: (${response.status}) ${response.statusText}`)
    } 
    return response.data
}


export async function validateRegister(form: RegisterForm): Promise<ViolationResponse | undefined> {
    try {
      const response = await axios.post<ViolationResponse>('/api/users', form, { params: {dryRun: true}})
    if(response.status === 200) {
      return undefined;
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

  export async function getGenres(language: string): Promise<Genre[]> {
    try {
      const response = await axios.get<Genre[]>('/api/genres', {params: {language}})
      return response.data
    } catch(error) {
      const ex = error as AxiosError
      if(ex.response) {
        const e = ex.response?.data as any
        throw new HttpError(ex.response.status, 
          ex.response.statusText || "Server Exception", 
          e.message || "Error")
      }
      throw new HttpError(500, "Sever error", "Cannot retrive response from server")
    }
  }

  export async function getMovies(language: string, page: number): Promise<Page<Movie>> {
    try {
      const response = await axios.get<Page<Movie>>('/api/movies', {params: {language, page}})
      return response.data
    } catch(error) {
      const ex = error as AxiosError
      if(ex.response) {
        const e = ex.response?.data as any
        throw new HttpError(ex.response.status, 
          ex.response.statusText || "Server Exception", 
          e.message || "Error")
      }
      throw new HttpError(500, "Sever error", "Cannot retrive response from server")
    }
  }

  export async function getMoviesByGenre(genreId: number, language: string, page: number): Promise<Page<Movie>> {
    try {
      const response = await axios.get<Page<Movie>>(`/api/genres/${genreId}/movies`, {params: {language, page}})
      console.log(response)
      return response.data
    } catch(error) {
      const ex = error as AxiosError
      if(ex.response) {
        const e = ex.response?.data as any
        throw new HttpError(ex.response.status, 
          ex.response.statusText || "Server Exception", 
          e.message || "Error")
      }
      throw new HttpError(500, "Sever error", "Cannot retrive response from server")
    }
  }

  export async function getMovie(id: number, language: string): Promise<Movie> {
    try {
      const response = await axios.get<Movie>('/api/movies/'+id, {params: {language}})
      return response.data
    } catch(error) {
      const ex = error as AxiosError
      if(ex.response) {
        const e = ex.response?.data as any
        throw new HttpError(ex.response.status, 
          ex.response.statusText || "Server Exception", 
          e.message || "Error")
      }
      throw new HttpError(500, "Sever error", "Cannot retrive response from server")
    }
  }

  export async function getGenre(id: number, language: string): Promise<Genre> {
    try {
      const response = await axios.get<Genre>('/api/genres/'+id, {params: {language}})
      return response.data
    } catch(error) {
      const ex = error as AxiosError
      if(ex.response) {
        const e = ex.response?.data as any
        throw new HttpError(ex.response.status, 
          ex.response.statusText || "Server Exception", 
          e.message || "Error")
      }
      throw new HttpError(500, "Sever error", "Cannot retrive response from server")
    }
  }

    export async function getLanguages(): Promise<string[]> {
      try {
        const response = await axios.get<string[]>('/api/languages')
        return response.data
      } catch(error) {
        const ex = error as AxiosError
        if(ex.response) {
          const e = ex.response?.data as any
          throw new HttpError(ex.response.status, 
            ex.response.statusText || "Server Exception", 
            e.message || "Error")
        }
        throw new HttpError(500, "Sever error", "Cannot retrive response from server")
      }
    }

    export async function getSettings(): Promise<Settings> {
      try {
        const response = await axios.get<Settings>('/api/settings')
        return response.data
      } catch(error) {
        const ex = error as AxiosError
        if(ex.response) {
          const e = ex.response?.data as any
          throw new HttpError(ex.response.status, 
            ex.response.statusText || "Server Exception", 
            e.message || "Error")
        }
        throw new HttpError(500, "Sever error", "Cannot retrive response from server")
      }
    }