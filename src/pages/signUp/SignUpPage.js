import React, {useState} from "react";
import LinkButton from "../../components/linkButton/LinkButton";
import Header from "../../components/header/Header";
import {useNavigate} from "react-router-dom";
import "./SignUpStyle.css"
import send from "../../assets/Send_Icon.png"
import InfoPopup from "../../components/infoPopup/InfoPopup";
import axios from "axios";
import bcrypt from "bcryptjs"

function SignUpPage(){
    const navigate = useNavigate();
    const testVerification = "123";

    const [nameInput, setNameInput] = useState("");
    const [usernameInput, setUsernameInput] = useState("");
    const [passwordInput, setPasswordInput] = useState("");
    const [passwordConfirmationInput, setPasswordConfirmationInput] = useState("");
    const [emailInput, setEmailInput] = useState("");
    const [verificationInput, setVerificationInput] = useState()

    const [signupFailed, setSignupFailed] = useState("")

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
                    <div id="signup-block">
                        <input className="signup-field"
                               type="text"
                               placeholder="naam"
                               onChange={(e) => setNameInput(e.target.value)}
                        />

                        <input className="signup-field"
                               type="text"
                               placeholder="Gebruikersnaam"
                               onChange={(e) => setUsernameInput(e.target.value)}
                        />

                        <input className="signup-field"
                               type="password"
                               placeholder="Wachtwoord"
                               onChange={(e) => setPasswordInput(e.target.value)}
                        />

                        <input className="signup-field"
                               type="password"
                               placeholder="Wachtwoord Herhaling"
                               onChange={(e) => setPasswordConfirmationInput(e.target.value)}
                        />
                        <div id="special-email-container">
                            <InfoPopup infoText="je moet gewoon normaal doen." id="info-button-email"/>
                            <input className="signup-field"
                                   id="email-field"
                                   type="email"
                                   placeholder="eMail adres"
                                   onChange={(e) => setEmailInput(e.target.value)}
                            />
                            <img src={send} alt="send" width="30px" height="30px"/>
                        </div>

                        <input className="signup-field"
                               type="text"
                               placeholder="confirmatie code"
                               onChange={(e) => setVerificationInput(e.target.value)}
                        />

                        <button id="signup-button" onClick={(e) => {
                            async function signup(){
                                if (passwordInput === passwordConfirmationInput &&
                                    verificationInput === testVerification){
                                    try{
                                        const response1 = await axios.post(`http://localhost:8080/users`,{
                                            name : nameInput,
                                            username : usernameInput,
                                            password : bcrypt.hashSync(passwordInput),
                                            enabled : true,
                                            apikey : "",
                                            email : emailInput,
                                            authorities : [{
                                                username : "",
                                                authority : ""
                                            }],
                                            favorites : []
                                        })
                                        if(response1.data !== usernameInput){
                                            setSignupFailed(response1.data)
                                            console.log(response1.data)
                                        }else{
                                            const response2 = await axios.post(`http://localhost:8080/authenticate`, {
                                                username : usernameInput,
                                                password : passwordInput
                                            })
                                            localStorage.setItem("token", response2.data.jwt)
                                            window.location.reload(false)
                                        }
                                    }catch (e){
                                        console.log(e)
                                        setSignupFailed("")
                                    }
                                }else{
                                    setSignupFailed("")
                                    console.log("failed!")
                                }
                            }
                            signup()
                        }}>
                            Create account
                        </button>
                        {signupFailed !== "" && <div>Account niet aangemaakt: {signupFailed}</div>}
                    </div>
                    <div id="login-suggestion">
                        <div>Heb je al een account?</div>
                        <LinkButton onClick={(e) => navigate("/login")} text={"click hier!"}/>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SignUpPage;