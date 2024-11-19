import styled from 'styled-components';
//import { colors } from '../index';


interface ResponsiveBoxProps {
    width: number;
    height: number;
}


export const LoginDivWapper = styled.nav<ResponsiveBoxProps>`
    width: ${(props) => (props.width > 768 ? '90vw' : '96vw')};
    height: ${(props) => (props.height > 768 ? '100vh' : 'calc(100vh - 50px)')};
    display: flex;
    align-items: center;
    align-content: center;
    justify-content: center;
    margin: 0 auto;
    flex-direction: column;
    width: 50%;
    //height: calc(100vh - 50px);
`

export const LoginContainer = styled.nav`
    display: flex;
    align-items: center;
    align-content: center;
    justify-content: center;
    margin: 0 auto;
    flex-direction: column;
    height: 100%;
    width: 50%;
`
export const LoginInput = styled.input`
    border: 1px solid black;
    border-radius: 12px;
    display: flex;
    padding: 12px;
    margin: 8px ;
`

export const LoginButton = styled.button`
    display: flex;
    flex-direction: row;
    `
export const LoginDivButton = styled.button`
    display: flex;
    width: 100%;
    gap: 6px;
    border-radius: 6px;
    flex-direction: row;
    align-items: center;
    justify-content: center;
`
