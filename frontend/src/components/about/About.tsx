import ProductCard from "../shared/ProductCard";

const products = [
    {
        publicId: "1",
        image: "https://embarkx.com/sample/placeholder.png",
        name: "iPhone 13 Pro Max",
        description:
            "The iPhone 13 Pro Max offers exceptional performance with its A15 Bionic chip, stunning Super Retina XDR display, and advanced camera features for breathtaking photos.",
        quantity: 2,
        discount: 2,
        specialPrice: 720,
        price: 780,
        about: true,
    },
    {
        publicId: "2",
        image: "https://embarkx.com/sample/placeholder.png",
        name: "Samsung Galaxy S21",
        description:
            "Experience the brilliance of the Samsung Galaxy S21 with its vibrant AMOLED display, powerful camera, and sleek design that fits perfectly in your hand.",
        quantity: 5,
        discount: 3,
        specialPrice: 699,
        price: 799,
        about: true,
    },
    {
        publicId: "3",
        image: "https://embarkx.com/sample/placeholder.png",
        name: "Google Pixel 6",
        description:
            "The Google Pixel 6 boasts cutting-edge AI features, exceptional photo quality, and a stunning display, making it a perfect choice for Android enthusiasts.",
        quantity: 10,
        discount: 1,
        price: 599,
        specialPrice: 400,
        about: true,
    }
];

const About = () => {
    return (
        <div className="max-w-7xl mx-auto px-4 py-8">
            <h1 className="text-slate-800 text-4xl font-bold text-center mb-12">
                About Us
            </h1>
            <div className="flex flex-col lg:flex-row justify-between items-center mb-12">
                <div className="w-full md:w-1/2 text-center md:text-left">
                    <p className="text-lg mb-4">
                        Welcome to Zel's Shop Ecomm, we got the lowest prices and the highest quality in the market. So come check it out.
                    </p>
                </div>
                <div className="w-full md:w-1/2 mb-6 md:mb-0">
                    <img src="https://embarkx.com/sample/placeholder.png" alt="About us"
                        className="w-full h-auto rounded-lg shadow-lg transform transition-transform duration-300 hover:scale-105">
                    </img>
                </div>
            </div>

            <div className="py-7 space-y-8">
                <h1 className="text-slate-800 text-4xl font-bold text-center">
                    Our Products
                </h1>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {
                        products.map((p, idx) => (
                            <ProductCard key={idx} {...p} />
                        ))
                    }
                </div>
            </div>
        </div>
    )
};

export default About;
