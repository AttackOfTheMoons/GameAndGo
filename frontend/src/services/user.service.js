import axios from 'axios';

const API_URL = 'http://localhost:3000/api/user';

const purchase = (appid) => {
    return axios.post(API_URL + '/buy', {appid});
};

const checkForPurchase = (appid) => {
    return axios.get(`${API_URL}/game/${appid}`);
};

const returnPurchase = (appid) => {
    return axios.delete(`${API_URL}/game/${appid}`);
};

const getUserBoard = () => {
    return axios.get(API_URL + '/games');
};


const UserService = {
    purchase,
    checkForPurchase,
    returnPurchase,
    getUserBoard,
};

export default UserService;
