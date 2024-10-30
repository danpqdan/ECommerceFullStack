// produtoService.js
const CACHE_KEY = 'produtos_cache';
const CACHE_DURATION = 60 * 60 * 1000; // 1 hora

export const fetchProdutosComCache = async () => {
    const cache = localStorage.getItem(CACHE_KEY);
    const timestamp = localStorage.getItem(`${CACHE_KEY}_timestamp`);

    if (cache && timestamp && (Date.now() - timestamp) < CACHE_DURATION) {
        return JSON.parse(cache);
    }

    try {
        const response = await fetch('http://localhost:8080/api/produtos');
        if (!response.ok) {
            throw new Error(`Erro HTTP! status: ${response.status}`);
        }

        const produtos = await response.json();
        localStorage.setItem(CACHE_KEY, JSON.stringify(produtos));
        localStorage.setItem(`${CACHE_KEY}_timestamp`, Date.now());

        return produtos;
    } catch (error) {
        console.error('Erro ao buscar produtos:', error);
        throw error;
    }
};
