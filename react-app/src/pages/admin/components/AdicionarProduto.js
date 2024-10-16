import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "../CSS/FormProdutoCss.css";
import TesteCardProduto from './TesteCardProduto';

const AdicionarProduto = () => {
    const [showPreview, setShowPreview] = useState(false);
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

            const responseText = await response.text(); // Ler a resposta como texto
            console.log('Resposta do servidor:', responseText); // Log a resposta


            if (!response.ok) {
                throw new Error('Erro ao criar o produto');
            }

            const data = await response.json();
            console.log('Produto criado com sucesso:', data);
            return data
        } catch (error) {
            console.error('Erro:', error);
            throw error
        }
    };
    const handlePreview = () => {
        setShowPreview(true);
    };


    const handleSubmit = async (e) => {
        e.preventDefault();

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
        try {
            const response = await createProduct(produto);

            if (response && response.success) {
                alert('Produto criado com sucesso!');
                resetForm(); // Limpa o formulário após sucesso
            } else {
                alert('Erro ao criar o produto: ' + (response ? response.message : 'Resposta inválida do servidor'));
                // Não resete o formulário se houver erro
            }
        } catch (error) {
            alert('Erro na comunicação com o servidor: ' + error.message);
            // Não resete o formulário se houver erro
        }
    }


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
        setShowPreview(false); // Ocultar a pré-visualização quando o formulário for resetado

    };




    return (
        <>
            <div id='contentBody'>
                <form onSubmit={handleSubmit} id='produtoForm'>
                    <div className='separatorContent'>

                        <div className='contentProduto'>

                            <h2>Criar Produto</h2>

                            <label className='inputLabel'>
                                Nome do Produto:
                                <input
                                    className='inputForm'
                                    type="text"
                                    value={nomeDoProduto}
                                    onChange={(e) => setNomeDoProduto(e.target.value)}
                                    required
                                />
                            </label>

                            <label className='inputLabel'>
                                Descrição:
                                <textarea
                                    className='inputForm'
                                    value={descricao}
                                    onChange={(e) => setDescricao(e.target.value)}
                                    required
                                />
                            </label>

                            <label className='inputLabel'>
                                Preço:
                                <input
                                    className='inputForm'
                                    type="number"
                                    value={preco}
                                    onChange={(e) => setPreco(e.target.value)}
                                    required
                                />
                            </label>

                            <label className='inputLabel'>
                                Quantidade em Estoque:
                                <input
                                    className='inputForm'
                                    type="number"
                                    value={quantidadeEmEstoque}
                                    onChange={(e) => setQuantidadeEmEstoque(e.target.value)}
                                    required
                                />
                            </label>
                        </div>
                        <div className='contentProduto'>

                            <h2>Imagem</h2>
                            <label className='inputLabel'>
                                Nome:
                                <input
                                    className='inputForm'
                                    type="text"
                                    value={imagemDTO.nome}
                                    onChange={(e) => setImagemDTO({ ...imagemDTO, nome: e.target.value })}
                                    required
                                />
                            </label>

                            <label className='inputLabel'>
                                Descrição:
                                <input
                                    className='inputForm'
                                    type="text"
                                    value={imagemDTO.descricao}
                                    onChange={(e) => setImagemDTO({ ...imagemDTO, descricao: e.target.value })}
                                    required
                                />
                            </label>

                            <label className='inputLabel'>
                                URL Principal:
                                <input
                                    className='inputForm'
                                    type="url"
                                    value={imagemDTO.urlPrincipal}
                                    onChange={(e) => setImagemDTO({ ...imagemDTO, urlPrincipal: e.target.value })}
                                    required
                                />
                            </label>

                            <label className='inputLabel'>
                                URL Miniatura:
                                <input
                                    className='inputForm'
                                    type="url"
                                    value={imagemDTO.urlMiniatura}
                                    onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura: e.target.value })}
                                />
                            </label>

                            <label className='inputLabel'>
                                URL Miniatura 2:
                                <input
                                    className='inputForm'
                                    type="url"
                                    value={imagemDTO.urlMiniatura2}
                                    onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura2: e.target.value })}
                                />
                            </label>

                            <label className='inputLabel'>
                                URL Miniatura 3:
                                <input
                                    className='inputForm'
                                    type="url"
                                    value={imagemDTO.urlMiniatura3}
                                    onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura3: e.target.value })}
                                />
                            </label>

                            <label className='inputLabel'>
                                URL Miniatura 4:
                                <input
                                    className='inputForm'
                                    type="url"
                                    value={imagemDTO.urlMiniatura4}
                                    onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura4: e.target.value })}
                                />
                            </label>

                            <label className='inputLabel'>
                                Categoria:
                                <input
                                    className='inputForm'
                                    type="text"
                                    value={categoria}
                                    onChange={(e) => setCategoria(e.target.value)}
                                    required
                                />
                            </label>
                        </div>
                    </div>

                    <div className='contentButton'>

                        <button type="submit" id='btnForm'>Criar Produto</button>
                        <button onClick={() => resetForm()} id='btnForm'>Limpar Dados</button>
                        <button type="button" onClick={handlePreview} id='btnForm'>Visualizar produto</button>
                    </div>
                </form>
                {showPreview && (
                    <div>
                        <h2>Pré-visualização do Produto</h2>
                        <TesteCardProduto
                            nomeDoProduto={nomeDoProduto}
                            descricao={descricao}
                            preco={preco}
                            quantidadeEmEstoque={quantidadeEmEstoque}
                            imagem={imagemDTO}
                            categoria={categoria}
                        />
                    </div>
                )}


            </div >
        </>
    );
}

export default AdicionarProduto;
