import React from 'react';
import '../CSS/NavBarAdminCSS.css'
import { Link } from 'react-router-dom';

const NavBarAdmin = ({ setSessaoSelecionada }) => {
    return (
        <div className='NavBarAdmin'>
            <ul>
                <p>Welcome to the admin page!</p>
                <button onClick={() => setSessaoSelecionada('listarProduto')}><li id='AdminList'>Listar Produtos</li></button>
                <button onClick={() => setSessaoSelecionada('adicionarProduto')}><li id='AdminList'>Adicionar Produtos</li></button>
                <button onClick={() => setSessaoSelecionada('listarSacola')}><li id='AdminList'>Listar Sacolas</li></button>
            </ul>
        </div>
    );
}

export default NavBarAdmin;
