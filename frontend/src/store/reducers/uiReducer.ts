const initialState = {
  isLoading: false,
  errorMessage: null,
  categoryLoader: false,
  categoryError: null,
  btnLoader: false,
};

export const uiReducer = (state = initialState, action) => {
  switch (action.type) {
    case "IS_LOADING":
      return {
        ...state,
        isLoading: true,
        errorMessage: null,
        btnLoader: true,
      };

    case "RESPONSE_SUCCESS":
      return {
        ...state,
        isLoading: false,
        errorMessage: null,
        categoryLoader: false,
        categoryError: null,
        btnLoader: false,
      };

    case "RESPONSE_ERROR":
      return {
        ...state,
        isLoading: false,
        errorMessage: action.payload,
        btnLoader: false,
        categoryLoader: false,
      };

    case "BUTTON_LOADER":
      return {
        ...state,
        btnLoader: true,
        errorMessage: null,
        categoryError: null,
      };

    case "CATEGORY_SUCCESS":
      return {
        ...state,
        categoryLoader: false,
        errorMessage: null,
        categoryError: null,
      };

    case "CATEGORY_LOADER":
      return {
        ...state,
        categoryLoader: true,
        errorMessage: null,
        categoryError: null,
      };

    default:
      return state;
  }
};
