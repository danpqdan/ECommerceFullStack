import { useEffect, useState } from 'react';

export interface Categoria {
    categoria: string;
}

const UseCategorias = () => {
    const [categorias, setCategorias] = useState<Categoria[]>([]);  // Tipando como Categoria[]

    useEffect(() => {
        const fetchCategorias = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/categoria', { method: 'GET' });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                const data = await response.json();
                if (Array.isArray(data)) {
                    setCategorias(data);  // Set categorias as array of Categoria
                } else {
                    console.error('Dados de categorias inválidos');
                }
            } catch (error) {
                console.error('Erro ao buscar categorias', error);
            }
        };

        fetchCategorias();
    }, []);

    return categorias;  // Retorna o array de categorias
}

export default UseCategorias;
