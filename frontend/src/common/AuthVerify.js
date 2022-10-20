import React from 'react';
import {withRouter} from 'react-router-dom';
import PropTypes from 'prop-types';

const parseJwt = (token) => {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
};

const AuthVerify = ({history, logOut}) => {
    history.listen(() => {
        const user = JSON.parse(localStorage.getItem('user'));

        if (user) {
            const decodedJwt = parseJwt(user.accessToken);

            if (decodedJwt.exp * 1000 < Date.now()) {
                logOut();
            }
        }
    });

    return <div></div>;
};

AuthVerify.propTypes = {
    history: PropTypes.any,
    logOut: PropTypes.func,
};

export default withRouter(AuthVerify);
