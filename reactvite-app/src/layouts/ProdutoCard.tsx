import { useLocation } from "react-router-dom";


export const ProdutoCard = () => {
    const { state } = useLocation();
    const { id, nome, imagem } = state || {};

    return (
        <div>
            <h1>{nome}</h1>
            <img src={imagem?.urlPrincipal} alt={nome} />
            <p>ID: {id}</p>
        </div>
    );
};
