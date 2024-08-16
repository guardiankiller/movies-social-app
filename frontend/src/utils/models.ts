interface Build {
    artifact: string
    group: string
    version: string
    name: string
    time: string
}

interface GitCommit {
    id: string
    time: string
}

interface Git {
    branch: string
    commit: GitCommit
}

export interface ActuatorInfo {
    build: Build
    git: Git
}

export interface RegisterForm {
    username: string
    fullName: string
    password: string
    confirmPassword: string
    email: string
}

export interface Violation {
  property: string
  message: string
  type: string
}

export interface ViolationResponse {
  message: string
  errors: Violation[]
}

export interface AuthResponse {
    accessToken: string
    expirationTime: string
    type: string
}

export interface UserInfo {
    id: number,
    username: string
    fullName: string
    email: string
    roles: string[]
}

export class HttpError extends Error {
    constructor(
        // @ts-ignore
        private statusCode: number, 
        // @ts-ignore
        private statusText: string,
        public message: string
    ) { super() }
}

export interface AccessToken {
    exp: number
    fullName: string
    iat: number
    iss: string
    sub: string
    username: string
}

export interface Page<T> {
    content: T[]
    totalPages: number
    totalElements: number
    numberOfElements: number
    size: number
    number: number
    first: boolean
    last: boolean
}

export interface Genre {
    id: number
    name: string
}

export interface Movie {
    id: number
    popularity: number
    voteAverage: number
    voteCount: number
    releaseDate: number[]
    imageURL: string
    title: string
    overview: string
    genres: Genre[]
}

export interface Settings {
    loading: boolean
}