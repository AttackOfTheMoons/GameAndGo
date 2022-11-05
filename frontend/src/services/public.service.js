import axios from 'axios';

const API_URL = 'http://localhost:3000/api';

const getPublicContent = (lastAppID) => {
    const params = {
        lastAppID,
    };
    return axios.get(API_URL + '/all/', {params});
};

const searchGame = (term) => {
    const params = {
        term,
    };
    return axios.get(API_URL + '/all/find', {params});
};


const PublicService = {
    getPublicContent,
    searchGame,
};

export default PublicService;
