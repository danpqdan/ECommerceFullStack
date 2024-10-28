import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import logo from '../logo.svg';


import "./CSS/NavBarCss.css";
import Modal from './AlertOverlayer/ModalTest';

function Navbar() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [user, setUsername] = useState();

    const navigate = useNavigate();
    
    useEffect(() => {
        // Verifica se existe um token de autenticação no localStorage
        const token = localStorage.getItem('token');
        const role = localStorage.getItem('role');
        setUsername(localStorage.getItem('user'));
        setIsAuthenticated(!!token);
    }, []);

    const logoutUser = () => {
        // Remove o token de autenticação do localStorage
        localStorage.removeItem('token');
        localStorage.removeItem('role');

        setIsAuthenticated(false);
        setShowModal(true);
        navigate('/login', { state: { message: 'logout realizado com sucesso!' } })
    };


    return (
        <>
            <nav id='NavBar'>
                <img src={logo} className="App-logo" alt="logo" />

                <ul id='listNavBar'>
                    <button id='itenNavBar'>
                        <Link to="/">Home</Link>
                    </button>
                    <button id='itenNavBar'>
                        <Link to="/produtos">Produtos</Link>
                    </button>
                    <button id='itenNavBar'>
                        <Link to="/carrinho">Carrinho</Link>
                    </button>
                    {!isAuthenticated ? (
                        <button id='itenNavBar'>
                            <Link to="/login">Login</Link>
                        </button>
                    ) : (
                        <>
                            <p>Bem-vindo! Você já está logado {user}.</p>
                            <button onClick={logoutUser} >Sair</button>
                        </>
                    )}
                    {showModal && (
                        <Modal
                            message="Você foi desconectado"
                            onClose={() => setShowModal(false)}
                        />
                    )}



                </ul>
            </nav>
        </>

    );
}

export default Navbar;
