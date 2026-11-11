import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { fetchProducts, fetchSellerProducts } from "../store/actions";

type ProductMode = "public" | "seller"

const useProductFilter = (mode: ProductMode) => {
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();

  useEffect(() => {
    const params = new URLSearchParams();

    const currentPage = searchParams.get("page")
      ? Number(searchParams.get("page"))
      : 1;

    params.set("page", currentPage - 1);

    const sortOrder = searchParams.get("sortOrder") || "asc";
    const categoryParams = searchParams.get("category") || null;
    const keyword = searchParams.get("keyword") || null;
    params.set("sortBy", "price");
    params.set("sortOrder", sortOrder);

    if (categoryParams) {
      params.set("category", categoryParams);
    }

    if (keyword) {
      params.set("keyword", keyword);
    }

    const queryString = params.toString();
    console.log("Query:", queryString);

    if (mode === "seller") {
      dispatch(fetchSellerProducts(queryString));
      return;
    } else {
      dispatch(fetchProducts(queryString));
    }

  }, [dispatch, searchParams, mode]);
};

export default useProductFilter;
