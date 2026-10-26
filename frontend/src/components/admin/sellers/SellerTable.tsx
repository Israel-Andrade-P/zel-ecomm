import { DataGrid } from "@mui/x-data-grid";
import { useState } from "react";
import { useNavigate, useSearchParams, useLocation } from "react-router-dom";
import { adminUserTableColumns } from "../../helper/TableColumn";

const SellerTable = ({ sellers, pagination }) => {
  const [currentPage, setCurrentPage] = useState(pagination?.pageNumber + 1 || 1);
  const navigate = useNavigate();

  const [searchParams] = useSearchParams();
  const params = new URLSearchParams(searchParams);
  const pathname = useLocation().pathname;

  const tableRecords = sellers?.map((item) => {
    return {
      username: item.username,
      email: item.email,
      dob: item.dob,
      telephone: item.telephone,
    }
  })

  const handlePaginationChange = (paginationModel) => {
    const page = paginationModel.page + 1;
    setCurrentPage(page);
    params.set("page", page.toString());
    navigate(`${pathname}?${params}`);
  }

  return (
    <div>
      <h1 className="text-slate-800 text-3xl text-center font-bold pb-6 uppercase">Sellers</h1>
      <div>
        <DataGrid
          className="w-full"
          rows={tableRecords}
          columns={adminUserTableColumns()}
          getRowId={(row) => row.username}
          paginationMode="server"
          rowCount={pagination?.totalElements || 0}
          initialState={{
            pagination: {
              paginationModel: {
                pageSize: pagination.pageSize || 10,
                page: currentPage - 1
              },
            },
          }}
          onPaginationModelChange={handlePaginationChange}
          disableColumnResize
          pageSizeOptions={[pagination?.pageSize || 10]}
          pagination
          checkboxSelection
          disableRowSelectionOnClick
          paginationOptions={{
            showFirstButton: true,
            showLastButton: true,
            hideNextButton: currentPage === pagination?.totalPages,
          }}
        />
      </div>
    </div>
  )
}

export default SellerTable
