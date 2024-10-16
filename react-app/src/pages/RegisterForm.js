import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
const RegisterForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();



    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');



        const userData = {
            username,
            password
        };

        try {
            const response = await fetch('http://localhost:8080/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            });

            if (!response.ok) {
                throw new Error('Erro ao registrar usuário');
            }

            const data = response.json();
            console.log('Usuário registrado com sucesso:', data);
            navigate('/login');
        } catch (error) {
            console.error(error);
            setError(error.message || 'Erro ao registrar. Tente novamente.');

        } finally {
            setLoading(false);

        }
    };

    const resetForm = () => {
        setUsername('');
        setPassword('');
    };

    return (
        <div id='bodyDiv'>

            <div className="modal-overlay" id='loginForm'>
                <div className="modal-content">

                    <form onSubmit={handleSubmit}>
                        <div className='loginContent'>
                            <h2>Registrar</h2>

                            <div >
                                <label htmlFor="username" className='inputLabel'>username</label>
                                <input
                                    className='inputForm'
                                    type="username"
                                    id="username"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                />
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
                            </div>


                            <div className='contentBtn'>
                                <button id='btnForm' type='submit' disabled={loading} s>
                                    {loading ? 'Registrando...' : 'Registrar'}

                                </button>

                            </div>
                            {error && <p style={{ color: 'red' }}>{error}</p>}
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default RegisterForm;
