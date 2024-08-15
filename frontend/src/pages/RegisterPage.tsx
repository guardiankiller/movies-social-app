
import { useEffect, useState } from 'react'
import createFormReducer from '../utils/form-reducer'
import { type RegisterForm } from '../utils/models'
import { submitRegister, validateRegister } from '../utils/api-functions'
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
    <>
    <label htmlFor={props.id}>{props.displayName}:</label>
    <input 
      type={props.type ?? 'text'}
      id={props.id} 
      value={props.value}
      onChange={e => props.setValue(e.target.value)}
      onBlur={ _ => updateDirty(true) }
      placeholder={props.placeholder ?? ''}
      aria-required="true"
      required/>
      {dirty && props.error ? <p className={`${classes.errorLabel}`}>{props.error}</p> : null}
    </>
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
    <>
      <main>
        <section className="form-section container">
            <h2>Register</h2>
            <form onSubmit={onSubmit}>
                <FormField
                  displayName='Username' id='username'
                  value={form.username} setValue={e => dispatch('username', e)}
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
                <button type="submit">Register</button>
            </form>
        </section>
      </main>
    </>
  )
}

export default RegisterPage
