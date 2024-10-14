import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import AdicionarProduto from '../admin/components/AdicionarProduto'
import ListarProduto from '../admin/components/ListarProduto'
import ListarSacola from '../admin/components/Sacola'
import NavBarAdmin from './components/NavBarAdmin';
import Sacola from '../admin/components/Sacola';

const PainelAdmin = () => {
    const [status, setStatus] = useState('loading'); // 'loading', 'authorized', 'unauthorized', 'error'
    const [sessaoSeleciona, setSessaoSelecionada] = useState(); // Componente inicial

    useEffect(() => {
        const fetchData = async () => {
            const token = localStorage.getItem('token'); // Obtenha o token JWT

            try {
                const response = await fetch('http://localhost:8080/api/admin', {
                    method: 'GET',
                    headers: {
                        'Authorization': `${token}`,
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include'
                });

                if (response.ok) {
                    setStatus('authorized');
                } else if (response.status === 403) {
                    setStatus('unauthorized');
                } else {
                    setStatus('error');
                }
            } catch (error) {
                setStatus('error');
            }
        };

        fetchData();
    }, []);

    if (status === 'loading') {
        return <p>Loading...</p>;
    }

    if (status === 'authorized') {

        const renderComponent = () => {
            switch (sessaoSeleciona) {
                case 'listarProduto':
                    return <ListarProduto />;
                case 'adicionarProduto':
                    return <AdicionarProduto />;
                case 'listarSacola':
                    return <ListarSacola />;
                default:
                    return "";
            }
        };


        return (
            <>
                <div>
                    <NavBarAdmin setSessaoSelecionada={setSessaoSelecionada} />
                    <div className="component-container">
                        {renderComponent()}
                    </div>

                </div>


            </>
        );
    }

    if (status === 'unauthorized') {
        return <p>You are not authorized to view this page.</p>;
    }

    if (status === 'error') {
        return <p>There was an error fetching the data.</p>;
    }

    return null;
};

export default PainelAdmin;
