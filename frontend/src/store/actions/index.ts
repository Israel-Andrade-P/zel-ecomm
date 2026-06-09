import api from "../../api/api";

export const fetchProducts = (queryString: string) => async (dispatch) => {
  try {
    dispatch({ type: "IS_FETCHING" });
    const { data } = await api.get(`/products?${queryString}`);
    dispatch({
      type: "FETCH_PRODUCTS",
      payload: data.content,
      pageNumber: data.pageNumber,
      pageSize: data.pageSize,
      totalElements: data.totalElements,
      totalPages: data.totalPages,
      lastPage: data.lastPage,
    });
    dispatch({ type: "FETCH_SUCCESS" });
  } catch (error) {
    console.log(error);
    dispatch({
      type: "FETCH_ERROR",
      payload: error?.response?.data?.message || "Failed to fetch products",
    });
  }
};

export const fetchCategories = () => async (dispatch) => {
  try {
    dispatch({ type: "CATEGORY_LOADER" });
    const { data } = await api.get(`/categories`);
    dispatch({
      type: "FETCH_CATEGORIES",
      payload: data.content,
      pageNumber: data.pageNumber,
      pageSize: data.pageSize,
      totalElements: data.totalElements,
      totalPages: data.totalPages,
      lastPage: data.lastPage,
    });
    dispatch({ type: "CATEGORY_SUCCESS" });
  } catch (error) {
    console.log(error);
    dispatch({
      type: "FETCH_ERROR",
      payload: error?.response?.data?.message || "Failed to fetch categories",
    });
  }
};

export const addToCart =
  (data, qnt = 1, toast) =>
  (dispatch, getState) => {
    //find product
    const { products } = getState().products;
    const product = products.find((item) => item.publicId === data.publicId);

    //quantity check
    const isInStock = product.quantity >= qnt;

    if (isInStock) {
      dispatch({ type: "ADD_TO_CART", payload: { ...data, quantity: qnt } });
      toast.success(`${data?.name} added to cart`);
      localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
    } else {
      toast.error("Out of stock");
    }
  };
