import React, { useEffect, useState } from 'react';
import { IoSearchOutline } from "react-icons/io5";
import "./CSS/BarraDePesquisaCSS.css";

const BarraDePesquisa = ({ onFiltrar, onOrdenar, onBuscar }) => {
    const [categoriasSelecionadas, setCategoriasSelecionadas] = useState([]);
    const [categorias, setCategorias] = useState([]);
    const [ordenacaoSelecionada, setOrdenacaoSelecionada] = useState("");
    const [termoBusca, setTermoBusca] = useState("");
    const [isVisible, setIsVisible] = useState(false);

    const toggleOverlay = () => {
        setIsVisible(!isVisible); // Alterna a visibilidade da sobreposição
    };

    const handleInputChange = (event) => {
        setTermoBusca(event.target.value);
    };

    const handleBuscarPorNome = (event) => {
        event.preventDefault();
        onBuscar(termoBusca);
        setTermoBusca("");
    };


    const handleOrdenarChange = (event) => {
        const valorOrdenacao = event.target.value;
        setOrdenacaoSelecionada(valorOrdenacao);
        onOrdenar(valorOrdenacao); // Chama a função de ordenação passada por props
    };


    const handleCheckboxChange = (event) => {
        const { value, checked } = event.target;
        if (checked) {
            setCategoriasSelecionadas([...categoriasSelecionadas, value]);
        } else {
            setCategoriasSelecionadas(
                categoriasSelecionadas.filter(categoria => categoria !== value)
            );
        }
    };


    useEffect(() => {
        const fetchCategorias = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/categoria', {
                    method: 'GET',
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setCategorias(data)
            } catch (error) {
            }
        };

        fetchCategorias();
    }, []);


    // if (loading) return <p>Carregando...</p>;
    // if (error) return <p>Erro: {error}</p>;

    const handleBuscar = () => {
        onFiltrar(categoriasSelecionadas);
        setCategoriasSelecionadas([]); // Limpa as categorias selecionadas
        toggleOverlay(); // Fecha o overlay
    };


    function searchItensForName() {

    }
    return (
        <nav id='contentNav'>

            <form onSubmit={handleBuscarPorNome} id='inputForm'>
                <IoSearchOutline id='searchIcon' className='rotateIcon' />
                <input type="text" className='inputForm' placeholder='Produto' value={termoBusca} onChange={handleInputChange} />
            </form>
            <button type='submit' id='btnSearch' onClick={() => searchItensForName()}>Buscar</button>
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
                    {isVisible ? 'Categorias' : 'Categorias'}
                </button>

                {isVisible && (
                    <div className="overlay">
                        <div className="overlay-content">
                            <h3>Selecione Categorias</h3>
                            {
                                categorias.map((categoria, index) => (
                                    <label key={index}>
                                        <input
                                            type="checkbox"
                                            value={categoria.categoria}
                                            onChange={handleCheckboxChange}
                                        />
                                        {categoria.categoria}
                                    </label>
                                ))

                            }
                            <button onClick={handleBuscar}>Buscar</button>
                            <button onClick={toggleOverlay}>Fechar</button>
                        </div>
                    </div>
                )}

            </div>
        </nav >
    );
}

export default BarraDePesquisa;
