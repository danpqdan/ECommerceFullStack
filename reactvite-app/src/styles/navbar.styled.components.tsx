import styled from 'styled-components';
import { colors } from '../index';

interface ResponsiveBoxProps {
  width: number;
  height: number;
}

export interface VisibilityProps {
  isVisible: boolean;
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
  border-bottom: 1px solid ${colors.darkText};
  border-radius: 12px;
  @media (max-width: 768px) {
    background-color: transparent;
    border: none;
    position: absolute;
    top: 0;
    left: 0;
    width: 100vw;
    height: 50px;
  }
`;

export const ToggleIcon = styled.div<{ isVisible: boolean }>`
  position: fixed;
  font-size: 18px;
  cursor: pointer;
  z-index: 100;
  right: 10px;
  padding: 14px;
  border-radius: 200%;
  color: ${colors.darkText};
  /* border: 1px solid ${colors.paleBlue}; */
  background-color: ${colors.paleBlue};
  transition: opacity 1s ease, visibility 1s ease, transform 1s ease;
  opacity: ${({ isVisible }) => (isVisible ? 1 : 0)};
  visibility: ${({ isVisible }) => (isVisible ? 'visible' : 'hidden')};
  transform: ${({ isVisible }) => (isVisible ? 'translateY(0)' : 'translateY(-100%)')};
  box-shadow: 0px 4px 16px rgba(0, 0, 0, 0.8), 0px 8px 8px rgba(255, 255, 255, 0.4);

`;

export const Overlay = styled.div<VisibilityProps>`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5); 
  backdrop-filter: blur(5px); 
  transition: opacity 0.5s ease;
  opacity: ${({ isVisible }) => (isVisible ? '1' : '0')};
  pointer-events: ${({ isVisible }) => (isVisible ? 'auto' : 'none')}; 
  z-index: 5; 
`;


export const ListNavBarToggle = styled.ul<VisibilityProps>`
display: flex;
z-index: 5;
height: 90vh;
justify-content: center;
align-items: center;
margin-top: 2vh;
  &.navbar-menu-toggle {
    display: flex;
    flex-direction: column;
    position: fixed;
    background-color: ${colors.paleBlue};
    width: 80vw;
    top: 0px;
    left: 50px;
    border-radius: 12px;
    border: none;
    transition: transform 0.5s ease, opacity 0.5s ease;
    opacity: ${({ isVisible }) => (isVisible ? '1' : '0')};
    transform: ${({ isVisible }) => (isVisible ? 'translateY(0)' : 'translateY(-20px)')};
    box-shadow: 0px 4px 16px rgba(0, 0, 0, 1), 0px 8px 8px rgba(255, 255, 255, 1);
    border: none;
    z-index: 10; // Z-index para estar acima do overlay
  }

`;

export const ListNavBar = styled.ul`
  display: flex;
  flex-direction: row;
  gap: 15px;
  align-items: center;
  z-index: 2;
`;

// Item de navegação
export const NavItem = styled.button`
  padding: 8px;
  margin-right: 10px;
  border: 1px solid ${colors.darkBlue};
  border-radius: 8px;
  box-shadow: 0px 4px 16px rgba(0, 0, 0, 0.4), 0px 8px 8px rgba(255, 255, 255, 0.4);
  margin: 4px;
  a {
    text-decoration: none;
    width: 100%;
  }

  @media (max-width: 768px) {
    width: 90%;
    border: none;
    align-items: center;
    a {
    text-decoration: none;
  }
  }
`;
