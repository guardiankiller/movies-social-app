import { Link } from "react-router-dom";
import { useAuth } from "../utils/AuthProvider";
import { useLanguage } from "../utils/LanguageProvider";


export function Header() {
    const auth = useAuth()
    const lang = useLanguage()
    return (
        <header>
        <div className="container">
            <h1>{lang.selected == "BG" ? "Социален сайт за филми" : "Movie Social Site"}</h1>
            <div className="navbar-second-row">
                <nav>
                    {auth.isLoggedIn() ? <Link to="/">{lang.selected == "BG" ? "Дом" : "Home"}</Link> : null}
                    {!auth.isLoggedIn() ? <Link to="/login">{lang.selected == "BG" ? "Влез" : "Login"}</Link> : null}
                    {!auth.isLoggedIn() ? <Link to="/register">{lang.selected == "BG" ? "Регистриай се" : "Register"}</Link> : null}
                    {auth.isLoggedIn() ? <Link to="/genres">{lang.selected == "BG" ? "Жанрове" : "Genres"}</Link> : null}
                    <Link to="/about">{lang.selected == "BG" ? "Кои сме ние" : "About Us"}</Link>
                    <Link to="/contact">{lang.selected == "BG" ? "Свържи се с нас" : "Contact Us"}</Link>
                    {auth.isLoggedIn() ? <Link to="/faq">FAQ</Link> : null}
                    
                </nav>
                {auth.isLoggedIn() ?
                <div className="user-panel">
                    <div className="username">{lang.selected == "BG" ? "Добре дошли," : "Welcome,"} {auth.accessToken().fullName}</div>
                    <button onClick={_ => auth.logout()}>Logout</button>
                </div> 
                : null}
                <div className="lang-panel">
                    {lang.languages
                    .map(x=><span 
                        key={x} 
                        onClick={_ => lang.select(x)} 
                        className={lang.isSelected(x) ? "lang-selected" : ""}>{x}</span>)}
                </div>
            </div>
        </div>
      </header>
    )
}