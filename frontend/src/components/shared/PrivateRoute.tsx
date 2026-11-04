import { useSelector } from "react-redux";
import { Navigate, Outlet, useLocation } from "react-router-dom";
import { isAdminUser, isSellerUser } from "../../utils/auth";

const PrivateRoute = ({ publicPage = false, adminOnly = false }) => {
  const { user } = useSelector((state) => state.auth);
  const location = useLocation();
  const isAdmin = isAdminUser(user);
  const isSeller = isSellerUser(user);

  if (publicPage) {
    return user ? <Navigate to="/" /> : <Outlet />
  }

  if (adminOnly) {
    if(isSeller) {
      const sellerAllowedPaths = ["/admin/orders", "/admin/products"]
      const sellerAllowed = sellerAllowedPaths.some(path => location.pathname.startsWith(path));
      if(!sellerAllowed) return <Navigate to="/" replace/>
    }
  }

  if (!isAdmin && !isSeller) return <Navigate to="/" />

  return user ? <Outlet /> : <Navigate to="/login" />
};

export default PrivateRoute;
