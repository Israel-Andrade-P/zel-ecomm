import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { fetchSellers } from "../store/actions";

const useSellerFilter = () => {
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();
  const { sellers } = useSelector((state) => state.users);

  useEffect(() => {
    const params = new URLSearchParams();

    const currentPage = searchParams.get("page")
      ? Number(searchParams.get("page"))
      : 1;

    params.set("page", currentPage - 1);

    const queryString = params.toString();
    console.log("Query:", queryString);

    dispatch(fetchSellers(queryString));
  }, [dispatch, searchParams]);

  console.log("Sellers:", sellers);
};

export default useSellerFilter;
