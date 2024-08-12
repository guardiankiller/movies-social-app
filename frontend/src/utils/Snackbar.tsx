import React, { ReactNode, useContext, useState } from "react";
import { CloseButton, Toast } from "react-bootstrap";

export interface Snackbar {
    show: (msg: string) => void
    showError: (msg: string) => void
}

const ctx = React.createContext({} as Snackbar)

export function useSnackbar() { return useContext(ctx); }

export type Props = { children: ReactNode }

export function Snackbar({ children }: Props) {
    const [show, setShow] = useState(false)
    const [msg, setMsg] = useState<string>("")

    function showMsg(msg: string) {
        setMsg(msg)
        setShow(true)
        setTimeout(() => setShow(false), 2000);
    }

    function showError(msg: string) {
        setMsg(msg)
        setShow(true)
    }

    return (
        <>
            <ctx.Provider value={{ show: showMsg, showError }}>
                <div style={{ position: 'absolute', width: '100%' }}>
                    <Toast show={show} onClose={_ => setShow(false)}
                        style={{ marginLeft: 'auto', marginRight: 'auto' }}>
                        <Toast.Body style={{ display: 'flex', justifyContent: 'space-between' }}>
                            {msg} <CloseButton onClick={_ => setShow(false)} />
                        </Toast.Body>
                    </Toast>
                </div>
                {children}
            </ctx.Provider>
        </>
    )
}