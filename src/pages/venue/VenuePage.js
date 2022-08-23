import React, {useEffect, useState} from "react";
import Header from "../../components/header/Header";
import Searchbar from "../../components/searchbar/Searchbar";
import LogoutButton from "../../components/logoutButton/LogoutButton";
import returnIcon from "../../assets/Return_Icon.png"
import moreOptions from "../../assets/More_Options.png"
import "./VenueStyle.css"
import clubUpTemp from "../../assets/Club_up_temp.jpg";
import clubUpLogo from "../../assets/ClubUp_logo_temp.png";
import VenueCard from "../../components/venueCard/VenueCard";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import minutesToStandard from "../../helpers/MinutesToStandard";

function VenuePage({user}){

    let navigate = useNavigate();
    const {venueName} = useParams()
    const [location, setLocation] = useState({})

    useEffect(() => {
        async function getLocation(){
            try{
                const response = await axios.get(`http://localhost:8080/location/byId`, {
                    params : {
                        locationId : venueName
                    }
                })
                setLocation(response.data);
                console.log(response.data);
            }catch (e){
                console.log(e);
            }
        }
        getLocation()
    }, [])

    const stAppSuggestionTemplate = {
        locationPhoto : clubUpTemp,
        logo : clubUpLogo,
        locationName : location.locationName,
        locationType : location.typeLocation,
        averageRating : location.reviewAverage,
        amountOfRatings : location.reviewAmount,
        openingTime : minutesToStandard(location.openingTime),
        closingTime : minutesToStandard(location.closingTime),
        distance : location.distance,
        locationId : location.locationId,
        latitude : location.latitude,
        longitude : location.longitude,

        tagOne : "Cocktails", //hardcoded
        tagTwo : "Lunch", //hardcoded
        tagThree : "Live muziek" //hardcoded
    }

    return(
        <div className="page">
            <Header classname="header" navItems={[<Searchbar/>, <LogoutButton/>]}/>
            <div className="venue-page-content">
                <div className="page-container-left">
                    <img src={returnIcon} alt="return" width="75px" onClick={(e) => navigate(`/home`)} id="hendrik"/>
                    <img src={moreOptions} alt="more options" width="75px"/>
                </div>
                <div className="base-info">
                    <VenueCard venueInformation={stAppSuggestionTemplate} user={user}/>
                </div>
                <div className="extra-info">
                    <p id="beschrijving">
                        {location.description}
                    </p>
                </div>
            </div>
        </div>
    )
}

export default VenuePage;