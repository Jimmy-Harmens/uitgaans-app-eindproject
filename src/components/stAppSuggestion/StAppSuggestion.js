import React from "react";
import "./StAppSuggestionStyle.css";
import ratingStar from "../../assets/Full_Star.png"
import clock from "../../assets/Clock_Icon.png"
import distance from "../../assets/Distance_Icon.png"
import {useNavigate} from "react-router-dom";


function StAppSuggestion({stAppLocation, user}){
    let navigate = useNavigate()
    return(
        <div className="stApp-suggestion" onClick={(e) => navigate(`../venue/${stAppLocation.locationID}`)}>
            <img src={stAppLocation.locationPhoto} alt="foto van de locatie" height="100px" className="binnenFoto"/>
            <div id="stApp-logo">
                <img src={stAppLocation.logo} alt="logo van de locatie" height="50px" width="50px" />
            </div>
            <div className="stApp-info">
                <h5 id="stApp-name">{stAppLocation.locationName}</h5>
                <p className="stApp-type">{stAppLocation.locationType}</p>
                <div className="variable-info">
                    <div className="info-item">
                        <img src={ratingStar} alt="Ster" width="30px"/>
                        <div>{stAppLocation.averageRating}</div>
                        <div>({stAppLocation.amountOfRatings})</div>
                    </div>
                    <div className="info-item">
                        <img src={clock} alt="clock icon" width="20px"/>
                        <div>{stAppLocation.openingTime + "-"}</div>
                        <div>{stAppLocation.closingTime}</div>
                    </div>
                    <div className="info-item">
                        <img src={distance} alt="distance icon" width="25px"/>
                        <div>{stAppLocation.distance + "km"}</div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default StAppSuggestion;