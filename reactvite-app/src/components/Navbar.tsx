import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { NavbarWrapper, ListNavBar, NavItem } from '../styles/navbar.styled.components';  // Importação correta


function Navbar({ width, height }) {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    // const [showModal, setShowModal] = useState(false);
    const [user, setUsername] = useState<string | null>();

    const navigate = useNavigate();

    useEffect(() => {
        // Verifica se existe um token de autenticação no localStorage
        const token = localStorage.getItem('token');
        const role = localStorage.getItem('role');
        const user = localStorage.getItem('user');
        setUsername(user);
        setIsAuthenticated(!!token);
    }, []);

    const logoutUser = () => {
        // Remove o token de autenticação do localStorage
        localStorage.removeItem('token');
        localStorage.removeItem('role');

        setIsAuthenticated(false);
        // setShowModal(true);
        navigate('/login', { state: { message: 'logout realizado com sucesso!' } })
    };


    return (
        <>
            <NavbarWrapper width={width} height={height} >

                <ListNavBar id='NavBar' className='navbar-menu-principal'>
                    <NavItem className='main-button' id='itenNavBar'>
                        <Link to="/">Home</Link>
                    </NavItem>
                    <NavItem className='main-button' id='itenNavBar'>
                        <Link to="/produtos"><p>Produtos</p></Link>
                    </NavItem>
                    <NavItem className='main-button' id='itenNavBar'>
                        <Link to="/carrinho">Carrinho</Link>
                    </NavItem>
                    {!isAuthenticated ? (
                        <NavItem className='main-button' id='itenNavBar'>
                            <Link to="/login">Login</Link>
                        </NavItem>
                    ) : (
                        <>
                            <p>Bem-vindo! Você já está logado {user}.</p>
                            <button onClick={logoutUser} >Sair</button>
                        </>
                    )}
                    {/* {showModal && (
                        <Modal
                            message="Você foi desconectado"
                            onClose={() => setShowModal(false)}
                        />
                        )} */}
                </ListNavBar>
            </NavbarWrapper>
        </>

    );
}

export default Navbar;
