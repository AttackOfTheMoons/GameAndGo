import React from 'react';
import UserService from '../services/user.service';
import PropTypes from 'prop-types';

const PurchaseOrReturn = ({appid, owned, setNotification, setOwned}) => {
    const purchase = () => {
        UserService.purchase(appid).then(
            (response) => {
                if (response.status === 200) {
                    setNotification(response.data, true);
                    setOwned(true);
                }
            },
        )
            .catch((error) => {
                if (error.response.data.message) {
                    setNotification(error.response.data.message, false);
                    setOwned(true);
                } else {
                    setNotification(error.message, false);
                }
            });
    };
    const returnGame = () => {
        UserService.returnPurchase(appid)
            .then(
                (response) => {
                    if (response.status === 204) {
                        setNotification('Return successful!', true);
                        setOwned(false);
                    } else {
                        setNotification(response.message, false);
                    }
                })
            .catch((error) => {
                if (error.response.data.message) {
                    setNotification(error.response.data.message, false);
                    setOwned(false);
                } else {
                    setNotification(error.message, false);
                }
            });
    };

    if (!owned) {
        return <button onClick={purchase}>Purchase</button>;
    } else {
        return <div>
            You already own this game.
            <button onClick={returnGame}>
                Return
            </button>
        </div>;
    }
};

PurchaseOrReturn.propTypes = {
    appid: PropTypes.string,
    setNotification: PropTypes.func,
    owned: PropTypes.bool,
    setOwned: PropTypes.func,
};

export default PurchaseOrReturn;
