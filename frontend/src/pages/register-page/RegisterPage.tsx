import { Link } from 'react-router-dom'
import logo from '../../assets/logo-just-watch-together.png'
import classes from './RegisterPage.module.css'
import { useEffect, useState } from 'react'
import createFormReducer from '../../utils/form-reducer'
import { type RegisterForm } from '../../utils/models'
import { submitRegister, validateRegister } from '../../utils/api-functions'
import { Alert, CloseButton } from 'react-bootstrap'

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

interface ToastProps {
  variant: 'danger' | 'success'
  message?: string
  id: number
}

function Toast(props: ToastProps) {
  const [show, setShow] = useState(false)

  useEffect(() => {
    if(props.message) {
      setShow(true)
      setTimeout(()=> setShow(false), 3000)
    }
  }, [props.message, props.id]);

  return(
    <>
        {show ? <Alert variant='danger' style={{width: 'fit-content', margin: '1em auto', display: 'flex', gap: '10px'}}>
        <span>{props.message}</span> <CloseButton onClick={_ => setShow(false)}/>
        </Alert>: null}
    </>
  )
}

function RegisterPage() {
  const initalState: RegisterForm = {
    username: '', password: '', confirmPassword: '', email: '', fullName: ''
  }

  const [form, dispatch] = createFormReducer(initalState)

  const [errors, setErrors] = useState(initalState)

  const [error, setError] = useState({msg: '', id: 0})

  const [success, setSuccess] = useState({msg: '', id:0})

  function showError(msg: string) {
    setError({msg, id: error.id++})
  }

  function showSuccess(msg: string) {
    setSuccess({msg, id: success.id++})
  }

  useEffect(() => {
    console.log("Test")
    const timer = setTimeout(async () => {
      const response = await validateRegister(form);
      if(response) {
        setErrors({
          username: response.errors.find(x => x.property === 'username')?.message ?? '',
          email: response.errors.find(x => x.property === 'email')?.message ?? '',
          password: response.errors.find(x => x.property === 'password')?.message ?? '',
          confirmPassword: response.errors.find(x => x.property === 'confirmPassword')?.message ?? '',
          fullName: response.errors.find(x => x.property === 'fullName')?.message ?? '',
        })
      } else {
        setErrors(initalState)
      }
    }, 500)
    return () => clearTimeout(timer)
  }, [form])

  async function onSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault()
    console.log("Submit")
    const response = await submitRegister(form)
    if(response) {
      showError(response)
    } else {
      console.log("Success")
      showSuccess("OK")
    }
  }

  return (
    <div>
      <header className={`${classes.siteHeader} ${classes.textAlign}`}>
        <a className={`${classes.linkNavIcon}`} href="#">
          <img className={`${classes.image} ${classes.imageSizeReg}`} src={logo} alt="logo" />
        </a>
      </header>
      <main className={`${classes.siteContent}`}>
        <Toast variant='danger' message={error.msg} id={error.id}/>
        <Toast variant='success' message={success.msg} id={success.id}/> 
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
                  type='password'
                  error={errors.password}
                />
                <FormField
                  displayName='Re-enter password'
                  id='confirmPassword'
                  value={form.confirmPassword}
                  setValue={e => dispatch('confirmPassword', e)}
                  placeholder="at least 8 characters"
                  type='password'
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
