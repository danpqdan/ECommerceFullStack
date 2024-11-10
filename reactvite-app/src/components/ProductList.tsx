import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchProdutosComCache } from '../hooks/useProdutosComCache';
import BarraDePesquisa from '../features/BarraDePesquisa';

export const ProductList = () => {
    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);
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

    const ProdutoCardInfo = ({ id, nome, imagem }) => {
        return (
            <button id="card-produto" onClick={() => navigate(`/produto/${id}`, { state: { id, nome, imagem } })}>
                <img src={imagem.urlPrincipal} alt={nome} id="imgMiniatura" />
                <h2>{nome}</h2>
                <p>Price: ${typeof preco === 'number' ? preco.toFixed(2) : 'Indisponível'}</p>
            </button>
        );
    };

    const filtrarProdutosPorCategoria = (categoriasSelecionadas) => {
        const filtrados = products.filter(produto =>
            categoriasSelecionadas.includes(produto.categoria.categoria)
        );
        setFilteredProducts(filtrados);
    };

    const ordenarProdutos = (criterio) => {
        console.log("Critério de ordenação:", criterio);
        let produtosOrdenados = [...filteredProducts];
        switch (criterio) {
            case "priceAsc":
                produtosOrdenados.sort((a, b) => parseFloat(a.preco) - parseFloat(b.preco));
                break;
            case "priceDesc":
                produtosOrdenados.sort((a, b) => parseFloat(b.preco) - parseFloat(a.preco));
                break;
            case "sold":
                produtosOrdenados.sort((a, b) => b.vendidos - a.vendidos);
                break;
            case "newest":
                produtosOrdenados.sort((a, b) => new Date(b.data) - new Date(a.data));
                break;
            default:
                break;
        }
        setFilteredProducts(produtosOrdenados);
    };

    const buscarProdutosPorNome = (nome) => {
        const filtrados = products.filter(produto =>
            produto.nome.toLowerCase().includes(nome.toLowerCase())
        );
        setFilteredProducts(filtrados.length > 0 ? filtrados : products);
    };

    return (
        <div id="corpo">
            <BarraDePesquisa onFiltrar={filtrarProdutosPorCategoria} onOrdenar={ordenarProdutos} onBuscar={buscarProdutosPorNome} />
            <div id="corpoProdutoLista">
                <ul id="ListaProduto">
                    {filteredProducts.map(product => (
                        <li key={product.id}>
                            <ProdutoCardInfo {...product} />
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default ProductList;
