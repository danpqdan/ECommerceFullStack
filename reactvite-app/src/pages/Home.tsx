import { ResponsiveBox } from "..";
import CarrouselHome from "../components/Carrosel";
import Navbar from "../components/Navbar";
import { useWindowSize } from "../hooks/useWindowsSize";

const Home = () => {
    const { width = 0, height = 0 } = useWindowSize();

    return (

        <>
            <ResponsiveBox width={width} height={height}>
                <CarrouselHome />
                <CarrouselHome />
                <CarrouselHome />
                <CarrouselHome />
            </ResponsiveBox>
            <div>t</div>
            <ResponsiveBox width={width} height={height}>
                <Navbar width={width} height={height} />
            </ResponsiveBox>
        </>
    )

}



export default Home;
