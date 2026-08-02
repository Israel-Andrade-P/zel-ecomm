const initialState = {
  paymentMethod: null,
  clientSecret: null,
};

export const paymentReducer = (state = initialState, action) => {
  switch (action.type) {
    case "ADD_PAYMENT_METHOD":
      return {
        ...state,
        paymentMethod: action.payload,
      };
    case "STRIPE_CLIENT_SECRET":
      return { ...state, clientSecret: action.payload };
    case "REMOVE_CLIENT_SECRET":
      return { ...state, clientSecret: null };
    default:
      return state;
  }
};
