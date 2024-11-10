import React from 'react';
import { ProductList } from '../components/ProductList';
import { ResponsiveBox } from '../index'
import { useWindowSize } from '../hooks/useWindowsSize';

const Produtos = () => {
    const { width = 0, height = 0 } = useWindowSize();

    return (
        <ResponsiveBox width={width} height={height}>
            <ProductList />
        </ResponsiveBox>
    );
}

export default Produtos;
