import React, { useState } from 'react';

const AdicionarProduto = () => {
    const [nomeDoProduto, setNomeDoProduto] = useState('');
    const [descricao, setDescricao] = useState('');
    const [preco, setPreco] = useState('');
    const [quantidadeEmEstoque, setQuantidadeEmEstoque] = useState('');
    const [imagemDTO, setImagemDTO] = useState({
        nome: '',
        descricao: '',
        urlPrincipal: '',
        urlMiniatura: '',
        urlMiniatura2: '',
        urlMiniatura3: '',
        urlMiniatura4: ''
    });
    const [categoria, setCategoria] = useState('');


    const createProduct = async (produto) => {
        try {
            const response = await fetch('http://localhost:8080/api/produtoeimagem', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}` 
                },
                body: JSON.stringify(produto)

            });

            if (!response.ok) {
                throw new Error('Erro ao criar o produto');
            }

            const data = await response.json();
            console.log('Produto criado com sucesso:', data);
        } catch (error) {
            console.error('Erro:', error);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault(); // Evitar o comportamento padrão do formulário

        const produto = {
            nomeDoProduto,
            descricao,
            preco: parseFloat(preco), // Converter para número
            quantidadeEmEstoque: parseInt(quantidadeEmEstoque, 10), // Converter para inteiro
            imagemDTO,
            categoria: {
                categoria
            }
        };

        createProduct(produto);
    };


    const resetForm = () => {
        setNomeDoProduto('');
        setDescricao('');
        setPreco('');
        setQuantidadeEmEstoque('');
        setImagemDTO({
            nome: '',
            descricao: '',
            urlPrincipal: '',
            urlMiniatura: '',
            urlMiniatura2: '',
            urlMiniatura3: '',
            urlMiniatura4: ''
        });
        setCategoria('');
    };



    return (
        <>
            <div>
                <h1>Adicionar Produto</h1>
                <form onSubmit={handleSubmit}>
                    <h2>Criar Produto</h2>

                    <label>
                        Nome do Produto:
                        <input
                            type="text"
                            value={nomeDoProduto}
                            onChange={(e) => setNomeDoProduto(e.target.value)}
                            required
                        />
                    </label>

                    <label>
                        Descrição:
                        <textarea
                            value={descricao}
                            onChange={(e) => setDescricao(e.target.value)}
                            required
                        />
                    </label>

                    <label>
                        Preço:
                        <input
                            type="number"
                            value={preco}
                            onChange={(e) => setPreco(e.target.value)}
                            required
                        />
                    </label>

                    <label>
                        Quantidade em Estoque:
                        <input
                            type="number"
                            value={quantidadeEmEstoque}
                            onChange={(e) => setQuantidadeEmEstoque(e.target.value)}
                            required
                        />
                    </label>

                    <h3>Imagem</h3>
                    <label>
                        Nome:
                        <input
                            type="text"
                            value={imagemDTO.nome}
                            onChange={(e) => setImagemDTO({ ...imagemDTO, nome: e.target.value })}
                            required
                        />
                    </label>

                    <label>
                        Descrição:
                        <input
                            type="text"
                            value={imagemDTO.descricao}
                            onChange={(e) => setImagemDTO({ ...imagemDTO, descricao: e.target.value })}
                            required
                        />
                    </label>

                    <label>
                        URL Principal:
                        <input
                            type="url"
                            value={imagemDTO.urlPrincipal}
                            onChange={(e) => setImagemDTO({ ...imagemDTO, urlPrincipal: e.target.value })}
                            required
                        />
                    </label>

                    <label>
                        URL Miniatura:
                        <input
                            type="url"
                            value={imagemDTO.urlMiniatura}
                            onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura: e.target.value })}
                        />
                    </label>

                    <label>
                        URL Miniatura 2:
                        <input
                            type="url"
                            value={imagemDTO.urlMiniatura2}
                            onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura2: e.target.value })}
                        />
                    </label>

                    <label>
                        URL Miniatura 3:
                        <input
                            type="url"
                            value={imagemDTO.urlMiniatura3}
                            onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura3: e.target.value })}
                        />
                    </label>

                    <label>
                        URL Miniatura 4:
                        <input
                            type="url"
                            value={imagemDTO.urlMiniatura4}
                            onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura4: e.target.value })}
                        />
                    </label>

                    <label>
                        Categoria:
                        <input
                            type="text"
                            value={categoria}
                            onChange={(e) => setCategoria(e.target.value)}
                            required
                        />
                    </label>

                    <button type="submit">Criar Produto</button>
                </form>

            </div>
        </>
    );
}

export default AdicionarProduto;
