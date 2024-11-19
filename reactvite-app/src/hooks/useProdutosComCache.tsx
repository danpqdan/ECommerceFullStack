// produtoService.js
const CACHE_KEY = 'produtos_cache';
const CACHE_DURATION = 60 * 60 * 1000; // 1 hora

export interface ProdutoCardInfoProps {
    id: string;
    nome: string;
    imagem: {
        urlPrincipal: string;
    };
    preco: string | number;
    vendidos: number;
    data: string;
    categoria: {
        categoria: string;
    };
}


export const fetchProdutosComCache = async () => {
    const cache = localStorage.getItem(CACHE_KEY);
    const timestamp = localStorage.getItem(`${CACHE_KEY}_timestamp`);

    if (cache && timestamp !== undefined && timestamp !== null && !isNaN(Number(timestamp)) && (Date.now() - Number(timestamp)) < CACHE_DURATION) {

        return JSON.parse(cache);
    }

    try {
        const response = await fetch('https://backend-g8j8.onrender.com/api/produtos');
        if (!response.ok) {
            throw new Error(`Erro HTTP! status: ${response.status}`);
        }

        const produtos = await response.json();
        localStorage.setItem(CACHE_KEY, JSON.stringify(produtos));
        localStorage.setItem(`${CACHE_KEY}_timestamp`, Date.now().toString());

        return produtos;
    } catch (error) {
        console.error('Erro ao buscar produtos:', error);
        throw error;
    }
};
