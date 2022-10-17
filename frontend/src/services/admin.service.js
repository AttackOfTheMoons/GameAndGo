import axios from 'axios';

const API_URL = 'http://localhost:3000/api/admin';

const getAdminBoard = () => {
    return axios.get(API_URL + '/users');
};

const deleteUser = (id) => {
    const data = {id};
    return axios.delete(API_URL + '/user', {data});
};

const editPrice = (appid, newPrice) => {
    const data = {newPrice};
    return axios.post(`${API_URL}/game/${appid}`, data);
};

const AdminService = {
    getAdminBoard,
    deleteUser,
    editPrice,
};

export default AdminService;
