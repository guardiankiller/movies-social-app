import { getMovies } from "../utils/api-functions";
import { useAuthGuard } from "../utils/AuthProvider";
import { useEffect, useState } from "react";
import { Movie, Page } from "../utils/models";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { useLanguage } from "../utils/LanguageProvider";

function MovieCard({title, imageURL, overview, id}: {title: string, imageURL: string, overview: string, id: number}) {
    const desc = overview.length > 100 ? overview.substring(0, 100) + "..." : overview
    const navigate = useNavigate()
    function details() {
        navigate(`/movies/${id}`)
    }
    return (
            <div className="movie-card" onClick={_ => details()}>
                <img src={imageURL} alt="Popular Movie 1" />
                <div className="movie-info">
                    <h3>{title}</h3>
                    <p>{desc}</p>
                </div>
            </div>
    )
}

export function Pagination({page}: {page:Page<any> | undefined}) {
    console.log(page)
    const createRange = (start: number, end: number, step = 1) => {
        return Array.from({ length: Math.ceil((end - start + 1) / step) }, (v, i) => start + i * step);
    };
    if(!page) {
        return (<></>)
    }
    return (
        <div className="pagination">
            {!page.first ? <Link to={`/?page=0`} className="prev">First</Link> : null}
            {!page.first ? <Link to={`/?page=${page.number-1}`} className="prev">Previous</Link> : null}
            {createRange(page.number, page.number+2)
            .map(i=><Link to={`/?page=${i}`} className="page-number">{i+1}</Link>)}
            {!page.last ? <Link to={`/?page=${page.number+1}`} className="next">Next</Link> : null}
            {!page.last ? <Link to={`/?page=${page.totalPages!-1}`} className="next">Last</Link> : null}
        </div>
    )
}

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