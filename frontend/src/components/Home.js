import React, {useState, useEffect} from 'react';

import PublicService from '../services/public.service';

import Game from './Game';
import SearchBox from './SearchBox';

const Home = () => {
    const [error, setError] = useState('Loading games');
    const [games, setGames] = useState([]);
    const [lastAppID, setLastAppID] = useState(0);
    const [appIDHistory, setHistory] = useState([]);

    useEffect(() => {
        PublicService.getPublicContent(lastAppID).then(
            (response) => {
                if (response.data.response.apps !== undefined) {
                    setGames(response.data.response.apps);
                    setHistory((a) =>
                        a.indexOf(response.data.response.last_appid) === -1 ?
                            a.concat(response.data.response.last_appid) :
                            a,
                    );
                    setError('');
                }
            },
            (error) => {
                const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();
                setError(_content);
            },
        );
    }, [lastAppID]);

    const previous = () => {
        if (appIDHistory.length > 1) {
            setLastAppID(appIDHistory[appIDHistory.length - 3] || 0);
            setHistory(appIDHistory.slice(0, -2));
        }
    };
    const next = () => {
        if (appIDHistory.length > 0) {
            setLastAppID(appIDHistory[appIDHistory.length - 1]);
        }
    };

    return (
        <div className="container">
            {error && <header className="jumbotron"><h3>{error}</h3></header>}
            <SearchBox />
            {games && games.map(({appid}, index) =>
                <Game key={appid} appid={appid}/>,
            )}
            {(!error && appIDHistory.length > 1) &&
                <button onClick={previous}>Prev</button>}
            {!error && <button onClick={next}>Next</button>}
        </div>
    );
};

export default Home;
