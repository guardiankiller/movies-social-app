import { useNavigate } from "react-router-dom"

export function MovieCard({title, imageURL, overview, id}: {title: string, imageURL: string, overview: string, id: number}) {
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