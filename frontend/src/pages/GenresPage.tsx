import { useEffect, useState } from "react"
import { Genre } from "../utils/models"
import { useAuthGuard } from "../utils/AuthProvider"
import { getGenres } from "../utils/api-functions"
import { useLanguage } from "../utils/LanguageProvider"
import { Link } from "react-router-dom"


export function GenresPage() {

    useAuthGuard()

    const [genres, setGenres] = useState<Genre[]>([])
    const lang = useLanguage()

    useEffect(() => {
        getGenres(lang.selected).then(x=>setGenres(x))
    }, [])


    return (
        <main>
        <section className="genres-list container">
            <h2>Movie Genres</h2>
            <ul>
                {genres.map(i=> <li><Link to={`/filter/${i.id}/movies`} key={i.id}>{i.name}</Link></li>)}
            </ul>
        </section>
    </main>
    )
}