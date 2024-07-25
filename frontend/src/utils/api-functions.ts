import { ActuatorInfo } from "./models";

export async function getServerInfo(): Promise<ActuatorInfo> {
    const response = await fetch('/actuator/info');

    if(!response.ok) {
        console.log(response)
        throw new Error(`Server returned: (${response.status}) ${response.statusText}`)
    }
    const body = await response.json() as ActuatorInfo
    return body
}