import React, {useState} from "react";
import "./InfoPopupStyle.css"
import infoButton from "../../assets/Info_Button.png"
import dismiss from "../../assets/Dismiss_Icon.png"

function InfoPopup({infoText}){
    const [showPopup, togglePopup] = useState(false)
    return(
        <div>
            <img src={infoButton} alt="info" width="20px" height="20px" onClick={(e) => togglePopup(true)}/>
            {showPopup && <div className="popup-container">
                <div className="popup-top-bar">
                    <div className="popup-minimize" onClick={(e) => {
                        togglePopup(false);
                        // console.log(showPopup)
                    }}>
                        <img src={dismiss} alt="close" width="30px" height="30px"/>
                    </div>
                </div>
                <div className="popup-body">
                    {infoText}
                </div>
            </div>}
        </div>
    )
}

export default InfoPopup;