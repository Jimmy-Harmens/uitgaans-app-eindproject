import React, {useEffect, useState} from "react";
import "./VenueCardStyle.css"
import banner from "../../assets/Venue_Banner.png"
import routeIcon from "../../assets/Route_Icon.png"
import StarRating from "../starRating/StarRating";
import {useNavigate} from "react-router-dom";
import FavoriteHeart from "../favoriteHeart/FavoriteHeart";
import axios from "axios";

function VenueCard({venueInformation, user}){
    //temporary hardcoded travel times
    const travelTimes = {
        lopend: 40,
        auto: 15,
        openbaarVervoer: 20
    }
    const [userRating, setUserRating] = useState(0)
    const [rating, setRating] = useState(0)
    const [transportMode, setTransportMode] = useState("walking")
    const [travelTime, setTravelTime] = useState(0)

    let navigate = useNavigate();

    useEffect(() => {
        if(rating === userRating && user && venueInformation){
            async function getRating(){
                try{
                    const response = await axios.get("http://localhost:8080/ratings", {
                        params : {
                            username : user.username,
                            locationId : venueInformation.locationId,
                        }
                    })
                    setUserRating(response.data)
                }catch (e){
                    console.log(e)
                }
            }
            getRating()
        }else{
            async function setRating(){
                try{
                    const response = await axios.post("http://localhost:8080/ratings", {
                        username : user.username,
                        locationId : venueInformation.locationId,
                        rating : rating
                    })
                }catch (e){
                    console.log(e)
                }
            }
            setRating()
        }
    },[rating])

    useEffect(() => function (){
        //api naar google maps om een reistijd te bepalen
    },[transportMode])

    return(
        <div className="venue-info">
            <div className="card-header">
                <img src={banner} alt="banner" width="100%" height="100px"/>
                <div className="venue-name">
                    <div className="venue-name-container">
                        {venueInformation.locationName}
                    </div>
                    <FavoriteHeart username={user.username} venueId={venueInformation.locationId}/>
                </div>
                <div className="star-container-venues">
                    <StarRating rating={rating} updateRating={setRating}/>
                </div>
            </div>
            <div className="tags-grid">
                <div className="grid-item-top">
                    {venueInformation.openingTime + "-" + venueInformation.closingTime}
                </div>
                <div className="grid-item-top">
                    {venueInformation.tagOne}
                </div>
                <div className="grid-item-bottom">
                    {venueInformation.tagTwo}
                </div>
                <div className="grid-item-bottom">
                    {venueInformation.tagThree}
                </div>
            </div>
            <div className="route-planner">
                <div className="route-planner-item">
                    {travelTime + " minuten"}
                </div>
                <div className="custom-dropdown">
                    <select id="dropDown" onChange={event => {
                        if(event.target.value === "openbaar vervoer"){
                            setTravelTime(travelTimes.openbaarVervoer)
                        }else if(event.target.value === "auto"){
                            setTravelTime(travelTimes.auto)
                        }else{
                            setTravelTime(travelTimes.lopend)
                        }
                        setTransportMode(event.target.value)
                    }}>
                        <option value="lopend">lopend</option>
                        <option value="openbaar vervoer">Openbaar vervoer</option>
                        <option value="auto">Auto</option>
                    </select>
                </div>

            </div>
            <a className="route-button" href={`https://google.com/maps/dir//${venueInformation.latitude},${venueInformation.longitude}/`} rel="noreferrer">
                Route
                <img src={routeIcon} alt="compass" width="50px" height="auto"/>
            </a>
        </div>
    )
}

export default VenueCard;
