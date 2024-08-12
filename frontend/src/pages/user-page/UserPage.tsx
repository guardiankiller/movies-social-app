import { useParams } from "react-router-dom";
import { useAuthGuard } from "../../utils/AuthProvider";
import { useEffect, useState } from "react";
import { getUserByUsername } from "../../utils/api-functions";
import { HttpError, UserInfo } from "../../utils/models";
import { useSnackbar } from "../../utils/Snackbar";


export function UserPage() {
    const { username } = useParams()

    const snackbar = useSnackbar()
    const [userInfo, setUserInfo] = useState({} as UserInfo)
    
    useAuthGuard()

    useEffect(() => {
        getUserByUsername(username as string)
        .then(data => setUserInfo(data))
        .catch((error: HttpError) => {
            console.log(error)
            snackbar.showError(error.message)
        })
    }, [])


    return(
        <>
        <h1>{userInfo.username}</h1>
        <p>{userInfo.email}</p>
        <p>{userInfo.roles}</p>
        <p>{userInfo.fullName}</p>
        </>
    )
}