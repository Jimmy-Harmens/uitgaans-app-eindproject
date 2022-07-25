import React, {useState} from "react";
import searchLogo from "../../assets/Search_Icon.svg.png"
import "./searchbarStyle.css"

function Searchbar(){
    const [searchValue, setSearchValue] = useState("")

    function onSearch(){
        //hier later functionaliteit aan toevoegen
        console.log(searchValue)
    }
    return(
        <div className="search-bar">
            <input type="text" placeholder="Vind een locatie..." onChange={(e => {setSearchValue(e.target.value)})} className="input-area"/>
            <div className="search-container">
                <img src={searchLogo} alt="vergrootglas (zoeken)" onClick={onSearch} width="30px" height="30px"/>
            </div>
        </div>
    )
}

export default Searchbar;