import { FaBoxOpen, FaDollarSign, FaShoppingCart } from "react-icons/fa";
import DashboardOverview from "./DashboardOverview"
import { MdAttachMoney } from "react-icons/md";

const Dashboard = () => {
    const { productCount, totalRevenue, totalOrders } = { productCount: "15", totalRevenue: "6800.00", totalOrders: "9" };

    return (
        <div>
            <div className="flex md:flex-row mt-8 flex-col lg:justify-between border border-slate-400 rounded-lg bg-linear-to-r from-blue-50 to-blue-100 shadow-lg">
                <DashboardOverview title="Total Products" amount={productCount} Icon={FaBoxOpen} />
                <DashboardOverview title="Total Orders" amount={totalOrders} Icon={FaShoppingCart} />
                <DashboardOverview title="Total Revenue" amount={totalRevenue} Icon={FaDollarSign} isCurrency />
            </div>
        </div>
    )
}

export default Dashboard
