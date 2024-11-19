import { useNavigate } from "react-router-dom";
import { ProdutoCardInfoProps } from "../hooks/useProdutosComCache";

export const ProdutoCardInfo: React.FC<ProdutoCardInfoProps> = ({ id, nome, imagem, preco }) => {
  const navigate = useNavigate();

  return (
    <button
      id="card-produto"
      onClick={() => navigate(`/produto/${id}`, { state: { id, nome, imagem } })}
      style={{ border: 'none', background: 'none', cursor: 'pointer', textAlign: 'left' }}
    >
      <img
        src={imagem?.urlPrincipal || '/path/to/default-image.jpg'}
        alt={nome}
        id="imgMiniatura"
        style={{ width: '100px', height: '100px', objectFit: 'cover' }}
      />
      <h2>{nome}</h2>
      <p>Price: {typeof preco === 'number' ? preco.toLocaleString('en-US', { style: 'currency', currency: 'USD' }) : 'Indisponível'}</p>
    </button>
  );
};
