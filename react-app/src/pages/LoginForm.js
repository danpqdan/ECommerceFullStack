import React, { useState } from 'react';
import useFetchData from '../hook/useFetchData';
import { Navigate, useNavigate } from 'react-router-dom';

import "../components/CSS/logincss.css"
import RegisterForm from './RegisterForm';
import Modal from '../components/AlertOverlayer/ModalTest';


const LoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState({});
    const [loading, setLoading] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [modalMessage, setModalMessage] = useState('');
    const [isAuthenticated, setIsAuthenticated] = useState(false);


    const navigate = useNavigate();




    // Função de validação
    const validate = () => {
        const errors = {};
        if (!username) {
            errors.username = 'Username is required';
        }
        if (!password) {
            errors.password = 'Password is required';
        } else if (password.length < 4) {
            errors.password = 'Password must be at least 6 characters';
        }
        return errors;
    };

    // Função para lidar com o envio do formulário
    const handleSubmit = async (e) => {
        e.preventDefault();

        const validationErrors = validate();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }


        setLoading(true);
        try {
            const response = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (!response.ok) {
                throw new Error('Login failed');
            }

            const data = await response.json();
            localStorage.setItem('token', data.token);
            localStorage.setItem('role', data.role)
            localStorage.setItem('user', username)

            if (data.role === 'role_admin') {
                setIsAuthenticated(true);
                setShowModal(true);
                navigate('/paineladmin')
                setModalMessage("Login realizado com sucesso")
                window.location.reload();

            } else {
                setIsAuthenticated(true);
                setShowModal(true);
                navigate('/carrinho')
                setModalMessage("Login realizado com sucesso")
                window.location.reload();
            }

        } catch (error) {
            console.error('Error:', error);
            setErrors({ ...errors, form: 'Login failed. Please try again.' });
        } finally {
            setLoading(false);
        }
    };


    return (
        <div id='bodyDiv'>

            <form onSubmit={handleSubmit} id='loginForm'>
                <div className='loginContent'>

                    <div >
                        <label htmlFor="username" className='inputLabel'>username</label>
                        <input
                            className='inputForm'
                            type="username"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                        {errors.username && <p className="error">{errors.username}</p>}
                    </div>

                    <div>
                        <label htmlFor="password" className='inputLabel'>Password</label>
                        <input
                            className='inputForm'
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        {errors.password && <p className="error">{errors.password}</p>}
                    </div>

                    {errors.form && <p className="error">{errors.form}</p>}

                    <div className='contentBtn'>

                        <button type="submit" disabled={loading} id='btnForm'>
                            {loading ? 'Logging in...' : 'Login'}
                        </button>
                        <button id='btnForm' onClick={() => navigate('/register')}>
                            Registre-se
                        </button>


                    </div>
                </div>
            </form>

            {showModal && (
                <Modal
                    message={isAuthenticated ? modalMessage : "Você foi desconectado"}
                    onClose={() => setShowModal(false)}
                />
            )}


        </div>
    );
};

export default LoginForm;
