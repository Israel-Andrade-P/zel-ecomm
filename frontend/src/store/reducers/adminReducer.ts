const intialState = { analytics: {} };

export const adminReducer = (state = intialState, action) => {
  switch (action.type) {
    case "FETCH_ANALYTICS":
      return { ...state, analytics: action.payload };
    default:
      return state;
  }
};
