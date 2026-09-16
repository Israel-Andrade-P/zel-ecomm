import { FaBoxOpen, FaDollarSign, FaShoppingCart } from "react-icons/fa";
import DashboardOverview from "./DashboardOverview"
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { getAdminAnalytics } from "../../../store/actions";
import Loader from "../../shared/Loader";
import ErrorPage from "../../shared/ErrorPage";

const Dashboard = () => {
    const dispatch = useDispatch();
    const { analytics: { totalProducts, totalRevenue, totalOrders } } = useSelector((state) => state.admin);
    const { isLoading, errorMessage } = useSelector((state) => state.uiStates)

    useEffect(() => {
        dispatch(getAdminAnalytics());
    }, [])

    if (isLoading) {
        return <Loader text="Fetching Analytics..." />
    }

    if (errorMessage) {
        return <ErrorPage message={errorMessage} />
    }

    return (
        <div>
            <div className="flex md:flex-row mt-8 flex-col lg:justify-between border border-slate-400 rounded-lg bg-linear-to-r from-blue-50 to-blue-100 shadow-lg">
                <DashboardOverview title="Total Products" amount={totalProducts} Icon={FaBoxOpen} />
                <DashboardOverview title="Total Orders" amount={totalOrders} Icon={FaShoppingCart} />
                <DashboardOverview title="Total Revenue" amount={totalRevenue} Icon={FaDollarSign} isCurrency />
            </div>
        </div>
    )
}

export default Dashboard
