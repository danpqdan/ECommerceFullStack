import { ResponsiveBox } from "..";
import { Login } from "../components/Login"
import { useWindowSize } from "../hooks/useWindowsSize";

const LoginForm = () => {
    const { width = 0, height = 0 } = useWindowSize();

    return (
        <ResponsiveBox width={width} height={height}>
            <Login />

        </ResponsiveBox>
    );
}

export default LoginForm;
