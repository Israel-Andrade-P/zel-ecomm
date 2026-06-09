const initialState = {
  cart: [],
  totalPrice: 0,
  cartId: null,
};

export const cartReducer = (state = initialState, action) => {
  switch (action.type) {
    case "ADD_TO_CART":
      const productToAdd = action.payload;
      const existingProduct = state.cart.find(
        (item) => item.publicId === productToAdd.publicId,
      );

      if (existingProduct) {
        const updatedCart = state.cart.map((item) => {
          if (item.publicId === productToAdd.publicId) {
            return productToAdd;
          } else {
            return item;
          }
        });

        return { ...state, cart: updatedCart };
      } else {
        const newCart = [...state.cart, productToAdd];
        return { ...state, cart: newCart };
      }

    default:
      return state;
  }
};
