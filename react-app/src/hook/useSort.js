import { useState, useCallback } from 'react';

/**
 * Hook para ordenação de uma lista de dados.
 * @param {Array} initialData - Dados iniciais a serem ordenados.
 * @param {Function} getSortValue - Função para extrair o valor de ordenação dos itens.
 * @returns {Array} - Array contendo os dados ordenados e a função para atualizar a ordem de ordenação.
 */
function useSort(initialData, getSortValue) {
  const [data, setData] = useState(initialData);
  const [sortOrder, setSortOrder] = useState('asc'); // 'asc' para ascendente e 'desc' para descendente

  // Função para ordenar os dados
  const sortData = useCallback(() => {
    const sortedData = [...data].sort((a, b) => {
      const valueA = getSortValue(a);
      const valueB = getSortValue(b);

      if (valueA < valueB) return sortOrder === 'asc' ? -1 : 1;
      if (valueA > valueB) return sortOrder === 'asc' ? 1 : -1;
      return 0;
    });

    setData(sortedData);
  }, [data, sortOrder, getSortValue]);

  // Função para alternar a ordem de ordenação
  const toggleSortOrder = () => {
    setSortOrder(prevOrder => {
      const newOrder = prevOrder === 'asc' ? 'desc' : 'asc';
      sortData();
      return newOrder;
    });
  };

  return [data, sortOrder, toggleSortOrder];
}

export default useSort;
