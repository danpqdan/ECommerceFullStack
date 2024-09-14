import React, { useEffect, useState } from 'react';
import ProductForm from '../components/ProductForm';
import ImagemUploadForm from '../components/ProductFormCarousel';

const PainelAdmin = () => {
    const [status, setStatus] = useState('loading'); // 'loading', 'authorized', 'unauthorized', 'error'

    useEffect(() => {
        const fetchData = async () => {
            const token = localStorage.getItem('token'); // Obtenha o token JWT

            try {
                const response = await fetch('http://localhost:8080/api/admin', {
                    method: 'GET',
                    headers: {
                        'Authorization': `${token}`,
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include'
                });

                if (response.ok) {
                    setStatus('authorized');
                } else if (response.status === 403) {
                    setStatus('unauthorized');
                } else {
                    setStatus('error');
                }
            } catch (error) {
                setStatus('error');
            }
        };

        fetchData();
    }, []);

    if (status === 'loading') {
        return <p>Loading...</p>;
    }

    if (status === 'authorized') {
        return (
            <div>
                <h1>Admin Page</h1>
                <p>Welcome to the admin page!</p>
                <ProductForm />
                <ImagemUploadForm />
            </div>
        );
    }

    if (status === 'unauthorized') {
        return <p>You are not authorized to view this page.</p>;
    }

    if (status === 'error') {
        return <p>There was an error fetching the data.</p>;
    }

    return null;
};

export default PainelAdmin;
