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

export const increaseItemQnt =
  (data, toast, currentQuantity, setCurrentQuantity) =>
  (dispatch, getState) => {
    const { products } = getState().products;

    if (!products) {
      toast.error("Products are still loading");
      return;
    }

    const product = products.find((item) => item.publicId === data.publicId);

    const isInStock = product.quantity >= currentQuantity + 1;

    if (isInStock) {
      const newQnt = currentQuantity + 1;
      setCurrentQuantity(newQnt);
      dispatch({
        type: "ADD_TO_CART",
        payload: { ...data, quantity: newQnt + 1 },
      });
      localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
    } else {
      toast.error("Item not in stock");
    }
  };

export const decreaseItemQnt = (data, newQnt) => (dispatch, getState) => {
  dispatch({
    type: "ADD_TO_CART",
    payload: { ...data, quantity: newQnt },
  });
  localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
};

export const removeFromCart = (data, toast) => (dispatch, getState) => {
  dispatch({ type: "REMOVE_FROM_CART", payload: data });
  toast.success(`${data.name} was removed`);
  localStorage.setItem("cartItems", JSON.stringify(getState().carts.cart));
};

export const authenticateUser =
  (sendData, toast, reset, navigate, setLoader) => async (dispatch) => {
    try {
      setLoader(true);
      const { data } = await api.post("/auth/login", sendData);
      dispatch({ type: "LOGIN_USER", payload: data });
      localStorage.setItem("auth", JSON.stringify(data));
      reset();
      toast.success("Login success");
      navigate("/");
    } catch (err) {
      console.log(err);
      toast.error(err?.response?.data?.message || "Internal Server Error");
    } finally {
      setLoader(false);
    }
  };

export const registerUser =
  (sendData, toast, reset, navigate, setLoader) => async (dispatch) => {
    try {
      setLoader(true);
      const { data } = await api.post("/auth/register", sendData);
      reset();
      toast.success(data?.message || "Registered successfully");
      navigate("/login");
    } catch (err) {
      console.log(err);
      toast.error(err?.response?.data?.message || "Internal Server Error");
    } finally {
      setLoader(false);
    }
  };

export const logUserOut = (navigate) => (dispatch) => {
  dispatch({ type: "LOG_OUT" });
  localStorage.removeItem("auth");
  navigate("/login");
};

export const addUpdateUserAddress =
  (sendData, toast, publicId, setOpen) => async (dispatch, getState) => {
    dispatch({ type: "BUTTON_LOADER" });

    try {
      if (publicId) {
        await api.put(`/locations/update/${publicId}`, sendData);
        toast.success("Address updated");
        dispatch({ type: "FETCH_SUCCESS" });
      } else {
        const { data } = await api.post("/locations/add", sendData);
        toast.success("Address added");
        dispatch({ type: "FETCH_SUCCESS" });
      }
      dispatch(fetchUserAddresses());
    } catch (err) {
      console.log(err);
      toast.error(err?.response?.data?.message || "Internal Server Error");
      dispatch({ type: "FETCH_ERROR", payload: null });
    } finally {
      setOpen(false);
    }
  };

export const fetchUserAddresses = () => async (dispatch, getState) => {
  try {
    dispatch({ type: "IS_FETCHING" });
    const { data } = await api.get(`/locations/user`);

    dispatch({ type: "USER_ADDRESS", payload: data });
    dispatch({ type: "FETCH_SUCCESS" });
  } catch (error) {
    console.log(error);
    dispatch({
      type: "FETCH_ERROR",
      payload:
        error?.response?.data?.message || "Failed to fetch user addresses",
    });
  }
};

export const selectUserAddress = (address) => {
  return {
    type: "SELECT_CHECKOUT_ADDRESS",
    payload: address,
  };
};

export const deleteUserAddress =
  (toast, publicId, setOpenDeleteModel) => async (dispatch, getState) => {
    try {
      dispatch({ type: "BUTTON_LOADER" });
      await api.delete(`/locations/delete/${publicId}`);
      dispatch({ type: "FETCH_SUCCESS" });
      toast.success("Address deleted");
      dispatch(fetchUserAddresses());
      dispatch(clearSelectedAddress());
    } catch (error) {
      console.log(error);
      dispatch({
        type: "FETCH_ERROR",
        payload: error?.response?.data?.message || "An ERROR has occurred",
      });
    } finally {
      setOpenDeleteModel(false);
    }
  };

export const clearSelectedAddress = () => {
  return {
    type: "DELETE_USER_ADDRESS",
  };
};
