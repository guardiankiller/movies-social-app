import { Link } from 'react-router-dom'
import logo from '../../assets/logo-just-watch-together.png'
import classes from './RegisterPage.module.css'
import { useEffect } from 'react'
import createFormReducer from '../../utils/form-reducer'
import { type RegisterForm, type ViolationResponse } from '../../utils/models'

function RegisterPage() {
  const initalState: RegisterForm = {
    username: '', password: '', confirmPassword: '', email: '', fullName: ''
  }

  const [form, dispatch] = createFormReducer(initalState)

  useEffect(() => {
    const timer = setTimeout(async () => {
      const response = await fetch('/api/users',  {
        method: 'POST', // Specify the request method
        headers: {
          'Content-Type': 'application/json', // Set the content type to JSON
        },
        body: JSON.stringify(form)});
      if(!response.ok) {
        const errors: ViolationResponse = await response.json()
        console.log(errors)
      }
    }, 500)
    return () => clearTimeout(timer)
  }, [form])

  return (
    <div>
      <header className={`${classes.siteHeader} ${classes.textAlign}`}>
        <a className={`${classes.linkNavIcon}`} href="#">
          <img className={`${classes.image} ${classes.imageSizeReg}`} src={logo} alt="logo" />
        </a>
      </header>
      <main className={`${classes.siteContent}`}>
        <form action="">
          <div className={`${classes.centerSection}`}>
            <div className={`${classes.aBox}`}>
              <div className={`${classes.aBoxInner}`}>
                <h1 className={`${classes.h1Margin} ${classes.width}`}>Create account</h1>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="username" className={`${classes.regLabel}`}>Username</label>
                  <input 
                    type="text" 
                    id="username" 
                    name="username" 
                    className={`${classes.aInputText} ${classes.aSpan12}`}
                    value={form.username}
                    onChange={e => dispatch('username', e.target.value)}
                    aria-required="true" />
                </div>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="username" className={`${classes.regLabel}`}>Your name</label>
                  <input 
                    type="text" 
                    id="fullName" 
                    name="fullName"
                    className={`${classes.aInputText} ${classes.aSpan12}`}
                    placeholder="First and last name"
                    value={form.fullName}
                    onChange={e => dispatch('fullName', e.target.value)}
                    aria-required="true" />
                </div>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="username" className={`${classes.regLabel}`}>Email</label>
                  <input 
                    type="email" 
                    id="email" 
                    name="email"
                    className={`${classes.aInputText} ${classes.aSpan12}`}
                    placeholder="person@example.com"
                    value={form.email}
                    onChange={e => dispatch('email', e.target.value)}
                    aria-required="true" />
                </div>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="password" className={`${classes.regLabel}`}>Password</label>
                  <input 
                    type="password" 
                    id="password" 
                    placeholder="at least 8 characters" 
                    name="password"
                    className={`${classes.aInputText} ${classes.aSpan12}`} 
                    value={form.password}
                    onChange={e => dispatch('password', e.target.value)}
                    aria-required="true" />
                </div>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="confirmPassword" className={`${classes.regLabel}`}>Re-enter password</label>
                  <input 
                    type="password" 
                    id="confirmPassword" 
                    name="confirmPassword"
                    value={form.confirmPassword}
                    onChange={e => dispatch('confirmPassword', e.target.value)}
                    className={`${classes.aInputText} ${classes.aSpan12}`} 
                    aria-required="true" />
                </div>
                <div className={`${classes.aSpacingExtraLarge}`}>
                  <span className={`${classes.aButton} ${classes.aButtonSpan12} ${classes.aButtonPrimary}`}>
                    <button className={`${classes.aButtonInner} ${classes.aButtonText} ${classes.textColor}`} type="submit">Create your JUST account</button>
                  </span>
                </div>

                <div className={`${classes.aDividerInner}`}></div>
                <div className={`${classes.aRow}`}>Already have an account?
                  <Link to="/login" className={`${classes.aLinkEmphasis}`}> Sign in</Link>
                </div>
              </div>
            </div>
          </div>
        </form>
      </main>
      <footer className={`${classes.siteFooter}`}>

      </footer>
    </div>
  )
}

export default RegisterPage
