import React from 'react';
import { useLocation } from 'react-router-dom';

const ProdutoCard = () => {

    const location = useLocation();
    const product = location.state?.product;

    if (!product) {
        return <p>Produto não encontrado.</p>;
    }

    return (
        <div>
            <h1>{product.nome}</h1>
            <img src={product.imagem.urlPrincipal} alt={product.nome} width="300" />
            <p>{product.descricao}</p>
            <p>Preço: ${product.preco.toFixed(2)}</p>
            <p>Categoria: {product.categoria.categoria}</p>
        </div>
    );

}

export default ProdutoCard;
