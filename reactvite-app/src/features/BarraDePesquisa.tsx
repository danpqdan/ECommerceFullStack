import React, { useState } from 'react';
import UseCategorias, { Categoria } from '../hooks/useCategorias';

export interface BarraDePesquisaProps {
    onFiltrar: (categoriasSelecionadas: string[]) => void;
    onOrdenar: (criterio: string) => void;
    onBuscar: (nome: string) => void;
}

const BarraDePesquisa: React.FC<BarraDePesquisaProps> = ({ onFiltrar, onOrdenar, onBuscar }) => {
    const categorias: Categoria[] = UseCategorias();  
    
    const [categoriasSelecionadas, setCategoriasSelecionadas] = useState<string[]>([]);
    const [ordenacaoSelecionada, setOrdenacaoSelecionada] = useState<string>("");
    const [termoBusca, setTermoBusca] = useState<string>("");
    const [isVisible, setIsVisible] = useState<boolean>(false);

    const toggleOverlay = () => {
        setIsVisible(!isVisible); 
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setTermoBusca(event.target.value);
    };

    const handleBuscarPorNome = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        onBuscar(termoBusca);
        setTermoBusca(""); 
    };

    const handleOrdenarChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const valorOrdenacao = event.target.value;
        setOrdenacaoSelecionada(valorOrdenacao);
        onOrdenar(valorOrdenacao);
    };

    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { value, checked } = event.target;
        setCategoriasSelecionadas(prevState =>
            checked
                ? [...prevState, value]
                : prevState.filter(categoria => categoria !== value)
        );
    };

    const handleBuscar = () => {
        onFiltrar(categoriasSelecionadas);
        setCategoriasSelecionadas([]); 
        toggleOverlay(); 
    };

    return (
        <nav id='contentNav'>
            <form onSubmit={handleBuscarPorNome} id='inputForm'>
                <input
                    type="text"
                    className='inputForm'
                    placeholder='Produto'
                    value={termoBusca}
                    onChange={handleInputChange}
                />
                <button type="submit" id='btnSearch'>Buscar</button>
            </form>

            <div>
                <label htmlFor="sort">Ordenar por: </label>
                <select id="sort" onChange={handleOrdenarChange} value={ordenacaoSelecionada}>
                    <option value="">Selecione</option>
                    <option value="priceAsc">Preço mais baixo</option>
                    <option value="priceDesc">Preço mais alto</option>
                    <option value="sold">Mais Vendidos</option>
                    <option value="newest">Novos</option>
                </select>
            </div>

            <div id='overlay'>
                <button id="btnSearch" className="btnToggle" onClick={toggleOverlay}>
                    {isVisible ? 'Fechar Categorias' : 'Categorias'}
                </button>

                {isVisible && (
                    <div className="overlay">
                        <div className="overlay-content">
                            <h3>Selecione Categorias</h3>
                            {categorias.map((categoria, index) => (
                                <label key={index}>
                                    <input
                                        type="checkbox"
                                        value={categoria.categoria}
                                        onChange={handleCheckboxChange}
                                    />
                                    {categoria.categoria}
                                </label>
                            ))}
                            <button onClick={handleBuscar}>Buscar</button>
                        </div>
                    </div>
                )}
            </div>
        </nav>
    );
};

export default BarraDePesquisa;
