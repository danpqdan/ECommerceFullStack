import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';

import './App.css';
import Navbar from "./components/NavBar";
import ProdutoCard from './components/ProdutoCard';
import PainelAdmin from './pages/admin/PainelAdmin';
import Carrinho from './pages/Carrinho';
import Home from './pages/Home';
import LoginForm from './pages/LoginForm';
import Produtos from './pages/Produtos';
import RegisterForm from './pages/RegisterForm';  
import { useEffect, useState } from 'react';

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
          <Route path="/register" element={<RegisterForm />} />
          <Route path='/paineladmin' element={<PainelAdmin />} />
          <Route path="/produto/:id" element={<ProdutoCard />} />
          <Route path="/produto-preview" element={<ProdutoCard />} />

        </Routes>

      </div >
    </Router >
  );
}

export default App;
