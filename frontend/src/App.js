import React, {useState, useEffect} from 'react';
import {Routes, Route, Link} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import AuthService from './services/auth.service';

import Login from './components/Login';
import Register from './components/Register';
import Home from './components/Home';
import Profile from './components/Profile';
import BoardUser from './components/BoardUser';
import BoardAdmin from './components/BoardAdmin';

import EventBus from './common/EventBus';
import GamePage from './components/GamePage';

import {useNavigate} from 'react-router-dom';

const App = () => {
    const [showAdminBoard, setShowAdminBoard] = useState(false);
    const [currentUser, setCurrentUser] = useState(undefined);

    const navigate = useNavigate();

    useEffect(() => {
        const user = AuthService.getCurrentUser();

        if (user) {
            setCurrentUser(user);
            setShowAdminBoard(user.roles.includes('ROLE_ADMIN'));
        }

        EventBus.on('logout', () => {
            logOut();
        });

        return () => {
            EventBus.remove('logout');
        };
    }, []);

    const logOut = () => {
        AuthService.logout();
        setShowAdminBoard(false);
        setCurrentUser(undefined);
        navigate('/login');
    };

    return (
        <div>
            <nav className="navbar navbar-expand navbar-dark bg-dark">
                <Link to={'/'} className="navbar-brand">
                    Game And Go
                </Link>
                <div className="navbar-nav mr-auto">
                    <li className="nav-item">
                        <Link to={'/home'} className="nav-link">
                            Home
                        </Link>
                    </li>

                    {showAdminBoard && (
                        <li className="nav-item">
                            <Link to={'/admin'} className="nav-link">
                                Admin Board
                            </Link>
                        </li>
                    )}

                    {currentUser && (
                        <li className="nav-item">
                            <Link to={'/user'} className="nav-link">
                                My Games
                            </Link>
                        </li>
                    )}
                </div>

                {currentUser ? (
                    <div className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link to={'/profile'} className="nav-link">
                                {currentUser.username}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <a
                                href="/login"
                                className="nav-link"
                                onClick={(e) => {
                                    e.preventDefault(); logOut();
                                }}
                            >
                                LogOut
                            </a>
                        </li>
                    </div>
                ) : (
                    <div className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link to={'/login'} className="nav-link">
                                Login
                            </Link>
                        </li>

                        <li className="nav-item">
                            <Link to={'/register'} className="nav-link">
                                Sign Up
                            </Link>
                        </li>
                    </div>
                )}
            </nav>

            <div className="container mt-3">
                <Routes>
                    <Route exact path={'/'} element={<Home />} />
                    <Route exact path={'/home'} element={<Home />} />
                    <Route exact path="/login" element={<Login />} />
                    <Route exact path="/register" element={<Register />} />
                    <Route exact path="/profile" element={<Profile />} />
                    <Route path="/user" element={<BoardUser />} />
                    <Route path="/admin" element={<BoardAdmin />} />
                    <Route path="/game/:appid" element={<GamePage />} />
                </Routes>
            </div>
        </div>
    );
};

export default App;
