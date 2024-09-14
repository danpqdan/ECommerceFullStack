// ProductList.js
import React, { useState, useEffect } from 'react';
import './CSS/produtoscss.css'

const ProductList = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProducts = async () => {
            const token = localStorage.getItem('token'); // Ou onde você estiver armazenando o token

            try {
                const response = await fetch('http://localhost:8080/api/produtos', {
                    method: 'GET',
                    headers: {
                        'Authorization': `${token}`,
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include' // Inclua isso se você estiver usando cookies para autenticação
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setProducts(data);
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error loading products: {error.message}</p>;

    return (
        <div>
            <h1>Product List</h1>
            <ul>
                {products.map(product => (
                    <button id='card-produto'>

                        <li key={product.id}>
                            <h2>{product.nome}</h2>
                            <p>
                                Price: ${typeof product.preco === 'number' ? product.preco.toFixed(2) : 'N/A'}
                            </p>
                        </li>
                    </button>
                ))}
            </ul>
        </div>
    );
};

export default ProductList;
