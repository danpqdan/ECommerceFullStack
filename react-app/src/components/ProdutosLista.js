// ProductList.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './CSS/produtoscss.css';
import BarraDePesquisa from './BarraDePesquisa';

const ProductList = ({ product }) => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProducts = async () => {
            const token = localStorage.getItem('token');

            try {
                const response = await fetch('http://localhost:8080/api/produtos', {
                    method: 'GET',
                    // headers: {
                    //     'Authorization': `${token}`,
                    //     'Content-Type': 'application/json'
                    // },
                    // credentials: 'include' // Inclua isso se você estiver usando cookies para autenticação
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

    const ProdutoCardInfo = (product) => {
        // Redireciona o usuário e passa o objeto `product` no state
        navigate(`/produto/${product.id}`, { state: { product } });
    };


    return (
        <div id='corpo'>
            <BarraDePesquisa />

            <div id='corpoProdutoLista'>

                <ul id='ListaProduto'>

                    {products.map(product => (
                        <li key={product.id}>
                            <button id='card-produto' onClick={() => ProdutoCardInfo(product)}>
                                <img src={product.imagem.urlPrincipal} id='imgMiniatura' />
                                <h2>{product.nome}</h2>
                                <p>
                                    Price: ${typeof product.preco === 'number' ? product.preco.toFixed(2) : 'N/A'}
                                </p>
                            </button>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default ProductList;
