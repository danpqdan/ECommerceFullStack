import React from 'react';
import './css/Modal.css'; // Estilos para o modal

function Modal({ message, onClose }) {
    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <p>{message}</p>
                <button onClick={onClose}>Fechar</button>
            </div>
        </div>
    );
}

export default Modal;
