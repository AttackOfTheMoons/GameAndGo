import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import AuthService from '../services/auth.service';
import SteamStoreService from '../services/steamstore.service';
import UserService from '../services/user.service';
import PurchaseOrReturn from './PurchaseOrReturn';
import Price from './Price';

const GamePage = () => {
    const {appid} = useParams();
    const [name, setName] = useState('');
    const [image, setImage] = useState('');
    const [description, setDescription] = useState('');
    const [notification, setNotification] = useState('');
    const [success, setSuccess] = useState(false);
    const [loading, setLoading] = useState(true);
    const [notFound, setNotFound] = useState(false);
    const [owned, setOwned] = useState(false);

    const currentUser = AuthService.getCurrentUser();


    useEffect(() => {
        SteamStoreService.getAppDetails(appid).then((response) => {
            if (response.data[appid].success) {
                const gameInfo = response.data[appid].data;
                setName(gameInfo.name);
                setImage(gameInfo.header_image);
                setDescription(gameInfo.short_description);
                setLoading(false);
            } else {
                setNotFound(true);
                setLoading(false);
            }
        });
        if (currentUser) {
            UserService.checkForPurchase(appid).then(
                (response) => {
                    if (response.status === 200) {
                        setOwned(response.data);
                    }
                },
                (error) => {
                    const _content = (error.response && error.response.data) ||
                                error.message ||
                                error.toString();
                    handleNotification(_content, false);
                },
            );
        }
    }, []);

    const handleNotification = (message, success) => {
        setNotification(message);
        setSuccess(success);
        setTimeout(() => {
            setNotification('');
        }, 5000);
    };

    if (loading) {
        return <div>Loading game information...</div>;
    } else if (notFound) {
        return <div>{`Game with appid: ${appid} not found`}</div>;
    } else {
        return (
            <div>
                {notification &&
                <div className={success ?
                    'alert alert-success' :
                    'alert alert-danger'}>
                    {notification}
                </div>}
                <img src={image} alt={`Header for ${name}`}></img>
                <h3>{name}</h3>
                <p>{description}</p>
                {currentUser &&
                <PurchaseOrReturn
                    appid={appid}
                    setNotification={(message, success) =>
                        handleNotification(message, success)}
                    owned={owned}
                    setOwned={setOwned}/>
                }
                <Price appid={appid}></Price>
            </div>
        );
    }
};

export default GamePage;
