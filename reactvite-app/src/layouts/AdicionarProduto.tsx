import React, { useState } from 'react';

interface Produto {
    nomeDoProduto: string;
    descricao: string;
    preco: number;
    quantidadeEmEstoque: number;
    imagemDTO: {
        nome: string;
        descricao: string;
        urlPrincipal: string;
        urlMiniatura: string;
        urlMiniatura2: string;
        urlMiniatura3: string;
        urlMiniatura4: string;
    };
    categoria: {
        categoria: string;
    };
}


export const AdicionarProduto = () => {
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

    const createProduct = async (produto: Produto) => {
        try {
            const response = await fetch('http://localhost:8080/api/produtos/adicionar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify(produto)
            });

            const responseText = await response.text(); // Ler a resposta como texto
            console.log('Resposta do servidor:', responseText);

            if (!response.ok) {
                throw new Error('Erro ao criar o produto');
            }

            const data = await response.json();
            console.log('Produto criado com sucesso:', data);
            return data;
        } catch (error) {
            console.error('Erro:', error);
            throw error;
        }
    };


    const handlePreview = () => {
        setShowPreview(true);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        // Verifique se todos os campos estão preenchidos
        if (!nomeDoProduto || !descricao || !preco || !quantidadeEmEstoque || !categoria) {
            alert('Por favor, preencha todos os campos obrigatórios.');
            return;
        }

        // Converte valores numéricos
        const produto = {
            nomeDoProduto,
            descricao,
            preco: parseFloat(preco), // Converter para número
            quantidadeEmEstoque: parseInt(quantidadeEmEstoque, 10), // Converter para inteiro
            imagemDTO,
            categoria: { categoria }
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
        } catch (error: unknown) {
            if (error instanceof Error) {
                console.error('Erro:', error.message); // Agora você pode acessar a propriedade message
            } else {
                console.error('Erro desconhecido:', error);
            }
            throw error;
        }
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
        setShowPreview(false);
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Nome do Produto:</label>
                <input
                    type="text"
                    value={nomeDoProduto}
                    onChange={(e) => setNomeDoProduto(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Descrição:</label>
                <input
                    type="text"
                    value={descricao}
                    onChange={(e) => setDescricao(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Preço:</label>
                <input
                    type="number"
                    value={preco}
                    onChange={(e) => setPreco(e.target.value)}
                    required
                    min="0"
                />
            </div>
            <div>
                <label>Quantidade em Estoque:</label>
                <input
                    type="number"
                    value={quantidadeEmEstoque}
                    onChange={(e) => setQuantidadeEmEstoque(e.target.value)}
                    required
                    min="0"
                />
            </div>
            <div>
                <label>Categoria:</label>
                <input
                    type="text"
                    value={categoria}
                    onChange={(e) => setCategoria(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Imagem Principal:</label>
                <input
                    type="text"
                    value={imagemDTO.urlPrincipal}
                    onChange={(e) => setImagemDTO({ ...imagemDTO, urlPrincipal: e.target.value })}
                />
            </div>
            <div>
                <label>Imagem Miniatura:</label>
                <input
                    type="text"
                    value={imagemDTO.urlMiniatura}
                    onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura: e.target.value })}
                />
            </div>
            <div>
                <label>Imagem Miniatura2:</label>
                <input
                    type="text"
                    value={imagemDTO.urlMiniatura2}
                    onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura2: e.target.value })}
                />
            </div>
            <div>
                <label>Imagem Miniatura3:</label>
                <input
                    type="text"
                    value={imagemDTO.urlMiniatura3}
                    onChange={(e) => setImagemDTO({ ...imagemDTO, urlMiniatura3: e.target.value })}
                />
            </div>
            <button type="button" onClick={handlePreview}>
                Visualizar
            </button>
            <button type="submit">Criar Produto</button>

            {showPreview && (
                <div>
                    <h3>Pré-visualização</h3>
                    <p><strong>Nome:</strong> {nomeDoProduto}</p>
                    <p><strong>Descrição:</strong> {descricao}</p>
                    <p><strong>Preço:</strong> {preco}</p>
                    <p><strong>Quantidade em Estoque:</strong> {quantidadeEmEstoque}</p>
                    <p><strong>Categoria:</strong> {categoria}</p>
                    <img src={imagemDTO.urlPrincipal} alt="Produto" />
                    <img src={imagemDTO.urlMiniatura} alt="Produto" />
                    <img src={imagemDTO.urlMiniatura2} alt="Produto" />
                    <img src={imagemDTO.urlMiniatura3} alt="Produto" />
                </div>
            )}
        </form>
    );
};
