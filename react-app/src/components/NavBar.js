import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../logo.svg';


import "./CSS/NavBarCss.css"

function Navbar() {
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
                    <button id='itenNavBar'>
                        <Link to="/login">Login</Link>
                    </button>
                </ul>
            </nav>
        </>

    );
}

export default Navbar;
