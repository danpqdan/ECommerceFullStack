import { useState, useEffect } from 'react';

// Tipos para o estado do tamanho da janela
export interface WindowSize {
    width: number | undefined;
    height: number | undefined;
}

export const useWindowSize = (): WindowSize => {
    // Estado para armazenar o tamanho da janela
    const [windowSize, setWindowSize] = useState<WindowSize>({
        width: undefined,
        height: undefined,
    });

    useEffect(() => {
        // Função para atualizar o estado com o tamanho atual da janela
        const handleResize = () => {
            setWindowSize({
                width: window.innerWidth,
                height: window.innerHeight,
            });
        };

        // Chama a função uma vez ao montar o componente
        handleResize();

        // Adiciona um listener para o evento de redimensionamento da janela
        window.addEventListener('resize', handleResize);

        // Remove o listener quando o componente é desmontado
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    return windowSize;
};