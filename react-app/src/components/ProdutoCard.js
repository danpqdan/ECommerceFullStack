import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import Modal from '../components/AlertOverlayer/ModalTest'
const ProdutoCard = () => {
    const location = useLocation();
    const product = location.state?.product;
    const [imagemPrincipal, setMainImage] = useState(product.imagem.urlPrincipal); // Imagem principal exibida
    const [quantidadeTotal, setQuantidadeTotal] = useState(1);
    const [mostrarOverlay, setMostrarOverlay] = useState(false);


    const imageArray = [
        { url: product.imagem.urlPrincipal, alt: 'Principal' },
        { url: product.imagem.urlMiniatura, alt: 'Miniatura 1' },
        { url: product.imagem.urlMiniatura2, alt: 'Miniatura 2' },
        { url: product.imagem.urlMiniatura3, alt: 'Miniatura 3' },
        { url: product.imagem.urlMiniatura4, alt: 'Miniatura 4' },
    ];

    if (!product) {
        return <p>Produto não encontrado.</p>;
    }

    const handleImageClick = (url) => {
        setMainImage(url);
    };

    const colocandoEmCarrinho = async () => {
        try {
            const storedToken = localStorage.getItem('token');
            const storedUsername = localStorage.getItem('user');
            console.log("location.state:", location.state);


            if (!storedToken || !storedUsername) {
                console.error("Token ou Username não encontrados no localStorage.");
                return;
            }


            const response = await fetch('http://localhost:8080/cliente/sacola', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${storedToken}`,
                },
                credentials: 'include',
                body: JSON.stringify({
                    login: {
                        token: storedToken,
                        username: storedUsername,
                    },
                    clienteProdutoDTO: [{
                        id: product.id,
                        quantidadeProduto: quantidadeTotal,
                    }]
                }),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log('Produto adicionado ao carrinho:', data);
            setMostrarOverlay(true)
        } catch (error) {
            console.error('Erro ao adicionar o produto ao carrinho:', error);
        }
    };

    const aumentarQuantidade = () => {
        setQuantidadeTotal(quantidadeTotal + 1);
    };

    // Função para diminuir a quantidade, evitando valores negativos
    const diminuirQuantidade = () => {
        if (quantidadeTotal > 1) {  // Define limite mínimo como 1
            setQuantidadeTotal(quantidadeTotal - 1);
        }
    };


    return (
        <div id="divProduto">
            <div style={{
                display: 'flex'
            }} id='miniatura'>
                {imageArray
                    .filter(image => image.url && image.url.trim() !== "")
                    .map((image, index) => (
                        <img
                            key={index} // Usar `index` como chave, pois a ordem é garantida
                            src={image.url}
                            id='imgMiniatura'
                            alt={image.alt}
                            width="100"
                            height="100"
                            onClick={() => handleImageClick(image.url)}
                            style={{
                                cursor: 'pointer',
                                border: imagemPrincipal === image.url ? '3px solid blue' : 'none'
                            }}
                        />

                    ))}
            </div>
            <div id='imgPrincipal'>
                <img src={imagemPrincipal} alt={product.nome} width="500" height="300" id='imgPrincipal' />
            </div>


            <div id='infoProduto'>

                <h1>{product.nome}</h1>
                <p>{product.descricao}</p>
                <p>Preço: ${product.preco.toFixed(2)}</p>
                <p>Categoria: {product.categoria.categoria}</p>
                <button onClick={() => aumentarQuantidade()}>+</button>
                {quantidadeTotal}
                <button onClick={() => diminuirQuantidade()}>-</button>
                <button onClick={() => colocandoEmCarrinho()}>Comprar</button>
                {mostrarOverlay && (
                    <Modal message={"Produto adicionado ao carrinho!"} onClose={() => setMostrarOverlay(false)} />
                )}

            </div>

        </div>
    );

}

export default ProdutoCard;
