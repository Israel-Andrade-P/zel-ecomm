const hasRole = (user, role) => user?.roles?.includes(role) ?? false;

export const isAdminUser = (user) => hasRole(user, "ROLE_ADMIN");
export const isSellerUser = (user) => hasRole(user, "ROLE_SELLER");

