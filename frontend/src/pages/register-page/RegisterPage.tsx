import { Link } from 'react-router-dom'
import logo from '../../assets/logo-just-watch-together.png'
import classes from './RegisterPage.module.css'

function RegisterPage() {

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
                  <input type="text" id="username" name="username" className={`${classes.aInputText} ${classes.aSpan12}`}
                    aria-required="true" />
                </div>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="username" className={`${classes.regLabel}`}>Your name</label>
                  <input type="text" id="fullName" name="fullName"
                    className={`${classes.aInputText} ${classes.aSpan12}`}
                    placeholder="First and last name"
                    aria-required="true" />
                </div>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="username" className={`${classes.regLabel}`}>Email</label>
                  <input type="email" id="email" name="email"
                    className={`${classes.aInputText} ${classes.aSpan12}`}
                    placeholder="person@example.com"
                    aria-required="true" />
                </div>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="password" className={`${classes.regLabel}`}>Password</label>
                  <input type="password" id="password" placeholder="at least 8 characters" name="password"
                    className={`${classes.aInputText} ${classes.aSpan12}`} aria-required="true" />
                </div>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="confirmPassword" className={`${classes.regLabel}`}>Re-enter password</label>
                  <input type="password" id="confirmPassword" name="confirmPassword"
                    className={`${classes.aInputText} ${classes.aSpan12}`} aria-required="true" />
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
