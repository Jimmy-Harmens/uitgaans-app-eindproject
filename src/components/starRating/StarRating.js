import React from "react";

function StarSvg(){
    return(
        <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
            <symbol id="star" viewBox="214.7 0 182.6 792"/>
        </svg>
    )
}

function StarRating({rating}){
    return(
        <div className="star-container">
            <img src="" alt=""/>
            <img src="" alt=""/>
            <img src="" alt=""/>
            <img src="" alt=""/>
            <img src="" alt=""/>
        </div>
    )
}

export default StarRating;