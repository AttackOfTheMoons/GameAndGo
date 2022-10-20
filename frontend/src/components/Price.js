import React, {useEffect, useState} from 'react';
import SteamStoreService from '../services/steamstore.service';
import PropTypes from 'prop-types';
import AdminService from '../services/admin.service';


const Price = ({appid}) => {
    const [isAdmin, setAdmin] = useState(false);
    const [price, setPrice] = useState(0);
    const [newPrice, setNewPrice] = useState('');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        SteamStoreService.getPricing(appid).then((response) => {
            if (response.data.price) {
                setPrice(response.data.price);
            }
            if (response.data.admin) {
                setAdmin(response.data.admin);
            }
            setLoading(false);
        });
    }, []);
    const priceEdit = (e) => {
        e.preventDefault();
        setNewPrice(e.target.value);
    };
    const priceSubmit = (e) => {
        e.preventDefault();
        AdminService.editPrice(appid, newPrice).then((respose) => {
            setPrice(respose.data.price);
        });
    };
    let admin;
    if (isAdmin) {
        admin = (<form onSubmit={priceSubmit}>
            <label htmlFor="editPrice">Edit Price:</label>
            <input
                type="number"
                id="editPrice"
                placeholder="updated price"
                onChange={priceEdit}>
            </input>
        </form>);
    }
    let priceElement;
    if (loading) {
        return <div>Loading price...</div>;
    }
    if (price > 0) {
        priceElement = <div>{`$${price}`}</div>;
    } else {
        priceElement = <div>No price currently set</div>;
    }
    return (
        <div>
            {priceElement}
            {admin}
        </div>
    );
};

Price.propTypes = {
    appid: PropTypes.string,
};

export default Price;
