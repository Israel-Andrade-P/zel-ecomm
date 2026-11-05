import { useSelector } from "react-redux";
import { Navigate, Outlet, useLocation } from "react-router-dom";
import { isAdminUser, isSellerUser } from "../../utils/auth";

const PrivateRoute = ({ publicPage = false, adminOnly = false }) => {
  const { user } = useSelector((state) => state.auth);
  const location = useLocation();

  if (publicPage) {
    return user ? <Navigate to="/" replace /> : <Outlet />
  }

  if (!user) {
    return <Navigate to="/login" state={{ from: location }} replace />
  }

  const isAdmin = isAdminUser(user);
  const isSeller = isSellerUser(user);

  if (adminOnly) {
    if (isAdmin) return <Outlet />;

    const sellerAllowedPaths = ["/admin/orders", "/admin/products"]
    const sellerAllowed = sellerAllowedPaths.some(path => location.pathname.startsWith(path));

    if (isSeller && sellerAllowed) return <Outlet />

    return <Navigate to="/" replace/>

  }

  return <Outlet />
};

export default PrivateRoute;
