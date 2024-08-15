import { useEffect, useState } from "react";
import { Movie } from "../utils/models";
import { getMovie } from "../utils/api-functions";
import { useParams } from "react-router-dom";
import { useLanguage } from "../utils/LanguageProvider";
import { useAuthGuard } from "../utils/AuthProvider";

export function MovieDetails() {

    useAuthGuard()

    const [movie, setMovie] = useState<Movie>()
    const { id } = useParams()
    const lang = useLanguage()

    let genres = () => movie?.genres.map(g=>g.name).join(", ")

    useEffect(()=>{
        getMovie(Number.parseInt(id as string), lang.selected)
        .then(x => setMovie(x))
    }, [lang.selected])

    const options = { year: 'numeric', month: 'long', day: 'numeric' };

    function showDate(movie: Movie | undefined) {
        if(!movie) {
            return "January 1, 2024"
        }
        return new Date(movie.releaseDate[0], movie.releaseDate[1] - 1, movie.releaseDate[2])
        .toLocaleDateString(lang.selected == "BG" ? "BG" : "en-US", options as any)
    }


    return (
        <main>
        <section className="movie-details container">
            <h2>{movie?.title}</h2>
            <div className="movie-info">
                <img src={movie?.imageURL} alt="Movie Details"/>
                <div className="details">
                    <p><strong>Genre:</strong> {genres()}</p>
                    <p><strong>Release Date:</strong> {showDate(movie)}</p>
                    <p><strong>Director:</strong> John Doe</p>
                    <p><strong>Cast:</strong> Actor A, Actor B, Actor C</p>
                    <p><strong>Synopsis:</strong> {movie?.overview}</p>
                </div>
            </div>
        </section>
    </main>
    )
}