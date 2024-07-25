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
  error: Violation[]
}