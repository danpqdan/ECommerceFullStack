import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export const useLogin = () => {
    const [status, setStatus] = useState<string | null>(null);
    const [userRole, setUserRole] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [logado, setLogado] = useState<boolean>(false);
    const navigate = useNavigate();

    const login = async (username: string, password: string) => {
        setLoading(true);
        try {
            const response = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                const data = await response.json();
                setStatus('success');
                setUserRole(data.role);
                setLogado(true);
                setError("Logado")
                localStorage.setItem('role', data.role)

                if (data.token) {
                    localStorage.setItem('token', data.token);
                }



                navigate('/')
                window.location.reload();
                return true;
            } else {
                setStatus('error');
                setError('Erro no login');
                return false;
            }
        } catch (error) {
            setStatus('error');
            setError(error instanceof Error ? error.message : 'Erro de rede');
            return false;
        } finally {
            setLoading(false);
        }
    };

    return { login, logado, setLogado, status, userRole, error, loading };
};

export const useIsLogado = () => {
    const [isLogado, setIsLogado] = useState<boolean>(false);

    useEffect(() => {
        const token = localStorage.getItem('token');
        setIsLogado(!!token);
    }, []);
    return isLogado;
};

export const useIsAdmin = () => {
    const [isAdmin, setIsAdmin] = useState<boolean>(false);

    useEffect(() => {
        const role = localStorage.getItem('role');
        setIsAdmin(role === 'role_admin');
    }, []);

    return isAdmin;
};