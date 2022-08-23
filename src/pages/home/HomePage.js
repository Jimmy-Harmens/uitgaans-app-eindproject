import React, {useEffect, useState} from "react";
import Header from "../../components/header/Header"
import Searchbar from "../../components/searchbar/Searchbar";
import LogoutButton from "../../components/logoutButton/LogoutButton";
import LinkButton from "../../components/linkButton/LinkButton";
import "./HomeStyle.css"
import StarRating from "../../components/starRating/StarRating";
import ToggleSwitch from "../../components/toggleSwitch/ToggleSwitch"
import StAppSuggestion from "../../components/stAppSuggestion/StAppSuggestion";
import minutesToStandard from "../../helpers/MinutesToStandard";
import axios from "axios"

import clubUpTemp from "../../assets/Club_up_temp.jpg"
import clubUpLogo from "../../assets/ClubUp_logo_temp.png"

function HomePage({user}){

    const [topSelector, setTopSelector] = useState("alles");
    const [city, setCity] = useState("Amsterdam");
    const [rating, setRating] =useState(0);
    const [isHungry, toggleHungry] = useState(false);
    const [isThirsty, toggleThirsty] = useState(false);
    const [happyHour, toggleHappyHour] = useState(false);
    const [evenementen, toggleEvenementen] = useState(false);
    const [liveMuziek, toggleLiveMuziek] = useState(false);

    const [recommendations, setRecommendations] = useState([])

    const stAppTypes = ["alles","Kroeg", "Club", "Bioscoop", "Restaurant", "Bar", "Cafe"]
    useEffect(() => {
        async function getRecommendations(){
            if(user.username) {
                try {
                    const response = await axios.get('http://localhost:8080/location', {
                        headers : {
                            Authorization : "Bearer " + localStorage.getItem("token")
                        },
                        params: {
                            "city": city,
                            "latitude": 52.29397116059169,
                            "longitude": 4.871916997258991,
                            "username": user.username,
                            "isHungry": isHungry,
                            "isThirsty": isThirsty
                        }
                    })
                    setRecommendations(response.data)
                    console.log(response.data)
                } catch (e) {
                    console.log(e)
                }
            }
        }

        getRecommendations()
    },[city, isHungry, isThirsty,user])

    return(
        <div className="page">
            <Header classname="header" navItems={[<Searchbar/>, <LogoutButton/>]} user={user.username}/>
            <div className="home-content">
                <nav className="home-options-top">
                    {stAppTypes.map((type) => {
                        return(
                            <LinkButton onClick={ (e) => {setTopSelector(type)
                                console.log(type)}} text={type} key={type}/>
                        )
                    })}
                </nav>
                <div className="home-content-bottom">
                    <aside className="home-options-side">
                        <h2>{city}</h2>
                        <div className="consume-container">
                            <div className="hunger-thirst-container">
                                <h4>Ik heb honger</h4>
                                <ToggleSwitch toggleFunction={toggleHungry} toggleState={isHungry}/>
                            </div>
                            <div className="hunger-thirst-container">
                                <h4>Ik heb dorst</h4>
                                <ToggleSwitch toggleFunction={toggleThirsty} toggleState={isThirsty}/>
                            </div>

                        </div>
                        <h3>Beoordelingen</h3>
                        <StarRating rating={rating} updateRating={setRating}/>
                        <h3>Acties/evenementen</h3>
                        <div className="evenementen-selector-container">
                            <div className="evenement">
                                <h4>Happy hour</h4>
                                <input type="checkbox" className="check-box" onClick={(e) => toggleHappyHour}/>
                            </div>
                            <div className="evenement">
                                <h4>Evenementen</h4>
                                <input type="checkbox" className="check-box" onClick={(e) => toggleEvenementen}/>
                            </div>
                            <div className="evenement">
                                <h4>Live muziek</h4>
                                <input type="checkbox" className="check-box" onClick={(e) => toggleLiveMuziek}/>
                            </div>
                        </div>
                    </aside>
                    <section className="my-list-results">
                        {recommendations.sort((a,b) => {return b.recommendationScore - a.recommendationScore}).map((recommendation) => {
                            if (recommendation.recommendationScore > 0) {
                                return <StAppSuggestion stAppLocation={{
                                    locationPhoto: clubUpTemp, //to do
                                    logo: clubUpLogo, //to do
                                    locationName: recommendation.locationName,
                                    locationType: recommendation.typeLocation,
                                    averageRating: recommendation.reviewAverage,
                                    amountOfRatings: recommendation.reviewAmount,
                                    openingTime: minutesToStandard(recommendation.openingTime),
                                    closingTime: minutesToStandard(recommendation.closingTime),
                                    distance: recommendation.distance,//to do
                                    locationID: recommendation.locationId

                                }} user={user} key={recommendation.locationName}/>
                            }else return("")
                        })}
                    </section>
                </div>
            </div>
        </div>
    )
}

export default HomePage;