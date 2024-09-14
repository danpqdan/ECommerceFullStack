import React, { useState } from 'react';

const ProductForm = () => {
  const [product, setProduct] = useState({
    nomeDoProduto: '',
    descricao: '',
    preco: ''
  });
  const [image, setImage] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct({
      ...product,
      [name]: value
    });
  };

  const handleImageChange = (e) => {
    setImage(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    const token = localStorage.getItem('token'); // Obtenha o token JWT

    e.preventDefault();

    const formData = new FormData();
    formData.append('produto', JSON.stringify(product));
    formData.append('imagem', image);

    try {
      const response = await fetch('http://localhost:8080/api/produtos', {
        method: 'POST',
        body: formData,
        headers: {
          'Authorization': `Bearer ${token}`
        },
      });

      if (response.ok) {
        console.log('Produto adicionado com sucesso');
      } else {
        console.error('Erro ao adicionar produto');
      }
    } catch (error) {
      console.error('Erro ao enviar a solicitação:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Nome:</label>
        <input
          type="text"
          name="nomeDoProduto"
          value={product.nomeDoProduto}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <label>Descrição:</label>
        <input
          type="text"
          name="descricao"
          value={product.descricao}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <label>Preço:</label>
        <input
          type="number"
          name="preco"
          value={product.preco}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <label>Imagem:</label>
        <input
          type="file"
          name="imagem"
          onChange={handleImageChange}
          required
        />
      </div>
      <button type="submit">Enviar</button>
    </form>
  );
};

export default ProductForm;
