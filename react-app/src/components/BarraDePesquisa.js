import React, { useState } from 'react';
import { IoSearchOutline } from "react-icons/io5";
import "./CSS/BarraDePesquisaCSS.css";

const BarraDePesquisa = () => {
    const [isVisible, setIsVisible] = useState(false);
    const [selectedCategories, setSelectedCategories] = useState([]);
    const [isOpen, setIsOpen] = useState(false); // Estado para controlar a visibilidade
    const toggleCategories = () => {
        setIsOpen(!isOpen);
    };

    const toggleOverlay = () => {
        setIsVisible(!isVisible); // Alterna a visibilidade da sobreposição
    };

    const handleCheckboxChange = (event) => {
        const { value, checked } = event.target;

        if (checked) {
            setSelectedCategories([...selectedCategories, value]);
        } else {
            setSelectedCategories(selectedCategories.filter((category) => category !== value));
        }
    };


    // if (loading) return <p>Carregando...</p>;
    // if (error) return <p>Erro: {error}</p>;


    function searchItensForName() {

    }
    return (
        <nav id='contentNav'>
            <form action="" method="get" id='inputForm'>
                <IoSearchOutline id='searchIcon' className='rotateIcon' />
                <input type="text" className='inputForm' placeholder='Produto' />
            </form>
            <button type='submit' id='btnSearch' onClick={() => searchItensForName()}>Buscar</button>
            <div>
                <label htmlFor="sort">Ordenar por: </label>
                <select id="sort">
                    <option value="">Selecione</option>
                    <option value="price">Preço</option>
                    <option value="sold">Mais Vendidos</option>
                    <option value="newest">Novos</option>
                </select>
            </div>
            <div id='overlay'>
                <button id="btnSearch" className="btnToggle"onClick={toggleOverlay}>
                    {isVisible ? 'Categorias' : 'Categorias'}
                </button>

                {isVisible && (
                    <div className="overlay">
                        <div className="overlay-content">
                            <h3>Selecione Categorias</h3>
                            <label>
                                <input
                                    type="checkbox"
                                    value="eletronicos"
                                    onChange={handleCheckboxChange}
                                />
                                Eletrônicos
                            </label>
                            <label>
                                <input
                                    type="checkbox"
                                    value="livros"
                                    onChange={handleCheckboxChange}
                                />
                                Livros
                            </label>
                            <label>
                                <input
                                    type="checkbox"
                                    value="roupas"
                                    onChange={handleCheckboxChange}
                                />
                                Roupas
                            </label>
                            <button onClick={toggleOverlay}>Fechar</button>
                        </div>
                    </div>
                )}

            </div>
        </nav >
    );
}

export default BarraDePesquisa;
