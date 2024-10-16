import React from 'react';
import '../CSS/NavBarAdminCSS.css'
import { Link } from 'react-router-dom';

const NavBarAdmin = ({ setSessaoSelecionada }) => {
    return (
        <div className='NavBarAdmin'>
            <ul>
                <p>Welcome to the admin page!</p>
                <button onClick={() => setSessaoSelecionada('listarProduto')} id='btnForm'><li>Listar Produtos</li></button>
                <button onClick={() => setSessaoSelecionada('adicionarProduto')} id='btnForm'><li>Adicionar Produtos</li></button>
                <button onClick={() => setSessaoSelecionada('listarSacola')} id='btnForm'><li>Listar Sacolas</li></button>
            </ul>
        </div>
    );
}

export default NavBarAdmin;
