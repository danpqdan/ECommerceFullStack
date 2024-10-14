import React, { useState, useEffect } from 'react';
import CarrouselHome from '../components/CarrouselHome';
import PromocoesIcons from '../components/PromocoesIcons'
import PromocoesItens from '../components/PromocoesItens';

const Home = () => {

    return (

        <>
            < CarrouselHome />
            <PromocoesIcons />
            <PromocoesItens />
            
        </>
    )

}



export default Home;
