import React from "react";
import "./LogoutButtonStyle.css";

function LogoutButton(){
    function logout(){
        //later functionaliteit toevoegen.
        console.log("uitloggen");
    }
    return(
        <button onClick={(event) => logout()} className="logout-button">Logout</button>
    )
}

export default LogoutButton;