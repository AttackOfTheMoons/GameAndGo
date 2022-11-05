import React, {useState} from 'react';
import PublicService from '../services/public.service';
import SearchResult from './SearchResult';
const SearchBox = () => {
    const [searchInput, setSearchInput] = useState('');
    const [searchResults, setSearchResults] = useState([]);

    const inputChange = (e) => {
        e.preventDefault();
        setSearchInput(e.target.value);
        if (searchInput === '') {
            setSearchResults([]);
            return;
        }
        PublicService.searchGame(searchInput).then((results) => {
            if (results.status === 200) {
                setSearchResults(results.data);
            }
        });
    };

    const results = () => {
        if (!searchResults || searchResults.length === 0) {
            return;
        }
        return (
            <ul className='resultsBox'>
                {searchResults.map(({appID, matchName, imageURL}, index) =>
                    (<SearchResult
                        key={index}
                        appid={appID}
                        name={matchName}
                        image={imageURL}
                    />))}
            </ul>
        );
    };

    return (
        <div>
            <input
                placeholder='search'
                value={searchInput}
                onChange={inputChange}
                type='text'
                name='search'
            />
            {results()}
        </div>
    );
};

export default SearchBox;
