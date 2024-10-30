import React, { useEffect, useState } from 'react';
import { ProdutoCardInfo } from '../components/ProdutoCard'
import { Navigate, useNavigate } from 'react-router-dom';

const Carrinho = () => {
    const [token, setToken] = useState();
    const [username, setUsername] = useState();
    const [dataFrame, setDataFrame] = useState([]);
    const navigate = useNavigate();


    const ProdutoCardInfo = (product) => {
        navigate(`/produto/${product.id}`, { state: { product } });
    };

    useEffect(() => {
        const fetchCliente = async () => {
            const storedToken = localStorage.getItem('token');
            const storedUsername = localStorage.getItem('user');

            if (!storedToken || !storedUsername) {
                console.error("Token ou Username não encontrados no localStorage.");
                return;
            }

            setToken(storedToken);
            setUsername(storedUsername);

            try {
                const response = await fetch('http://localhost:8080/cliente', {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${storedToken}`,
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        login: {
                            token: storedToken,
                            username: storedUsername,
                        }
                    }),
                    credentials: 'include',

                })

                const data = await response.json();
                console.log('Dados recebidos:', data);
                localStorage.setItem('sacola', JSON.stringify(data))
                setDataFrame(data)

            } catch (error) {
                console.error('Erro ao buscar cliente:', error);
            }
        };
        fetchCliente();
    }, []);



    return (
        <>
            <div>
                <h2>Carrinho de Compras - {dataFrame.estadoDaCompra}</h2>
                <ul>
                    {Array.isArray(dataFrame.clienteProduto) && dataFrame.clienteProduto.length > 0 ? (
                        dataFrame.clienteProduto.map(product => (
                            <li key={product.produto.id}>
                                <button id='card-produto' onClick={() => ProdutoCardInfo(product.produto)}>
                                    <img src={product.produto.imagem.urlPrincipal} id='imgMiniatura' />
                                    <h2>{product.produto.nome}</h2>
                                    <p>Quantidade: {product.quantidadeProduto}</p>
                                    <p>Price: ${typeof product.valorTotalDeProduto === 'number' ? product.valorTotalDeProduto.toFixed(2) : 'N/A'}</p>
                                </button>
                            </li>
                        ))
                    ) : (
                        <p>Nenhum produto encontrado.</p>
                    )}
                </ul>
                <p>Valor Total da Sacola: {dataFrame.valorFinalSacola}</p>
            </div >

        </>
    );
}

export default Carrinho;
