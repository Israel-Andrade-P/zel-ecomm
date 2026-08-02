const initialState = {
  currentOrderId: null,
};

export const orderReducer = (state = initialState, action) => {
  switch (action.type) {
    case "SET_CURRENT_ORDER":
      return { ...state, currentOrderId: action.payload };
    default:
      return state;
  }
};
