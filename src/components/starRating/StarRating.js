import React, {useState} from "react";
import empty from "../../assets/Empty_Star.png";
import full from "../../assets/Full_Star.png"

function StarRating({rating, updateRating}){

    const [hoverState, setHoverState] = useState(0);
    const starSize = "50px" //dev note: verander deze om alle sterren een ander formaat geven.

    return(
        <div className="star-container">
            <img src={rating >= 1 || hoverState >= 1 ? full : empty}
                 alt="One star"
                 onMouseOver={(e) => setHoverState(1)}
                 onMouseLeave={(e) => setHoverState(0)}
                 height={starSize}
                 width={starSize}
                 onClick={(e) => updateRating(1)}
            />
            <img src={rating >= 2 || hoverState >= 2 ? full : empty}
                 alt="Two stars"
                 onMouseOver={(e) => setHoverState(2)}
                 onMouseLeave={(e) => setHoverState(0)}
                 height={starSize}
                 width={starSize}
                 onClick={(e) => updateRating(2)}
            />
            <img src={rating >= 3 || hoverState >= 3 ? full : empty}
                 alt="Three stars"
                 onMouseOver={(e) => setHoverState(3)}
                 onMouseLeave={(e) => setHoverState(0)}
                 height={starSize}
                 width={starSize}
                 onClick={(e) => updateRating(3)}
            />
            <img src={rating >= 4 || hoverState >= 4 ? full : empty}
                 alt="Four stars"
                 onMouseOver={(e) => setHoverState(4)}
                 onMouseLeave={(e) => setHoverState(0)}
                 height={starSize}
                 width={starSize}
                 onClick={(e) => updateRating(4)}
            />
            <img src={rating === 5 || hoverState === 5 ? full : empty}
                 alt="Five stars"
                 onMouseOver={(e) => setHoverState(5)}
                 onMouseLeave={(e) => setHoverState(0)}
                 height={starSize}
                 width={starSize}
                 onClick={(e) => updateRating(5)}
            />
        </div>
    )
}

export default StarRating;