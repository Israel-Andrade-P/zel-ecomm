import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { fetchOrders } from "../store/actions";
import { isAdminUser } from "../utils/auth";

const useOrderFilter = () => {
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();
  const { orders } = useSelector((state) => state.order);
  const { user } = useSelector((state) => state.auth);
  const isAdmin = isAdminUser(user);

  useEffect(() => {
    const params = new URLSearchParams();

    const currentPage = searchParams.get("page")
      ? Number(searchParams.get("page"))
      : 1;

    params.set("page", currentPage - 1);

    const queryString = params.toString();
    console.log("Query:", queryString);

    dispatch(fetchOrders(queryString, isAdmin));
  }, [dispatch, searchParams]);

  console.log("Orders:", orders);
};

export default useOrderFilter;
