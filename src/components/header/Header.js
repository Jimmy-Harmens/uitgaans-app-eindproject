import React from "react";
import userIcon from "../../assets/User_Icon.png"
import "./HeaderStyle.css"


function Header({navItems}) {
    return(
        <header className="header">
            <div className="container-left">
                <img id="userIcon" src={userIcon} alt="user icon" width="100" height="100"/>
                <h1>sTapp</h1>
            </div>
            <div className="container-right">
                {navItems}
            </div>
        </header>
    )
}

export default Header;