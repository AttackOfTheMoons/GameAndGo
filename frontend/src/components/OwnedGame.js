import React, {useEffect, useState} from 'react';
import SteamStoreService from '../services/steamstore.service';
import PropTypes from 'prop-types';

const OwnedGame = ({appid, returnGame}) => {
    const [name, setName] = useState('Loading name...');
    useEffect(() => {
        SteamStoreService.getAppDetails(appid).then(
            (response) => {
                setName(response.data[appid].data.name);
            },
        );
    }, [appid]);
    return (
        <li>
            <a href={`/game/${appid}`}>{name}</a>
            <button onClick={() => returnGame(appid)}> Return </button>
        </li>
    );
};

OwnedGame.propTypes = {
    appid: PropTypes.string,
    returnGame: PropTypes.func,
};

export default OwnedGame;
