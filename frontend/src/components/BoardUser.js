import React, {useState, useEffect} from 'react';

import EventBus from '../common/EventBus';
import UserService from '../services/user.service';
import OwnedGame from './OwnedGame';

const BoardUser = () => {
    const [content, setContent] = useState([]);
    const [notification, setNotification] = useState('');
    const [success, setSuccess] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        UserService.getUserBoard()
            .then(
                (response) => {
                    setContent(response.data);
                    setLoading(false);
                })
            .catch((error) => {
                EventBus.dispatch('logout');
            });
    }, []);

    const returnGame = (appid) => {
        setContent(content.filter((id) => id !== appid));
        UserService.returnPurchase(appid);
        setNotificationBox('Return successful!', true);
    };

    const setNotificationBox = (message, success) => {
        setNotification(message);
        setSuccess(success);
        setTimeout(() => {
            setNotification('');
        }, 5000);
    };
    if (loading) {
        return <div>Loading games...</div>;
    }

    if (content.length === 0) {
        return (<div>No games owned!</div>);
    } else {
        return (
            <div className="container">
                {notification &&
              <div
                  className={success ?
                      'alert alert-success' :
                      'alert alert-danger'}>
                  {notification}
              </div>}
                <ul>
                    {content.map((id) =>
                        <OwnedGame
                            key={id}
                            appid={id}
                            returnGame={returnGame}/>)}
                </ul>
            </div>
        );
    }
};

export default BoardUser;
