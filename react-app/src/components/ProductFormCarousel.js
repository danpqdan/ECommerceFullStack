import React, { useState } from 'react';

const ImagemUploadForm = () => {
    const [descricao, setDescricao] = useState('');
    const [imagem, setImagem] = useState(null);

    const handleDescricaoChange = (e) => {
        setDescricao(e.target.value);
    };

    const handleImagemChange = (e) => {
        setImagem(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        const token = localStorage.getItem('token'); // Obtenha o token JWT

        e.preventDefault();

        const formData = new FormData();
        formData.append('descricao', descricao);
        formData.append('arquivo', imagem);

        try {
            const response = await fetch('http://localhost:8080/api/imagens/upload', {
                method: 'POST',
                body: formData,
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            });

            if (response.ok) {
                console.log('Imagem enviada com sucesso');
            } else {
                console.error('Erro ao enviar a imagem');
            }
        } catch (error) {
            console.error('Erro ao enviar a solicitação:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Descrição:</label>
                <input
                    type="text"
                    value={descricao}
                    onChange={handleDescricaoChange}
                    required
                />
            </div>
            <div>
                <label>Imagem:</label>
                <input
                    type="file"
                    onChange={handleImagemChange}
                    required
                />
            </div>
            <button type="submit">Enviar</button>
        </form>
    );
};

export default ImagemUploadForm;
