import { useState } from 'react';
import { ResponsiveBox } from "..";
import { useWindowSize } from '../hooks/useWindowsSize';
import ListarProdutoMin from '../layouts/ListarProdutoMin';
import { LoginContainer } from '../styles/login';
import { ListNavBar } from '../styles/navbar.styled.components';
import { AdicionarProduto } from '../layouts/AdicionarProduto';

export const PainelAdmin = () => {
    const { width = 0, height = 0 } = useWindowSize();

    // Estado para alternar o conteúdo
    const [currentView, setCurrentView] = useState("default");

    // Função para alterar a visualização
    const switchElement = (view: "default" | "addProduct" | "listProducts" | "completedPurchases") => {
        setCurrentView(view);
    };

    return (
        <ResponsiveBox width={width} height={height}>
            {/* Barra de Navegação */}
            <ListNavBar>
                <LoginContainer style={{ display: "flex", flexDirection: "row" }}>
                    <li>
                        <button
                            onClick={() => switchElement("addProduct")}
                            className='main-button'
                        >
                            Adicionar produto
                        </button>
                    </li>
                    <li>
                        <button
                            onClick={() => switchElement("listProducts")}
                            className='main-button'
                        >
                            Listar produtos
                        </button>
                    </li>
                    <li>
                        <button
                            onClick={() => switchElement("completedPurchases")}
                            className='main-button'
                        >
                            Compras finalizadas
                        </button>
                    </li>
                </LoginContainer>
            </ListNavBar>

            {/* Conteúdo Renderizado Condicionalmente */}
            <div>
                {currentView === "addProduct" && (
                    <AdicionarProduto />


                )}
                {currentView === "listProducts" && (
                    <ListarProdutoMin />
                )}
                {currentView === "completedPurchases" && (
                    <div>
                        <h2>Compras Finalizadas</h2>
                        <p>Lista de compras finalizadas será exibida aqui.</p>
                    </div>
                )}
                {currentView === "default" && (
                    <AdicionarProduto />
                )}
            </div>
        </ResponsiveBox>
    );
};
