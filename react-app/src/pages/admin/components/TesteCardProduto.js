import React, { useState } from 'react';


const TesteCardProduto = ({ nomeDoProduto, descricao, preco, quantidadeEmEstoque, imagem, categoria }) => {

    const [imagemPrincipal, setMainImage] = useState(imagem.urlPrincipal);
    const precoFormatado = parseFloat(preco) ? parseFloat(preco).toFixed(2) : '0.00';


    const imageArray = [
        { url: imagem.urlPrincipal, alt: 'Principal' },
        { url: imagem.urlMiniatura, alt: 'Miniatura 1' },
        { url: imagem.urlMiniatura2, alt: 'Miniatura 2' },
        { url: imagem.urlMiniatura3, alt: 'Miniatura 3' },
        { url: imagem.urlMiniatura4, alt: 'Miniatura 4' },
    ];



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
                <img src={imagemPrincipal} alt={imagem.nome} width="500" height="300" id='imgPrincipal' />
            </div>


            <div id='infoProduto'>

                <h1>{nomeDoProduto}</h1>
                <p>{descricao}</p>
                <p>Preço: ${precoFormatado}</p>
                <p>Categoria: {categoria.categoria}</p>
            </div>
        </div>
    );
}

export default TesteCardProduto;
