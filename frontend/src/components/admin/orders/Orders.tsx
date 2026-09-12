import { FaShoppingCart } from "react-icons/fa";
import OrderTable from "./OrderTable";
import { useSelector } from "react-redux";
import useOrderFilter from "../../../hooks/useOrderFilter";

const Orders = () => {
    const { orders, pagination } = useSelector((state) => state.order);
    const isOrderEmpty = !orders || orders?.length <= 0;

    useOrderFilter();

    if (isOrderEmpty) return (
        <div className="flex flex-col items-center justify-center text-gray-600 py-10">
            <FaShoppingCart size={50} className="mb-3" />
            <h2 className="text-2xl font-semibold">No orders have been found :(</h2>
        </div>
    )

    return (
        <div className="pb-6 pt-20">
            <OrderTable orders={orders} pagination={pagination} />
        </div>
    )
}

export default Orders
