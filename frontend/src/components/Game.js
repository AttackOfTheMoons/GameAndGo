import React from 'react';
import {useNavigate} from 'react-router-dom';
import '../Game.css';
import PropTypes from 'prop-types';

const Game = ({appid}) => {
    const navigate = useNavigate();

    const style = {
        backgroundImage: `url(https://cdn.cloudflare.steamstatic.com/steam/apps/${appid}/header.jpg)`,
    };

    const gameClicked = (appid) => {
        navigate(`/game/${appid}`);
        window.location.reload();
    };

    return <div
        style={style}
        className={'gamePreviewBox'}
        onClick={() => gameClicked(appid)}
    >
    </div>;
};
Game.propTypes = {
    appid: PropTypes.number,
};

export default Game;
