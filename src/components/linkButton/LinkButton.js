import React from "react";
import "./LinkButtonStyle.css"

function LinkButton({onClick, text}){
    return(
        <button className="link-button" onClick={onClick}>{text}</button>
    )
}
export default LinkButton;