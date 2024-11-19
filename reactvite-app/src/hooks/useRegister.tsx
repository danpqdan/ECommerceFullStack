import { useState } from 'react';

export const useRegister = () => {
    const [loading, setLoading] = useState(false); // Add loading state
    const [error, setError] = useState<string | null>(null); // Optional: Handle error state

    const register = async (username: string, password: string) => {
        setLoading(true);
        setError(null); // Reset error state before starting request
        try {
            const response = await fetch('http://localhost:8080/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                // Successful registration
                setLoading(false);
                return true;
            } else {
                // Handle unsuccessful registration (maybe show an error message)
                setLoading(false);
                setError('Registration failed. Please try again.');
                return false;
            }
        } catch (err) {
            // Handle network errors
            setLoading(false);
            setError(err instanceof Error ? err.message : 'Erro de rede');
            return false;
        }
    };

    return { register, loading, error };
};
