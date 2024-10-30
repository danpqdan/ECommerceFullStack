import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchProdutosComCache } from '../../../hook/produtosEmCache'

const ListarProduto = ({ product }) => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();


    const ProdutoCardInfo = (product) => {
        // Redireciona o usuário e passa o objeto `product` no state
        navigate(`/produto/${product.id}`, { state: { product } });
    };


    useEffect(() => {
        const fetchProducts = async () => {

            try {
                const data = await fetchProdutosComCache();
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
            <ul id='ListaProduto'>
                {products.map(product => (
                    <li key={product.id}>
                        <button id='card-produto' onClick={() => ProdutoCardInfo(product)}>

                            <img src={product.imagem.urlPrincipal} alt={product.nome} width="200" />
                            <h2>{product.nome}</h2>
                            <p>
                                Price: ${typeof product.preco === 'number' ? product.preco.toFixed(2) : 'N/A'}
                            </p>
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ListarProduto;
