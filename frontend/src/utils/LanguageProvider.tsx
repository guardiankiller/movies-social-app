import React, { ReactNode, useEffect, useState } from "react"
import { useContext } from "react"
import { getLanguages } from "./api-functions"

export interface LnaguageContext {
    languages: string[]
    isSelected: (lang: string) => boolean,
    selected: string,
    select: (lang: string) => void
}

const ctx = React.createContext<LnaguageContext>({} as LnaguageContext)

export const useLanguage = () => useContext(ctx)

export function LanguageProvider({ children }: { children: ReactNode }) {

    const [languages, setLanguages] = useState<string[]>([])
    const [current, setCurrent] = useState<string>(localStorage.getItem("lang") || "")

    function select(lang: string) {
        setCurrent(lang)
        localStorage.setItem("lang", lang)
    }

    useEffect(()=> {
        getLanguages()
        .then(res=>{
            setLanguages(res)
            if(current == "") {
                setCurrent(res[0])
            }
        })

        let id = setInterval(() => {
            getLanguages()
            .then(res=>{
                setLanguages(res)
                if(current == "") {
                    setCurrent(res[0])
                }
            })
            if(languages.length > 0) {
                clearInterval(id)
            }
        }, 3000)
        return () => clearInterval(id)
    }, [])

    let value: LnaguageContext = {
        languages,
        isSelected: lang => current === lang,
        selected: current,
        select
    }

    return <ctx.Provider value={value}>{children}</ctx.Provider>
}