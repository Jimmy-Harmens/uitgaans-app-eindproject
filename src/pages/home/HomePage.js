import React, {useEffect, useState} from "react";
import Header from "../../components/header/Header"
import Searchbar from "../../components/searchbar/Searchbar";
import LogoutButton from "../../components/logoutButton/LogoutButton";
import LinkButton from "../../components/linkButton/LinkButton";
import "./HomeStyle.css"
import StarRating from "../../components/starRating/StarRating";

function HomePage(){

    const [topSelector, setTopSelector] = useState("alles");
    const [city, setCity] = useState("Utrecht")
    const [rating, setRating] =useState(0)

    const stAppTypes = ["alles","Kroeg", "Club", "Bioscoop", "Restaurant", "Bar", "Cafe"]
    useEffect(() => function(){
        // hier een axios api call naar de backend voor alle aanbevelingen.
    },[topSelector])

    return(
        <div className="page">
            <Header classname="header" navItems={[<Searchbar/>, <LogoutButton/>]}/>
            <div className="home-content">
                <div className="home-options-top">
                    {stAppTypes.map((type) => {
                        return(
                            <LinkButton onClick={ (e) => {setTopSelector(type)
                                console.log(type)}} text={type} key={type}/>
                        )
                    })}
                </div>
                <div className="home-content-bottom">
                    <div className="home-options-side">
                        <h2>{city}</h2>
                        <h4>Ik heb honger</h4>
                        <StarRating rating={rating} updateRating={setRating}/>
                    </div>
                    <div className="my-list-results">
                        <h4>hello</h4>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default HomePage;