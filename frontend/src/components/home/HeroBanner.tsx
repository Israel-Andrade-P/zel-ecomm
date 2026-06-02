import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';

const HeroBanner = () => {
    return (
        <div className='py-2 rounded-md'>
            <Swiper grabCursor={true} autoplay={{ delay: 4000, disableOnInteraction: false }} navigation modules={[]} pagination={{ clickable: true }} scrollbar={{ draggable: true }} slidesPerView={1}>

            </Swiper>
        </div>
    );
}
