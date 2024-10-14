import { useEffect, useState } from 'react';

const useFetchData = () => {
    const [status, setStatus] = useState(null);
    const [userRole, setUserRole] = useState(null);
    useEffect(() => {
        const fetchData = async () => {
            const token = localStorage.getItem('token');
            const role = localStorage.getItem('role');

            try {
                const response = await fetch('http://localhost:8080/auth/login', {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include'
                });

                if (response.ok) {
                    const data = await response.json();
                    setStatus('success');
                    setUserRole(data.role);
                } else if (response.status === 403) {
                    setStatus(response.getItem('role'));
                } else {
                    setStatus('error');
                }
            } catch (error) {
                setStatus('error');
            }
        };

        fetchData();
    }, []);

    return { status, userRole };
};

const isAdmin = () => {
    const role = localStorage.getItem('role');
    return role === 'admin'; // Supondo que 'admin' seja o valor da role para administradores
};


export default useFetchData;
