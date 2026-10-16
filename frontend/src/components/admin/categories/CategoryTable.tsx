import { DataGrid } from "@mui/x-data-grid";
import { useState } from "react";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import { adminCategoryTableColumns } from "../../helper/TableColumn";
import Model from "../../shared/Model";
import CategoryForm from "./CategoryForm";
import { MdAddShoppingCart } from "react-icons/md";
import DeleteModel from "../../shared/DeleteModel";
import { useDispatch, useSelector } from "react-redux";
import { deleteCategory } from "../../../store/actions";
import toast from "react-hot-toast";

const CategoryTable = ({ categories, pagination }) => {
  const { btnLoader } = useSelector((state) => state.uiStates);
  const [currentPage, setCurrentPage] = useState(pagination?.pageNumber + 1 || 1);
  const [openAddModel, setOpenAddModel] = useState(false);
  const [openUpdateModel, setOpenUpdateModel] = useState(false);
  const [openDeleteModel, setOpenDeleteModel] = useState(false);
  const [selectedItem, setSelectedItem] = useState("");
  const dispatch = useDispatch();
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
    setSelectedItem(item);
    setOpenDeleteModel(true);
  }

  const onDeleteHandler = async () => {
    const result = await dispatch(deleteCategory(selectedItem?.name));

    if (!result.success) {
      toast.error("Failed deleting category")
      return;
    }

    toast.success("Category deleted")
    setOpenDeleteModel(false);
  }

  const handlePaginationChange = (paginationModel) => {
    const page = paginationModel.page + 1;
    setCurrentPage(page);
    params.set("page", page.toString());
    navigate(`${pathname}?${params}`);
  }

  return (
    <div>
      <div className="pt-6 pb-10 flex justify-end">
        <button onClick={() => setOpenAddModel(true)}
          className="bg-custom-blue hover:bg-blue-800 text-white font-semibold py-2 px-4 flex items-center gap-2 rounded-md shadow-md transition-colors 
                     hover:text-slate-300 duration-300" >
          <MdAddShoppingCart className="text-xl" />
          Add Category
        </button>
      </div>

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

      <DeleteModel open={openDeleteModel} setOpen={setOpenDeleteModel} loader={btnLoader} title="Delete Category" onDeleteHandler={onDeleteHandler} />
    </div>
  )
}

export default CategoryTable
