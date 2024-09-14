import React, { useEffect, useRef, useState } from 'react';
import 'swiper/css';
import 'swiper/less';
import { Swiper, SwiperSlide, useSwiper } from 'swiper/react';
import './CSS/carouselhomecss.css'; // Importar o CSS personalizado

const SlideNextButton = () => {
    const swiper = useSwiper();

    return (
        <button onClick={() => swiper.slideNext()} className="swiper-button-next">

        </button>
    );
};

// Componente para o botão de slide anterior
const SlidePrevButton = () => {
    const swiper = useSwiper();

    return (
        <button onClick={() => swiper.slidePrev()} className="swiper-button-prev">

        </button>
    );
};

const CarrouselHome = ({ itens }) => {
    const [images, setImages] = useState([]);
    const swiperRef = useRef(null);



    // Função para buscar as imagens do backend
    useEffect(() => {
        // Função para buscar imagens do backend
        const fetchImages = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/imagens/carouselhome',
                    {
                        method: "GET"
                    }
                );
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                if (Array.isArray(data)) {
                    // Extrair URLs de dados diretamente do JSON
                    const imageUrls = data.map(image => `data:image/jpeg;base64,${image.dados}`);
                    setImages(imageUrls);
                } else {
                    console.error('A resposta da API não é um array');
                }
            } catch (error) {
                console.error('Erro ao buscar imagens:', error);
            }
        };

        fetchImages();
    }, []);


    return (
        <div className="carousel-container">
            <Swiper
                spaceBetween={30}
                slidesPerView={1}
                pagination={{ clickable: true }}
                navigation={{
                    clickable: true,
                    prevEl: '.swiper-button-prev', // Configurar o botão anterior
                    nextEl: '.swiper-button-next'  // Configurar o botão próximo
                }} // Configurar botões de navegação
                autoplay={{ delay: 5000, disableOnInteraction: false }}
                loop
                className="mySwiper"
            >
                {images.map((src, index) => (
                    <SwiperSlide key={index} className="swiper-slide">
                        <img src={src} alt={`Imagem ${index + 1}`} className="carousel-image" />
                    </SwiperSlide>
                ))}
                {/* Navegação personalizada */}
                <SlideNextButton></SlideNextButton>
                <SlidePrevButton></SlidePrevButton>
            </Swiper >
        </div >
    )
}

export default CarrouselHome;
