import React, {useContext} from "react";
import "./LogoutButtonStyle.css";
import {useNavigate} from "react-router-dom";

function LogoutButton({setUser}){

    let navigate = useNavigate()

    function logout(){
        //nu functionaliteit toevoegen.
        localStorage.removeItem("token")
        console.log("uitloggen...");
        navigate("/")
        window.location.reload(false);
    }
    return(
        <button onClick={(event) => logout()} className="logout-button">Logout</button>
    )
}

export default LogoutButton;