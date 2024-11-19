import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useLogin } from '../hooks/useLogin.tsx';
import { useWindowSize } from '../hooks/useWindowsSize.tsx';
import { LoginButton, LoginContainer, LoginDivButton, LoginDivWapper, LoginInput } from '../styles/login.tsx';

export const Login = () => {
    const { width = 0, height = 0 } = useWindowSize();
    const { login, status, userRole, error, loading, logado } = useLogin();
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const isLoggedIn = await login(username, password);
        if (isLoggedIn) {
            console.log('Login realizado com sucesso');
        } else {
            console.log('Erro no login:', error);
        }

    };



    return (
        <LoginDivWapper height={height} width={width} className='LoginDivWapper' >
            <form onSubmit={handleSubmit}>
                <LoginContainer>
                    <LoginInput type="text" placeholder='username' value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <LoginInput type="password"
                        placeholder='senha' value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </LoginContainer>
                {error && <p>{error}</p>}
                <LoginDivButton>
                    <LoginButton className='main-button' type="submit">Login</LoginButton>
                    <LoginButton className='main-button'><Link to="/register">Registre-se</Link></LoginButton>
                </LoginDivButton>

            </form>

        </LoginDivWapper>
    );
}


