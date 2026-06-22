const intialState = {
  user: null,
  address: [],
};

export const authReducer = (state = intialState, action) => {
  switch (action.type) {
    case "LOGIN_USER":
      return { ...state, user: action.payload };

    default:
      return state;
  }
};
