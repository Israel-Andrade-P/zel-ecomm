export const formatPrice = (amount) => {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
  }).format(amount);
};

export const priceCalculation = (price, quantity) => {
  return (Number(price) * Number(quantity)).toFixed(2);
};
