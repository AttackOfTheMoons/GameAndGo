import React, {useState, useEffect} from 'react';

import EventBus from '../common/EventBus';
import UserListItem from './UserListItem';
import AdminService from '../services/admin.service';

const BoardAdmin = () => {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState('Loading users...');

    useEffect(() => {
        AdminService.getAdminBoard().then(
            (response) => {
                if (response.status === 200) {
                    setUsers(response.data);
                    if (response.data.length === 0) {
                        setError('No users exist!');
                    } else {
                        setError('');
                    }
                }
            },
        )
            .catch((error) => {
                const _content =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
                setError(_content);
                EventBus.dispatch('logout');
            });
    }, []);

    const delUser = (userId) => {
        AdminService.deleteUser(userId).then(() => {
            setUsers(users.filter((user) => user.id !== userId));
        });
    };

    if (error) {
        return (<div>{error}</div>);
    } else {
        return (
            <div className="container">
                <table>
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.map(
                            (user) =>
                                <UserListItem
                                    key={user.id}
                                    username={user.username}
                                    email={user.email}
                                    delUser={() => delUser(user.id)}/>,
                        )}
                    </tbody>
                </table>
            </div>
        );
    }
};

export default BoardAdmin;
