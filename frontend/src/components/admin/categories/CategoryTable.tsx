import { DataGrid } from "@mui/x-data-grid";
import { useState } from "react";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import { adminCategoryTableColumns } from "../../helper/TableColumn";
import Model from "../../shared/Model";
import CategoryForm from "./CategoryForm";

const CategoryTable = ({ categories, pagination }) => {
  const [currentPage, setCurrentPage] = useState(pagination?.pageNumber + 1 || 1);
  const [openAddModel, setOpenAddModel] = useState(false);
  const [openUpdateModel, setOpenUpdateModel] = useState(false);
  const [selectedItem, setSelectedItem] = useState("");
  const navigate = useNavigate();

  const [searchParams] = useSearchParams();
  const params = new URLSearchParams(searchParams);
  const pathname = useLocation().pathname;

  const tableRecords = categories?.map((item) => {
    return {
      name: item.name,
    }
  })

  const handleEdit = (item) => {
    setSelectedItem(item);
    console.log("Selected Item:", item);
    
    setOpenUpdateModel(true);
  }

  const handleDelete = (item) => {

  }

  const handlePaginationChange = (paginationModel) => {
    const page = paginationModel.page + 1;
    setCurrentPage(page);
    params.set("page", page.toString());
    navigate(`${pathname}?${params}`);
  }

  return (
    <div>
      <h1 className="text-slate-800 text-3xl text-center font-bold pb-6 uppercase">Categories</h1>
      <div>
        <DataGrid
          className="w-full"
          rows={tableRecords}
          columns={adminCategoryTableColumns(handleEdit, handleDelete)}
          getRowId={(row) => row.name}
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

      <Model 
        open={openAddModel || openUpdateModel} 
        setOpen={openAddModel ? setOpenAddModel : setOpenUpdateModel} 
        title={openAddModel ? "Add Category" : "Update Category"}>
        <CategoryForm
          setOpen={openAddModel ? setOpenAddModel : setOpenUpdateModel}
          category={selectedItem}
          isUpdate={openUpdateModel}>
        </CategoryForm>
      </Model>
    </div>
  )
}

export default CategoryTable
