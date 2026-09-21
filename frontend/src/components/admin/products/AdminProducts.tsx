import { MdAddShoppingCart } from "react-icons/md";
import { useSelector } from "react-redux";
import Spinners from "../../shared/Spinners";
import { FaBoxOpen } from "react-icons/fa";
import { DataGrid } from "@mui/x-data-grid";
import { adminProductTableColumns } from "../../helper/TableColumn";
import { useState } from "react";
import useProductFilter from "../../../hooks/useProductFilter";
import Model from "../../shared/Model";
import AddProductForm from "./AddProductForm";

const AdminProducts = () => {
    const { products, pagination } = useSelector((state) => state.products);
    const { isLoading, errorMessage } = useSelector((state) => state.uiStates);
    const [openUpdateModel, setOpenUpdateModel] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState("");
    const [currentPage, setCurrentPage] = useState(pagination?.pageNumber + 1 || 1);
    const isProductsEmpty = products?.length === 0;
    const tableRecords = products?.map((item) => {
        return {
            id: item.productId,
            productName: item.productName,
            description: item.description,
            quantity: item.quantity,
            image: item.image,
            price: item.price,
            discount: item.discount,
            specialPrice: item.specialPrice,
        }
    })

    const onEdit = (product) => {
        setSelectedProduct(product);
        setOpenUpdateModel(true);
    }
    const onDelete = (product) => { }
    const onImageUpload = (product) => { }
    const onProductView = (product) => { }
    const handlePaginationChange = (pagination) => { }

    useProductFilter();

    if (isLoading) return (

        <div className="flex min-h-[60vh] items-center justify-center gap-3 text-lg">
            <Spinners />
            Loading products...
        </div>

    )
    return (
        <div>
            <div className="pt-6 pb-10 flex justify-end">
                <button className="bg-custom-blue hover:bg-blue-800 text-white font-semibold py-2 px-4 flex items-center gap-2 rounded-md shadow-md
                                       transition-colors hover:text-slate-300 duration-300">
                    <MdAddShoppingCart className="text-xl" />
                    Add Product
                </button>
            </div>

            {
                isProductsEmpty ? (
                    <div className="flex flex-col items-center justify-center text-gray-600 py-10">
                        <FaBoxOpen size={50} className="mb-3" />
                        <h2 className="text-2xl font-semibold">No products found</h2>
                    </div>
                ) : (
                    <>
                        <h1 className="text-slate-800 text-3xl text-center font-bold pb-6 uppercase">All Products</h1>
                        <div className="max-w-full">
                            <DataGrid
                                className="w-full"
                                rows={tableRecords}
                                columns={adminProductTableColumns(onEdit, onDelete, onImageUpload, onProductView)}
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

                            <Model open={openUpdateModel} setOpen={setOpenUpdateModel} title="Update Product">
                                <AddProductForm setOpen={setOpenUpdateModel} product={selectedProduct} isUpdate={openUpdateModel} />
                            </Model>
                        </div>
                    </>
                )
            }
        </div>
    )
}

export default AdminProducts
