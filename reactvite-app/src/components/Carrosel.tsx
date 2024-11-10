import React, { useEffect, useState } from 'react';
import { Swiper, SwiperSlide, useSwiper } from 'swiper/react';
import 'swiper/css';
import 'swiper/less';
import '../styles/carouselhomecss.css';

const SlideNextButton = () => {
    const swiper = useSwiper();

    return (
        <button onClick={() => swiper.slideNext()} className="swiper-button-next">
            <>1'</>
        </button>
    );
};

const SlidePrevButton = () => {
    const swiper = useSwiper();

    return (
        <button onClick={() => swiper.slidePrev()} className="swiper-button-prev">
            <>1'</>
        </button>
    );
};

const CarrouselHome = () => {
    const [images, setImages] = useState([]);


    useEffect(() => {
        const fetchImages = async () => {
            try {
                const response = await fetch('https://placehold.co/600x400'); // Exemplo de URL de imagens
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                // Usando imagens geradas aleatoriamente como exemplo
                const data = Array(5).fill('https://placehold.co/600x400'); // Exemplo de múltiplas imagens
                setImages(data);
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
                    prevEl: '.swiper-button-prev',
                    nextEl: '.swiper-button-next',
                }}
                autoplay={{ delay: 5000, disableOnInteraction: false }}
                loop
                className="mySwiper"
            >
                {images.map((src, index) => (
                    <SwiperSlide key={index} className="swiper-slide">
                        <img src={src} alt={`Imagem ${index + 1}`} className="carousel-image" />
                    </SwiperSlide>
                ))}

                <SlideNextButton />
                <SlidePrevButton />
            </Swiper>
        </div>
    );
};

export default CarrouselHome;
