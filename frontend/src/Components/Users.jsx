
import { useEffect, useState } from 'react';
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import { useNavigate } from 'react-router';
import useAuth from '../hooks/useAuth';


const Users = () => {
    const [users, setUsers] = useState([]);
    const axiosPrivate = useAxiosPrivate()
    const navigate = useNavigate();
    const { doLogout } = useAuth();

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const getUsers = async () => {
            try {

                const response = await axiosPrivate.get('/user', {

                    signal: controller.signal,
                });

                if (isMounted) {
                    setUsers(response.data.userList);
                }
            } catch (err) {
                doLogout();

            }
        }

        getUsers();

        return () => {
            isMounted = false;
            controller.abort();
        }

    }, [])


    return (
        <article>
            <h2>Users List</h2>
            {users?.length

                ? (<ul>
                    {users.map((item, i) => {
                        console.log(i);
                        console.log(users);
                        return <li key={i}>{item.user.username}</li>
                    })}
                </ul>)

                : (<div>No Users to show</div>)
            }



        </article>

    )


};

export default Users