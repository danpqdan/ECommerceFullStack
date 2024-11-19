import React from 'react';
import { useIsAdmin } from '../hooks/useLogin';
import { PainelAdmin } from './PainelAdmin';
const Painel = () => {
    const isAdmin = useIsAdmin(); return (
        <div>
            {isAdmin ? (
                <>
                    <PainelAdmin />
                </>
            ) : (
                <h1>Você não tem acesso ao painel de administração.</h1>
            )}

        </div>
    );
}

export default Painel;
