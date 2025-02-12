import { getMovies } from "../utils/api-functions";
import { useAuthGuard } from "../utils/AuthProvider";
import { useEffect, useState } from "react";
import { Movie, Page } from "../utils/models";
import { useSearchParams } from "react-router-dom";
import { useLanguage } from "../utils/LanguageProvider";
import { MovieCard } from "../layouts/MovieCard";
import { Pagination } from "../layouts/Pagination";

export function UserPage() {

    useAuthGuard()

    const lang = useLanguage()

    const [movies, setMovies] = useState<Page<Movie> | undefined>()

    const [searchParams, _] = useSearchParams()

    const page = () => Number.parseInt(searchParams.get("page") || "0")

    useEffect(() => {
        getMovies(lang.selected, page()).then(m => setMovies(m))
    }, [lang.selected, page()])


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
                    <h2>Popular Movies</h2>
                    <Pagination page={movies}/>
                    <div className="movie-grid">
                        {movies?.content.map(x => <MovieCard key={x.id} id={x.id} title={x.title} imageURL={x.imageURL} overview={x.overview}/>)}
                    </div>
                    <Pagination page={movies}/>
                </section>
            </main>
        </>
    )
}