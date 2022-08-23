import React from "react";
import "./ToggleSwitchStyle.css"

function ToggleSwitch({toggleFunction, toggleState}){
    return(
        <label className="switch">
            <input type="checkbox" onClick={(e) => toggleFunction(!toggleState)}/>
            <span className="slider round"/>
        </label>
    )
}

export default ToggleSwitch;