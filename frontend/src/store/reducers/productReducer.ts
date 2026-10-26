import { updatePagination } from "../../utils/pages";

const initialState = {
  products: [],
  categories: [],
  productsPagination: {},
  categoriesPagination: {},
};

export const productReducer = (state = initialState, action) => {
  switch (action.type) {
    case "FETCH_PRODUCTS":
      return {
        ...state,
        products: action.payload,
        productsPagination: updatePagination(state.productsPagination, action)       
      };
    case "FETCH_CATEGORIES":
      return {
        ...state,
        categories: action.payload,
        categoriesPagination: updatePagination(state.categoriesPagination, action) 
      }
    default:
      return state;
  }
};

