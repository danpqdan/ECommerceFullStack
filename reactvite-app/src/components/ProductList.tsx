import { useEffect, useState } from 'react';
import BarraDePesquisa from '../features/BarraDePesquisa';
import { fetchProdutosComCache, ProdutoCardInfoProps } from '../hooks/useProdutosComCache';
import { ErrorLayout } from '../layouts/ErrorLayout';
import { ProdutoCardInfo } from './ProductCardInfo';

export const ProductList: React.FC = () => {
  const [products, setProducts] = useState<ProdutoCardInfoProps[]>([]);  // Tipando como ProdutoCardInfoProps[]
  const [filteredProducts, setFilteredProducts] = useState<ProdutoCardInfoProps[]>([]);  // Tipado como ProdutoCardInfoProps[]
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const data = await fetchProdutosComCache();
        setProducts(data);
        setFilteredProducts(data);
      } catch (error) {
        setError(error instanceof Error ? error.message : 'Unknown error');
      } finally {
        setLoading(false);
      }
    };
    fetchProducts();
  }, []);

  if (error) {
    return <ErrorLayout message={error} />;
  }
  if (loading) return <p>Loading...</p>;

  const filtrarProdutosPorCategoria = (categoriasSelecionadas: string[]) => {
    if (!Array.isArray(categoriasSelecionadas) || categoriasSelecionadas.length === 0) {
      return; // Caso não tenha categorias selecionadas, não faz nada
    }
    const filtrados = products.filter(produto =>
      categoriasSelecionadas.includes(produto.categoria.categoria)
    );
    setFilteredProducts(filtrados);
  };

  const ordenarProdutos = (criterio: string) => {
    const produtosOrdenados = [...filteredProducts];
    if (produtosOrdenados.length === 0) return;

    switch (criterio) {
      case "priceAsc":
        produtosOrdenados.sort((a, b) => {
          const precoA = parseFloat(a.preco.toString());
          const precoB = parseFloat(b.preco.toString());
          return precoA - precoB;
        });
        break;
      case "priceDesc":
        produtosOrdenados.sort((a, b) => {
          const precoA = parseFloat(a.preco.toString());
          const precoB = parseFloat(b.preco.toString());
          return precoB - precoA;
        });
        break;
      case "sold":
        produtosOrdenados.sort((a, b) => b.vendidos - a.vendidos);
        break;
      case "newest":
        produtosOrdenados.sort((a, b) => new Date(b.data).getTime() - new Date(a.data).getTime());
        break;
      default:
        break;
    }
    setFilteredProducts(produtosOrdenados);
  };

  const buscarProdutosPorNome = (nome: string) => {
    if (!nome) {
      setFilteredProducts(products);
      return;
    }
    const filtrados = products.filter(produto =>
      produto.nome.toLowerCase().includes(nome.toLowerCase())
    );
    setFilteredProducts(filtrados.length > 0 ? filtrados : products);
  };
  
  return (
    <div id="corpo">
      <BarraDePesquisa onFiltrar={filtrarProdutosPorCategoria} onOrdenar={ordenarProdutos} onBuscar={buscarProdutosPorNome} />
      <div id="corpoProdutoLista">
        {filteredProducts.length === 0 ? (
          <p>Nenhum produto encontrado</p>
        ) : (
          <ul id="ListaProduto">
            {filteredProducts.map((product) => (
              <li key={product.id}>
                <ProdutoCardInfo {...product} />
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default ProductList;
