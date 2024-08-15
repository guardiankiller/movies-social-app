import { ReactNode, useEffect, useState } from "react";
import { getSettings } from "../utils/api-functions";
import { useLanguage } from "../utils/LanguageProvider";


export function LoadingPage({children}: {children: ReactNode}) {

    const [loading, setLoading] = useState(true)
    const lang = useLanguage()

    useEffect(() => {
        getSettings().then(settings => setLoading(settings.loading))
        let int = setInterval(() => {
            getSettings().then(settings => setLoading(settings.loading))
        }, 3000)
        return () => clearInterval(int)
    }, [])


    if(loading) {
        if(lang.selected == "BG") {
            return <h2>Сайтът все още импортира своята информация. Моля изчакайте.</h2>
        } else {
            return <h2>The site is currently importing its data. Please wait.</h2>
        }
    }

    return (<>{children}</>)
}