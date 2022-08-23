import React from "react";
import Header from "../../components/header/Header";
import LogoutButton from "../../components/logoutButton/LogoutButton";

function RoutePage(){
    const randomVariable = {
        randomArray : ["one", "two", "three"],
        randomNumber : 3.56,
        randomObject : {
            deeperParam : "lorem200"
        }
    }
    return(
        <div>
            <Header navItems={[<LogoutButton/>]}/>
            <h1>The start of something exciting</h1>
        </div>
    )
}

export default RoutePage;