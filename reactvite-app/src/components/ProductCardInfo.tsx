import { useNavigate } from "react-router-dom";
import { ProdutoCardInfoProps } from "../hooks/useProdutosComCache";

export const ProdutoCardInfo: React.FC<ProdutoCardInfoProps> = ({ id, nome, imagem, preco }) => {
    const navigate = useNavigate();
  
    return (
      <button id="card-produto" onClick={() => navigate(`/produto/${id}`, { state: { id, nome, imagem } })}>
        <img src={imagem.urlPrincipal} alt={nome} id="imgMiniatura" />
        <h2>{nome}</h2>
        <p>Price: ${typeof preco === 'number' ? preco.toFixed(2) : 'Indisponível'}</p>
      </button>
    );
  };
  