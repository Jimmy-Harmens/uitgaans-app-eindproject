import React, {useContext, useState} from "react";
import {Context} from "../../context/Context";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import jwtDecode from "jwt-decode";
import "./LoginStyle.css"
import Header from "../../components/header/Header";
import LinkButton from "../../components/linkButton/LinkButton";
import beer from "../../assets/Beer_Mug_Logo.png"

function LoginPage(){
    const [usernameInput, setUsernameInput] = useState("")
    const [passwordInput, setPasswordInput] = useState("")
    const [loginFailed, setLoginFailed] = useState(false)

    let navigate = useNavigate()

    return(
        <div className="page">
            <Header navItems={[
                <LinkButton onClick={(e) => navigate("/info")} text="Info"/>,
                <LinkButton onClick={(e) => navigate("/contact")} text="Contact"/>
            ]}
            />
            <div id="bottom-content">
                <div id="middle-column">
                    <h1>stApp</h1>
                    <div id="login-block">
                        <input className="login-field" type="text" placeholder="gebruikersnaam" onChange={(e) => setUsernameInput(e.target.value)}/>
                        <input className="login-field" type="password" placeholder="wachtwoord" onChange={(e => setPasswordInput(e.target.value))}/>
                        <button
                            id="login-button"
                            onClick={
                            async function login(){
                                try{
                                    const response = await axios.post(`http://localhost:8080/authenticate`, {
                                        username : usernameInput,
                                        password : passwordInput
                                    })
                                    console.log(response.data)
                                    console.log(jwtDecode(response.data.jwt));
                                    localStorage.setItem("token", response.data.jwt)
                                    navigate("/home");
                                    window.location.reload(false);
                                }catch (e){
                                    console.log(e)
                                    setLoginFailed(true)
                                }
                            }
                        }>
                            login
                        </button>
                        {loginFailed && <div>Verkeerd wachtwoord! probeer opnieuw.</div>}
                        <img id="beer-logo" src={beer} alt="beer mug" width="100px" height="100px"/>
                    </div>
                    <div id="signup-suggestion">
                        <div>Nog geen account?</div>
                        <LinkButton onClick={(e) => navigate("/signup")} text={"click hier!"}/>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LoginPage;