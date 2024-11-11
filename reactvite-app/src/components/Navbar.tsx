import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useWindowSize, WindowSize } from '../hooks/useWindowsSize';
import { ListNavBar, ListNavBarToggle, NavbarWrapper, NavItem, Overlay, ToggleIcon } from '../styles/navbar.styled.components'; // Importação correta
import { useScrollDirection } from '../hooks/useScrollDirection';

const Navbar: React.FC<WindowSize> = () => {
    const { width = 0, height = 0 } = useWindowSize();

    const [isAuthenticated, setIsAuthenticated] = useState(false);
    // const [showModal, setShowModal] = useState(false);
    const [user, setUsername] = useState<string | null>();
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const isToggleIconVisible = useScrollDirection(); // Controle de visibilidade do ícone


    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        // const role = localStorage.getItem('role');
        const user = localStorage.getItem('user');
        setUsername(user);
        setIsAuthenticated(!!token);
    }, []);

    const logoutUser = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');

        setIsAuthenticated(false);
        // setShowModal(true);
        navigate('/login', { state: { message: 'logout realizado com sucesso!' } })
    };

    const isMobile = width < 768;
    const toggleMenu = () => setIsMenuOpen(!isMenuOpen);
    const toggleItem = () => setIsMenuOpen(false);



    return (
        <>
            <NavbarWrapper width={width} height={height}>
                {isMobile ? (
                    <>
                        {isToggleIconVisible && (
                            <ToggleIcon isVisible={isToggleIconVisible} onClick={toggleMenu}>
                                ☰
                            </ToggleIcon>
                        )}
                        <Overlay isVisible={isMenuOpen} onClick={toggleMenu} />
                        {isMenuOpen && (
                            <ListNavBarToggle className='navbar-menu-toggle' isVisible={isMenuOpen}>
                                <NavItem className='main-button' onClick={() => toggleItem()}>
                                    <Link to="/" style={{ display: 'block', width: '100%', height: '100%', textDecoration: 'none' }}>Home</Link>
                                </NavItem>
                                <NavItem className='main-button' onClick={() => toggleItem()}>
                                    <Link to="/produtos" style={{ display: 'block', width: '100%', height: '100%', textDecoration: 'none' }}>Produtos</Link>
                                </NavItem>
                                <NavItem className='main-button' onClick={() => toggleItem()}>
                                    <Link to="/carrinho" style={{ display: 'block', width: '100%', height: '100%', textDecoration: 'none' }}>Carrinho</Link>
                                </NavItem>
                                {!isAuthenticated ? (
                                    <NavItem className='main-button'>
                                        <Link to="/login" style={{ display: 'block', width: '100%', height: '100%', textDecoration: 'none' }}>Login</Link>
                                    </NavItem>
                                ) : (
                                    <>
                                        <p>Bem-vindo! Você já está logado {user}.</p>
                                        <button onClick={logoutUser}>Sair</button>
                                    </>
                                )}
                            </ListNavBarToggle>
                        )}
                    </>
                ) : (
                    <ListNavBar>
                        <NavItem className='main-button'>
                            <Link to="/">Home</Link>
                        </NavItem>
                        <NavItem className='main-button'>
                            <Link to="/produtos">Produtos</Link>
                        </NavItem>
                        <NavItem className='main-button'>
                            <Link to="/carrinho">Carrinho</Link>
                        </NavItem>
                        {!isAuthenticated ? (
                            <NavItem className='main-button'>
                                <Link to="/login">Login</Link>
                            </NavItem>
                        ) : (
                            <>
                                <p>Bem-vindo! Você já está logado {user}.</p>
                                <button onClick={logoutUser}>Sair</button>
                            </>
                        )}
                    </ListNavBar>
                )}
            </NavbarWrapper >
        </>

    );
}

export default Navbar;
