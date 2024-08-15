import { useState } from 'react'
import { useAuth } from '../utils/AuthProvider'


function LoginPage() {

  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")

  const auth = useAuth()


  async function login(username: string, password: string) {
    console.log(username, password)
    auth.login(username, password)
  }

  return (
    <main>
        <section className="form-section container">
            <h2>Login</h2>
            <form onSubmit={e => {e.preventDefault(); login(username, password)}}>
                <label htmlFor="username">Username:</label>
                <input value={username} onChange={e => setUsername(e.target.value)} type="text" id="username" name="username" required/>
                <label htmlFor="password">Password:</label>
                <input value={password} onChange={e => setPassword(e.target.value)} type="password" id="password" name="password" required/>
                <button type="submit">Login</button>
            </form>
        </section>
    </main>
  )
}

export default LoginPage
