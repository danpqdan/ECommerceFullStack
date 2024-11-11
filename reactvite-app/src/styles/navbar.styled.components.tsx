import styled from 'styled-components';
import { colors } from '../index';

interface ResponsiveBoxProps {
  width: number;
  height: number;
}

export const NavbarWrapper = styled.nav<ResponsiveBoxProps>`
  width: ${(props) => (props.width > 768 ? '90vw' : '96vw')};
  min-height: 50px;
  display: flex;
  justify-content: center;
  margin: 0 auto;
  border-radius: 5px;
  height: 100%;
  background-color: ${colors.blue};
  border-bottom: 1px solid ${colors.darkGreen};
  border-radius: 12px;
`;

// Lista de itens do menu
export const ListNavBar = styled.ul`
    display: flex;
    flex-direction: row;
    gap: 15px;
    align-items: center;
    z-index: 2;
`;

// Item de navegação
export const NavItem = styled.button`
  margin-right: 10px;
  border: 1px solid ${colors.darkGreen};

  // Estilo do Link dentro do item de navegação
  a {
    text-decoration: none;
  }
`;
