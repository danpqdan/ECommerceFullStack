import { ProductList } from '../components/ProductList';
import { useWindowSize } from '../hooks/useWindowsSize';
import { ResponsiveBox } from '../index';

const Produtos = () => {
    const { width = 0, height = 0 } = useWindowSize();

    return (
        <ResponsiveBox width={width} height={height}>
            <ProductList />
        </ResponsiveBox>
    );
}

export default Produtos;
