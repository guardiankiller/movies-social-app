import { Link } from 'react-router-dom'
import logo from '../../assets/logo-just-watch-together.png'
import classes from './RegisterPage.module.css'
import { useEffect, useState } from 'react'
import createFormReducer from '../../utils/form-reducer'
import { type RegisterForm, type ViolationResponse } from '../../utils/models'

interface FormFieldProps {
  displayName: string
  id: string
  value: string
  setValue: (input: string) => void
  error: string
  type?: string
  placeholder?: string
}


function FormField(props: FormFieldProps) {
  const [dirty, setDirty] = useState(false)

  function updateDirty(value: boolean) {
    if(!dirty) {
      setDirty(value)
    }
  }

  return (
    <div className={`${classes.aSpacingBase}`}>
    <label htmlFor={props.id} className={`${classes.regLabel}`}>{props.displayName}</label>
    <input 
      type={props.type ?? 'text'}
      id={props.id}
      className={`${classes.aInputText} ${classes.aSpan12}`}
      value={props.value}
      onChange={e => props.setValue(e.target.value)}
      onBlur={ _ => updateDirty(true) }
      placeholder={props.placeholder ?? ''}
      aria-required="true" />
      {dirty && props.error ? <p className={`${classes.errorLabel}`}>{props.error}</p> : null}
  </div>
  )
}

function RegisterPage() {
  const initalState: RegisterForm = {
    username: '', password: '', confirmPassword: '', email: '', fullName: ''
  }

  const [form, dispatch] = createFormReducer(initalState)

  const [errors, setErrors] = useState(initalState)

  useEffect(() => {
    const timer = setTimeout(async () => {
      const response = await fetch('/api/users?dryRun=true',  {
        method: 'POST', // Specify the request method
        headers: {
          'Content-Type': 'application/json', // Set the content type to JSON
        },
        body: JSON.stringify(form)});
      if(!response.ok) {
        const errors: ViolationResponse = await response.json()

        setErrors({
          username: errors.errors.find(x => x.property === 'username')?.message ?? '',
          email: errors.errors.find(x => x.property === 'email')?.message ?? '',
          password: errors.errors.find(x => x.property === 'password')?.message ?? '',
          confirmPassword: errors.errors.find(x => x.property === 'confirmPassword')?.message ?? '',
          fullName: errors.errors.find(x => x.property === 'fullName')?.message ?? '',
        })
      } else {
        setErrors(initalState)
      }
    }, 500)
    return () => clearTimeout(timer)
  }, [form])

  function onSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault()
    console.log("Submit")
  }

  return (
    <div>
      <header className={`${classes.siteHeader} ${classes.textAlign}`}>
        <a className={`${classes.linkNavIcon}`} href="#">
          <img className={`${classes.image} ${classes.imageSizeReg}`} src={logo} alt="logo" />
        </a>
      </header>
      <main className={`${classes.siteContent}`}>
        <form action="" onSubmit={onSubmit}>
          <div className={`${classes.centerSection}`}>
            <div className={`${classes.aBox}`}>
              <div className={`${classes.aBoxInner}`}>
                <h1 className={`${classes.h1Margin} ${classes.width}`}>Create account</h1>
                <FormField
                  displayName='Username'
                  id='username'
                  value={form.username}
                  setValue={e => dispatch('username', e)}
                  error={errors.username}
                />
                <FormField
                  displayName='Your name'
                  id='fullName'
                  value={form.fullName}
                  setValue={e => dispatch('fullName', e)}
                  placeholder="First and last name"
                  error={errors.fullName}
                />
                <FormField
                  displayName='Email'
                  id='email'
                  value={form.email}
                  setValue={e => dispatch('email', e)}
                  placeholder="person@example.com"
                  type='email'
                  error={errors.email}
                />
                <FormField
                  displayName='Password'
                  id='password'
                  value={form.password}
                  setValue={e => dispatch('password', e)}
                  placeholder="at least 8 characters"
                  type='pasword'
                  error={errors.password}
                />
                <FormField
                  displayName='Re-enter password'
                  id='confirmPassword'
                  value={form.confirmPassword}
                  setValue={e => dispatch('confirmPassword', e)}
                  placeholder="at least 8 characters"
                  type='pasword'
                  error={errors.confirmPassword}
                />
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
