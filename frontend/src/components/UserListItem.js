import React from 'react';
import PropTypes from 'prop-types';

const UserListItem = ({username, email, delUser}) => {
    return <tr>
        <td>{username}</td>
        <td>{email}</td>
        <td><button onClick={delUser}>Delete</button></td>
    </tr>;
};

UserListItem.propTypes = {
    username: PropTypes.string,
    email: PropTypes.string,
    delUser: PropTypes.func,
};

export default UserListItem;
