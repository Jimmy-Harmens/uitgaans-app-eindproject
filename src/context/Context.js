import React, {createContext, useState} from "react";

export const Context = createContext({});

function ContextProvider({children}){

    //Voor Tessa: ik ga ook werkende authenticatie verwerken, dit is alleen voor illustratie
    const [loggedIn, toggleLoggedIn] = useState(false)

    return(
        <Context.Povider value={{
            isLoggedIn : loggedIn,
            logout : toggleLoggedIn(false),
            login : toggleLoggedIn(true)
        }}>
            {children}
        </Context.Povider>
    )
}

export default ContextProvider;