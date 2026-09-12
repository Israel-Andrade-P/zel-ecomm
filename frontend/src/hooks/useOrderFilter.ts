import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { fetchOrders } from "../store/actions";

const useOrderFilter = () => {
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();
  const { orders } = useSelector((state) => state.order);

  useEffect(() => {
    const params = new URLSearchParams();

    const currentPage = searchParams.get("page")
      ? Number(searchParams.get("page"))
      : 1;

    params.set("page", currentPage - 1);

    const queryString = params.toString();
    console.log("Query:", queryString);

    dispatch(fetchOrders(queryString));
  }, [dispatch, searchParams]);

  console.log("Orders:", orders);
};

export default useOrderFilter;
