import axios from 'axios';

const API_URL = 'http://localhost:3000/api/game';

const getAppDetails = (appid) => {
    return axios.get(`${API_URL}/${appid}`);
};

const getPricing = (appid) => {
    return axios.get(`${API_URL}/price/${appid}`);
};


const SteamStoreService = {
    getAppDetails,
    getPricing,
};

export default SteamStoreService;
