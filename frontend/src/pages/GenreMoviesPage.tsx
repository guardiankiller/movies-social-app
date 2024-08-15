import { getGenre, getMoviesByGenre } from "../utils/api-functions";
import { useAuthGuard } from "../utils/AuthProvider";
import { useEffect, useState } from "react";
import { Movie, Page } from "../utils/models";
import { useParams, useSearchParams } from "react-router-dom";
import { useLanguage } from "../utils/LanguageProvider";
import { Pagination } from "../layouts/Pagination";
import { MovieCard } from "../layouts/MovieCard";


export function GenreMoviesPage() {

    useAuthGuard()

    const lang = useLanguage()

    const { id } = useParams()

    const [movies, setMovies] = useState<Page<Movie> | undefined>()
    const [genreName, setGenreName] = useState("")

    const [searchParams, _] = useSearchParams()

    const page = () => Number.parseInt(searchParams.get("page") || "0")

    useEffect(() => {
        const nid = Number.parseInt(id as string)
        getGenre(nid, lang.selected).then(g=>setGenreName(g.name))
        getMoviesByGenre(nid, lang.selected, page()).then(m => setMovies(m))
    }, [lang.selected, page(), id])


    return (
        <>
            <section className="hero">
                <div className="container">
                    <h2>Welcome to Movie Social Site</h2>
                    <p>Discover, Share, and Discuss Your Favorite Movies</p>
                    <button>Explore Now</button>
                </div>
            </section>
            <main>
                <section className="container">
                    <h2>{genreName}</h2>
                    <Pagination page={movies} base={`/filter/${id}/movies`}/>
                    <div className="movie-grid">
                        {movies?.content.map(x => <MovieCard key={x.id} id={x.id} title={x.title} imageURL={x.imageURL} overview={x.overview}/>)}
                    </div>
                    <Pagination page={movies}/>
                </section>
            </main>
        </>
    )
}