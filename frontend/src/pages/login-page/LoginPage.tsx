import { Link } from 'react-router-dom'
import logo from '../../assets/logo-just-watch-together.png'
import classes from './LoginPage.module.css'

function LoginPage() {

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
                <h1 className={`${classes.h1Margin} ${classes.width}`}>Sign in</h1>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="username" className={`${classes.regLabel}`}>Username</label>
                  <input type="text" id="username" name="username" className={`${classes.aInputText} ${classes.aSpan12}`}
                    aria-required="true" />
                </div>
                <div className={`${classes.aSpacingBase}`}>
                  <label htmlFor="password" className={`${classes.regLabel}`}>Password</label>
                  <input type="password" id="password" placeholder="at least 8 characters" name="password"
                    className={`${classes.aInputText} ${classes.aSpan12}`} aria-required="true" />
                </div>
                <div className={`${classes.aSpacingExtraLarge}`}>
                  <span className={`${classes.aButton} ${classes.aButtonSpan12} ${classes.aButtonPrimary}`}>
                    <button className={`${classes.aButtonInner} ${classes.aButtonText} ${classes.textColor}`} type="submit">Sign in</button>
                  </span>
                </div>
                <label className={`${classes.regLabel}`}>
                  <input type="checkbox" name="remember" value="true" />
                  <span className={`${classes.aLabel} ${classes.aCheckboxLabel}`}>
                    Keep me signed in.
                  </span>
                </label>
                <div className={`${classes.aDividerInner}`}></div>
                <div className={`${classes.aRow}`}>New to JUST?
                  <div className={`${classes.bSpacingExtraLarge}`}>
                    <span className={`${classes.aButton} ${classes.aButtonSpan12} ${classes.bButtonPrimary}`}>
                      <Link className={`${classes.aButtonInner} ${classes.aButtonText} ${classes.textColor} ${classes.textDecoration}`} to="/register">Create your JUST account</Link>
                    </span>
                  </div>
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

export default LoginPage
