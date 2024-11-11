import { useEffect, useState } from 'react';
import { Swiper, SwiperSlide, useSwiper } from 'swiper/react';
import '../styles/carouselhomecss.css'
import 'swiper/swiper-bundle.css';

const SlideNextButton = () => {
    const swiper = useSwiper();

    return (
        <button onClick={() => swiper.slideNext()} className='button-next'>

        </button>
    );
};

const SlidePrevButton = () => {
    const swiper = useSwiper();

    return (
        <button onClick={() => swiper.slidePrev()} className="button-prev">

        </button>
    );
};

const CarrouselHome = () => {
    const [images, setImages] = useState<string[]>([]);


    useEffect(() => {
        const fetchImages = async () => {
            try {
                const response = await fetch('https://placehold.co/600x400');
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                // Usando imagens geradas aleatoriamente como exemplo
                const data = Array(5).fill('https://placehold.co/600x400');
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
