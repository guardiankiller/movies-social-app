import { useReducer } from "react"

export class Action<T> {
    constructor(readonly type: keyof T, readonly input: string) { }
}


function registerReducerFunc<T>(state: T, action: Action<T>): T {
    const newObj: T = { ...state }
    newObj[action.type] = action.input as any
    return newObj
}

type DipatchFunc<T> = (type: keyof T, input: string) => void

export default function createFormReducer<T>(initalState: T): [T, DipatchFunc<T>] {
    const [form, dispatch] = useReducer(registerReducerFunc<T>, initalState)
    
    function change (type: keyof T, input: string) {
        dispatch({type, input})
    }
    return  [form, change]
}