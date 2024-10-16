import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';

const ProdutoCard = () => {
    const location = useLocation();
    const product = location.state?.product;
    const [imagemPrincipal, setMainImage] = useState(product.imagem.urlPrincipal); // Imagem principal exibida

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
            </div>
        </div>
    );

}

export default ProdutoCard;
