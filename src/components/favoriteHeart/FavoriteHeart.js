import React, {useEffect, useState} from "react";
import "./FavoriteHeartStyle.css"
import full from "../../assets/Full_Heart.png"
import empty from "../../assets/Empty_Heart.png"
import axios from "axios";

function FavoriteHeart({venueId, username}){
    const [filled, toggleFilled] = useState(false)
    const [testDependency, activateTestDependency] = useState(true)
    useEffect((e) => {
        async function isFavorite(){
            try{
                if(venueId && username){
                    const response = await axios.get("http://localhost:8080/favorite", {
                        params : {
                            username : username,
                            locationId : venueId
                        }
                    })
                    console.log(response.data)
                    toggleFilled(response.data)
                }
            }catch (e) {
                console.log(e)
            }
        }
        isFavorite()
    },[testDependency,username,venueId])
    return(
        <div id="heart-container" onClick={(e) => {
            async function toggleFavorite(){
                try{
                    await axios.post("http://localhost:8080/favorite", {},{
                        params: {
                            username : username,
                            locationId : venueId
                        }});
                    activateTestDependency(!testDependency)
                }catch (e) {
                    console.log(e)
                }
            }
            toggleFavorite()
        }}>
            {filled ? <img src={full} alt="favoriet!" width="40px" height="40px"/> : <img src={empty} alt="geen favoriet" width="40px" height="40px"/>}
        </div>
    )
}

export default FavoriteHeart;