import { updatePagination } from "../../utils/pages";

const intialState = {
  users: [],
  sellers: [],
  admins: [],
  usersPagination: {},
  sellersPagination: {},
  adminsPagination: {},
};

export const userReducer = (state = intialState, action) => {
  switch (action.type) {
    case "FETCH_SELLERS":
      return {
        ...state,
        sellers: action.payload,
        sellersPagonation: updatePagination(state.sellersPagination, action)
      };
    default:
      return state;
  }
};
