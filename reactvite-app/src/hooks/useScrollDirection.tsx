import { useEffect, useState } from 'react';

export function useScrollDirection() {
    const [isVisible, setIsVisible] = useState(true);
    const [lastScrollY, setLastScrollY] = useState(0);

    useEffect(() => {
        const handleScroll = () => {
            const currentScrollY = window.scrollY;

            if (currentScrollY > lastScrollY + 10) {
                // Desaparece ao rolar para baixo com uma margem de tolerância de 10px
                setIsVisible(false);
            } else if (currentScrollY < lastScrollY - 10 || currentScrollY === 0) {
                // Aparece ao rolar para cima ou quando o scroll é zero (topo da página)
                setIsVisible(true);
            }

            setLastScrollY(currentScrollY);
        };

        window.addEventListener('scroll', handleScroll);

        return () => window.removeEventListener('scroll', handleScroll);
    }, [lastScrollY]);

    return isVisible;
}
