// ProductList.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './CSS/produtoscss.css';
import BarraDePesquisa from './BarraDePesquisa';
import { fetchProdutosComCache } from '../hook/produtosEmCache';

const ProductList = ({ product }) => {
    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]); // Produtos filtrados

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const data = await fetchProdutosComCache();
                setProducts(data);
                setFilteredProducts(data);

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
        navigate(`/produto/${product.id}`, { state: { product } });
    };

    const filtrarProdutosPorCategoria = (categoriasSelecionadas) => {
        const filtrados = products.filter(produto =>
            categoriasSelecionadas.includes(produto.categoria.categoria)
        );
        setFilteredProducts(filtrados);
    };

    const ordenarProdutos = (criterio) => {
        console.log("Critério de ordenação:", criterio); // Log do critério de ordenação

        let produtosOrdenados = [...filteredProducts]; // Faz uma cópia dos produtos filtrados

        switch (criterio) {
            case "priceAsc":
                produtosOrdenados.sort((a, b) => parseFloat(a.preco) - parseFloat(b.preco));
                break;
            case "priceDesc":
                produtosOrdenados.sort((a, b) => parseFloat(b.preco) - parseFloat(a.preco));
                break;
            case "sold":
                // Supondo que há uma propriedade "vendidos" em cada produto
                produtosOrdenados.sort((a, b) => b.vendidos - a.vendidos);
                break;
            case "newest":
                // Supondo que há uma propriedade "data" ou "criadoEm" em cada produto
                produtosOrdenados.sort((a, b) => new Date(b.data) - new Date(a.data));
                break;
            default:
                break;
        }

        setFilteredProducts(produtosOrdenados); // Atualiza o estado com os produtos ordenados
    };

    const buscarProdutosPorNome = (nome) => {
        console.log("Buscando produtos por nome:", nome); // Log para verificar o termo de busca
        const filtrados = products.filter(produto =>
            produto.nome.toLowerCase().includes(nome.toLowerCase()) // Faz a busca, ignorando maiúsculas/minúsculas
        );
        setFilteredProducts(filtrados.length > 0 ? filtrados : products); // Se nenhum produto encontrado, mostra todos
    };

    return (
        <div id='corpo'>
            <BarraDePesquisa onFiltrar={filtrarProdutosPorCategoria} onOrdenar={ordenarProdutos} onBuscar={buscarProdutosPorNome} />

            <div id='corpoProdutoLista'>

                <ul id='ListaProduto'>

                    {filteredProducts.map(product => (
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
