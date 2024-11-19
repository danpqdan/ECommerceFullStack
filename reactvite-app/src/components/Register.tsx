import { useState } from 'react';
import { LoginContainer, LoginInput } from '../styles/login';
import { useRegister } from '../hooks/useRegister';

export const Register = () => {
    const { register, loading, error } = useRegister();
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault(); // Prevent form from reloading the page
        const success = await register(username, password); // Use the `register` function
        if (success) {
            // Do something on success, e.g., redirect
            console.log('Registration successful');
        } else {
            // Handle failure, e.g., show error message
            console.log('Registration failed');
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <LoginContainer>
                    <LoginInput
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <LoginInput
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <button type="submit" disabled={loading}>
                        {loading ? 'Submitting...' : 'Submit'}
                    </button>
                </LoginContainer>
                {error && <p style={{ color: 'red' }}>{error}</p>}
            </form>
        </div>
    );
};
