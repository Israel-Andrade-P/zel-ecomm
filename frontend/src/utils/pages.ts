export const updatePagination = (pagination, action) => ({
  ...pagination,
  pageNumber: action.pageNumber,
  pageSize: action.pageSize,
  totalElements: action.totalElements,
  totalPages: action.totalPages,
  lastPage: action.lastPage,
});
