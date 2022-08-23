import React from "react";
import userIcon from "../../assets/User_Icon.png"
import "./HeaderStyle.css"


function Header({navItems,user}) {
    return(
        <header className="header">
            <div className="container-left">
                <div className="visual-personal">
                    <img id="userIcon" src={userIcon} alt="user icon" width="80" height="80"/>
                </div>
                <h1>stApp</h1>
                <div className="username-in-header">{user}</div>
            </div>
            <div className="container-right">
                {navItems}
            </div>
        </header>
    )
}

export default Header;