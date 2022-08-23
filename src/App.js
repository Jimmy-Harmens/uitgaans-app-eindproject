import React, {useContext, useEffect, useState} from "react";
import './App.css';
import HomePage from "./pages/home/HomePage";
import VenuePage from "./pages/venue/VenuePage";
import SignUpPage from "./pages/signUp/SignUpPage";
import LoginPage from "./pages/login/LoginPage";
import RoutePage from "./pages/route/RoutePage";
import {BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import ContextProvider from "./context/Context";
import jwtDecode from "jwt-decode";



function App() {
    const [user, setUser] = useState({})
    useEffect(() => {
        if(localStorage.getItem("token")){
            setUser({username : jwtDecode(localStorage.getItem("token")).sub})
        }
    },[])

  return (
    <div id="app">
        <Router>
            {/*<ContextProvider>*/}
                <Routes>
                    <Route
                        path="/login"
                        element={!localStorage.getItem("token") ?
                            <LoginPage/> :
                            <Navigate replace to="/home"/>}
                    />
                    <Route
                        path="/signup"
                        element={!localStorage.getItem("token") ?
                            <SignUpPage/> :
                            <Navigate replace to="/home"/>}
                    />
                    <Route
                        path={"/home"}
                        element={localStorage.getItem("token") ?
                        <HomePage user={user}/> :
                        <Navigate replace to="/login"/>}
                    />
                    <Route path={"/venue/:venueName"} element={localStorage.getItem("token") ?
                        <VenuePage user={user}/> :
                        <Navigate replace to="/login"/>}
                    />
                    <Route path="/*" element={<Navigate replace to="/login"/>}/>
                </Routes>
            {/*</ContextProvider>*/}
        </Router>
    </div>
  );
}

export default App;
