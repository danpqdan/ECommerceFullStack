import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../logo.svg';


import "./CSS/NavBarCss.css"

function Navbar() {
    return (
        <>
            <nav>
                <img src={logo} className="App-logo" alt="logo" />

                <ul>
                    <li>
                        <Link to="/">Home</Link>
                    </li>
                    <li>
                        <Link to="/produtos">Produtos</Link>
                    </li>
                    <li>
                        <Link to="/carrinho">Carrinho</Link>
                    </li>
                    <li>
                        <Link to="/login">Login</Link>
                    </li>
                </ul>
            </nav>
        </>

    );
}

export default Navbar;
