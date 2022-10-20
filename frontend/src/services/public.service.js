import axios from 'axios';

const API_URL = 'http://localhost:3000/api';

const getPublicContent = (lastAppID) => {
    const params = {
        lastAppID,
    };
    return axios.get(API_URL + '/all/', {params});
};


const PublicService = {
    getPublicContent,
};

export default PublicService;
