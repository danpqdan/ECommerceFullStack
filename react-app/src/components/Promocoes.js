import React from 'react';
import { PiHeartbeatBold } from "react-icons/pi";

import './CSS/promocoescss.css';
import ProdutosPromocoes from './ProdutosPromocoes';


const Promocoes = () => {
    return (
        <>
            <div className='list-btn'>
                <button className='btn-home'>
                    <PiHeartbeatBold id='icon' />
                    <h2>Saúde e Bem estar</h2>
                </button>

                <button className='btn-home'>
                    <PiHeartbeatBold id='icon' />
                    <h2>Moda</h2>
                </button>

                <button className='btn-home'>
                    <PiHeartbeatBold id='icon' />
                    <h2>Beauty</h2>
                </button>
            </div>
            <ProdutosPromocoes>


            </ProdutosPromocoes>
        </>
    );
}

export default Promocoes;
