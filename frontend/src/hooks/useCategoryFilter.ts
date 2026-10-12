import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { fetchCategories } from "../store/actions";

const useCategoryFilter = () => {
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();
  const { categories } = useSelector((state) => state.products);

  useEffect(() => {
    const params = new URLSearchParams();

    const currentPage = searchParams.get("page")
      ? Number(searchParams.get("page"))
      : 1;

    params.set("page", currentPage - 1);

    const queryString = params.toString();
    console.log("Query:", queryString);

    dispatch(fetchCategories(queryString));
  }, [dispatch, searchParams]);

  console.log("Categories:", categories);
};

export default useCategoryFilter;
