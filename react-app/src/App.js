import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import './App.css';
import Navbar from "./components/NavBar";
import Carrinho from './pages/Carrinho';
import Home from './pages/Home';
import LoginForm from './pages/Login';
import Produtos from './pages/Produtos';
import PainelAdmin from './pages/PainelAdmin';

function App() {

  return (
    <Router>
      <div className="App">
        <header className="App-header">
          <div>
            {/* Navbar sempre visível */}
            <Navbar />

          </div>

        </header>
        {/* Definindo as rotas */}
        <Routes>

          <Route path="/" element={<Home />} />
          <Route path="/produtos" element={<Produtos />} />
          <Route path="/carrinho" element={<Carrinho />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path='/paineladmin' element={<PainelAdmin />} />

        </Routes>

      </div >
    </Router >
  );
}

export default App;
