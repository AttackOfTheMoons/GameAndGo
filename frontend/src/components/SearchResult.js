import React from 'react';
import {useNavigate} from 'react-router-dom';
import PropTypes from 'prop-types';
import '../SearchResult.css';

const SearchResult = ({name, image, appid}) => {
    const navigate = useNavigate();

    const searchClicked = (e) => {
        e.preventDefault();
        navigate(`/game/${appid}`);
        window.location.reload();
    };

    return (
        <li className='resultListItem'>
            <a
                href={`/game/${appid}`}
                onClick={searchClicked}
                className='searchResult'
            >
                <img src={image} alt={`cover photo for ${name}`}></img>
                <span className='searchName'>{name}</span>
            </a>
        </li>
    );
};

SearchResult.propTypes = {
    appid: PropTypes.string,
    name: PropTypes.string,
    image: PropTypes.string,
};

export default SearchResult;
