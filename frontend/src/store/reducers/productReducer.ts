import { updatePagination } from "../../utils/pages";

const initialState = {
  products: [],
  categories: [],
  productsPagination: {},
  categoriesPagination: {},
  sellerProducts: [],
  sellerProductsPagination: {},
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
    case "FETCH_SELLER_PRODUCTS":
      return {
        ...state,
        sellerProducts: action.payload,
        sellerProductsPagination: updatePagination(state.sellerProductsPagination, action) 
      }
    default:
      return state;
  }
};

