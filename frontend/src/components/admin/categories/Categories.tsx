import { useSelector } from "react-redux";
import Spinners from "../../shared/Spinners";
import ErrorPage from "../../shared/ErrorPage";
import { FaShoppingCart } from "react-icons/fa";
import CategoryTable from "./CategoryTable";
import useCategoryFilter from "../../../hooks/useCategoryFilter";

const Categories = () => {
    const { categories, categoriesPagination } = useSelector((state) => state.products);
    const { isLoading, errorMessage } = useSelector((state) => state.uiStates);
    const isCategoryEmpty = categories?.length === 0;

  useCategoryFilter();

    if (isLoading) return (

        <div className="flex min-h-[60vh] items-center justify-center gap-3 text-lg">
            <Spinners />
            Loading categories...
        </div>
    )

    if (errorMessage) return (
        <ErrorPage message={errorMessage} />
    )

    if (isCategoryEmpty) return (
        <div className="flex flex-col items-center justify-center text-gray-600 py-10">
            <FaShoppingCart size={50} className="mb-3" />
            <h2 className="text-2xl font-semibold">No categories found</h2>
        </div>
    )

    return (
        <div className="pb-6 pt-20">
            <CategoryTable categories={categories} pagination={categoriesPagination} />
        </div>
    )
}

export default Categories;
