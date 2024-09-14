import React, { useState } from 'react';

const LoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState({});
    const [loading, setLoading] = useState(false);


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

        } catch (error) {
            console.error('Error:', error);
            setErrors({ ...errors, form: 'Login failed. Please try again.' });
        } finally {
            setLoading(false);
        }
    };


    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label htmlFor="username">username:</label>
                <input
                    type="username"
                    id="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                {errors.username && <p className="error">{errors.username}</p>}
            </div>

            <div>
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                {errors.password && <p className="error">{errors.password}</p>}
            </div>

            {errors.form && <p className="error">{errors.form}</p>}

            <button type="submit" disabled={loading}>
                {loading ? 'Logging in...' : 'Login'}
            </button>
        </form>
    );
};

export default LoginForm;
