
import { Children } from 'react';
import styled, { createGlobalStyle, css } from 'styled-components';

export const colors = {
  blue: '#CEF2F2',
  paleBlue: '#0ed8d8',
  darkBlue: '#2a4f86',
  darkText: '#000000',
  lightText: '#cae2e2'
};


export const fontSizes = {
  small: '12px',
  regular: '16px',
  large: '20px',
};

export const height = {
  small: '340px',
  regural: '481px',
  large: '1025px'
};

export const widht = {
  small: '480px',
  regular: '768px',
  large: '1280px'
}

export const flexCenter = css`
  display: flex;
  align-items: center;
  justify-content: center;
`;

interface ResponsiveBoxProps {
  width: number;
  height: number;
}

export const ResponsiveBox = styled.div<ResponsiveBoxProps>`
  width: ${(props) => (props.width > 768 ? '90vw' : '100%')};
  height: ${(props) => `calc(${props.height}px + ${Children}px)`}; 
  background-color: ${(props) => (props.width > 800 ? colors.darkBlue : colors.darkBlue)};
  transition: all 0.3s ease;
  margin: 0 auto;
  box-shadow: 0px 4px 16px rgba(0, 0, 0, 1), 0px 8px 8px rgba(255, 255, 255, 1);
  border-radius: 6px;

`;


export const GlobalStyle = createGlobalStyle`
  *,
  *::before,
  *::after {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    text-decoration: none;
    list-style: none;
  }

  body {
    font-family: "Space Grotesk", sans-serif;
    font-weight: 100;
    background-color: ${colors.paleBlue};
    color: ${colors.darkText};
    line-height: 1.5;
    overflow-x: hidden;
  }

  a, Link ,p{
    color: inherit;
    text-decoration: none;
    z-index: 5;
  }


  input,
  textarea,
  select,
  button {
    font-family: inherit;
    font-size: inherit;
    border: none;
    outline: none;
    background: none;
    
  }


  img {
    max-width: 100%;
    height: auto;
  }


  h1, h2, h3, h4, h5, h6 {
    z-index: 2;
    font-weight: 500;
  }


  body {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }


  ol, ul {
    list-style-position: inside;
  }


  button {
    background-color: transparent;
    cursor: pointer;
  }


  h1 {
    color: ${colors.darkText};
    font-size: ${fontSizes.large};
  }
  .main-button {
    z-index: 1;
  position: relative;
  padding: 6px 8px;
  border-radius: 4px;
  border: 0.05px solid ${colors.lightText};
  overflow: hidden; 
}

.main-button::after {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 0;
  height: 100%;
  background-color: ${colors.lightText};
  transition: width 1s ease-in-out;
  z-index: -1;
}

.main-button:hover::after {
  width: 100%;
}

.main-button:hover {
  color: ${colors.darkText}; 
}


  .centered-container {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100vh;
    background-color: ${colors.lightText};
  }
`;

