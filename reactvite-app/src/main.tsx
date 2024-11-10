import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.tsx'
import App from './App.tsx'
import { GlobalStyle } from './index.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <GlobalStyle />
    <App />
  </StrictMode>,
)
