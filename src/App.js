
import './App.css';
import HomePage from "./pages/home/HomePage";
import VenuePage from "./pages/venue/VenuePage";
import SignUpPage from "./pages/signUp/SignUpPage";
import LoginPage from "./pages/login/LoginPage";
import RoutePage from "./pages/route/RoutePage";
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';

function App() {
  return (
    <div id="app">
        <Routes>
            <Route exact path="/" element=<HomePage/>/>
            </Routes>
    </div>
  );
}

export default App;
