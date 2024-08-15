import { Link } from "react-router-dom";
import { Page } from "../utils/models";

export function Pagination({page, base}: {page:Page<any> | undefined, base?: string}) {
    const createRange = (start: number, end: number, step = 1) => {
        return Array.from({ length: Math.ceil((end - start + 1) / step) }, (_, i) => start + i * step);
    };
    if(!page) {
        return (<></>)
    }

    const link = (pageNumber: number) => `${base || '/'}?page=${pageNumber}`

    return (
        <div className="pagination">
            {!page.first ? <Link to={link(0)} className="prev">First</Link> : null}
            {!page.first ? <Link to={link(page.number-1)} className="prev">Previous</Link> : null}
            {createRange(page.number, page.number+2 >= page.totalPages ? page.totalPages-1 : page.number+2)
            .map(i=><Link to={link(i)} className="page-number">{i+1}</Link>)}
            {!page.last ? <Link to={link(page.number+1)} className="next">Next</Link> : null}
            {!page.last ? <Link to={link(page.totalPages-1)} className="next">Last</Link> : null}
        </div>
    )
}
