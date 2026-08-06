const initialState = {
  currentOrderId: null,
  orderTotalPrice: 0.0,
};

export const orderReducer = (state = initialState, action) => {
  switch (action.type) {
    case "SET_CURRENT_ORDER":
      return {
        ...state,
        currentOrderId: action.payload.orderId,
        orderTotalPrice: action.payload.totalPrice,
      };
    default:
      return state;
  }
};
