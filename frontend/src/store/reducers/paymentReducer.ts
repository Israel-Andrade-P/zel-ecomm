const initialState = {
  paymentMethod: null,
  clientSecret: null,
  paymentConfirmed: false,
  isLoading: false,
  errorMessage: null,
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
    case "PAYMENT_CONFIRM_REQUEST":
      return {
        ...state,
        isLoading: true,
        errorMessage: null,
        paymentConfirmed: false,
      };

    case "PAYMENT_CONFIRM_SUCCESS":
      return {
        ...state,
        isLoading: false,
        paymentConfirmed: true,
        errorMessage: null,
      };

    case "PAYMENT_CONFIRM_FAILURE":
      return {
        ...state,
        isLoading: false,
        paymentConfirmed: false,
        errorMessage: action.payload,
      };
    default:
      return state;
  }
};
