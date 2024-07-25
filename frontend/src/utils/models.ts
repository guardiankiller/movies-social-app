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
