import { useSelector } from "react-redux";
import useSellerFilter from "../../../hooks/useSellerFilter";
import Spinners from "../../shared/Spinners";
import ErrorPage from "../../shared/ErrorPage";
import { FaShoppingCart } from "react-icons/fa";
import SellerTable from "./SellerTable";

const Sellers = () => {
  const { sellers, sellersPagination } = useSelector((state) => state.users);
  const { isLoading, errorMessage } = useSelector((state) => state.uiStates);
  const isSelelrsEmpty = sellers?.length === 0;

  useSellerFilter();

  if (isLoading) return (

    <div className="flex min-h-[60vh] items-center justify-center gap-3 text-lg">
      <Spinners />
      Loading sellers...
    </div>
  )

  if (errorMessage) return (
    <ErrorPage message={errorMessage} />
  )

  if (isSelelrsEmpty) return (
    <div className="flex flex-col items-center justify-center text-gray-600 py-10">
      <FaShoppingCart size={50} className="mb-3" />
      <h2 className="text-2xl font-semibold">No sellers found</h2>
    </div>
  )

  return (
    <div className="pb-6 pt-20">
      <SellerTable sellers={sellers} pagination={sellersPagination} />
    </div>
  )
}

export default Sellers
