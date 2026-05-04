type ProductCardProps = {
  productId: number;
  productName: string;
  image: string;
  description: string;
  quantity: number;
  price: number;
  discount: number;
  specialPrice: number;
};

const ProductCard = ({ productId, productName, image, description, quantity, price, discount, specialPrice, }: ProductCardProps) => {
  return (
    <div>
      <h1>In ProductCard</h1>
    </div>
  )
}

export default ProductCard;
