const initialState = {
  currentOrderId: null,
  orderTotalPrice: 0.0,
  orders: [],
  pagination: {},
};

export const orderReducer = (state = initialState, action) => {
  switch (action.type) {
    case "SET_CURRENT_ORDER":
      return {
        ...state,
        currentOrderId: action.payload.orderId,
        orderTotalPrice: action.payload.totalPrice,
      };
    case "FETCH_ORDERS":
      return {
        ...state,
        orders: action.payload.content,
        pagination: {
          pageNumber: action.payload.pageNumber,
          pageSize: action.payload.pageSize,
          totalElements: action.payload.totalElements,
          totalPages: action.payload.totalPages,
          lastPage: action.payload.lastPage,
        },
      };
    default:
      return state;
  }
};
