import { ActuatorInfo, RegisterForm, ViolationResponse } from "./models";

export async function getServerInfo(): Promise<ActuatorInfo> {
    const response = await fetch('/actuator/info');

    if(!response.ok) {
        console.log(response)
        throw new Error(`Server returned: (${response.status}) ${response.statusText}`)
    }
    const body = await response.json() as ActuatorInfo
    return body
}


export async function validateRegister(form: RegisterForm) {
    const response = await fetch('/api/users?dryRun=true',  {
      method: 'POST', // Specify the request method
      headers: {
        'Content-Type': 'application/json', // Set the content type to JSON
      },
      body: JSON.stringify(form)});
    if(response.ok) {
      return null;
    } else {
      const errors: ViolationResponse = await response.json()
      return errors
    }
}

export async function submitRegister(form: RegisterForm) {
    const response = await fetch('/api/users',  {
      method: 'POST', // Specify the request method
      headers: {
        'Content-Type': 'application/json', // Set the content type to JSON
      },
      body: JSON.stringify(form)});
    if(response.ok) {
      return null
    } else {
      return response.statusText
    }
}