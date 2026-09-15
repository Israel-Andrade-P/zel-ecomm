import { DataGrid } from "@mui/x-data-grid";
import { adminOrderTableColumns } from "../../helper/TableColumn";
import { useState } from "react";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import Model from "../../shared/Model";
import UpdateOrderForm from "./UpdateOrderForm";

const OrderTable = ({ orders, pagination }) => {
    const [currentPage, setCurrentPage] = useState(pagination?.pageNumber + 1 || 1);
    const [updateOpenModel, setUpdateOpenModel] = useState(false);
    const [selectedItem, setSelectedItem] = useState("");
    const navigate = useNavigate();

    const [searchParams] = useSearchParams();
    const params = new URLSearchParams(searchParams);
    const pathname = useLocation().pathname;

    const tableRecords = orders?.map((item) => {
        return {
            id: item.orderId,
            email: item.userEmail,
            totalAmount: item.totalPrice,
            status: item.status,
            date: item.createdAt,
        }
    })

    const handleEdit = (item) => {
        setSelectedItem(item);
        setUpdateOpenModel(true);
    }

    const handlePaginationChange = (paginationModel) => {
        const page = paginationModel.page + 1;
        setCurrentPage(page);
        params.set("page", page.toString());
        navigate(`${pathname}?${params}`);
    }

    return (
        <div>
            <h1 className="text-slate-800 text-3xl text-center font-bold pb-6 uppercase">All orders</h1>
            <div>
                <DataGrid
                    className="w-full"
                    rows={tableRecords}
                    columns={adminOrderTableColumns(handleEdit)}
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

            <Model open={updateOpenModel} setOpen={setUpdateOpenModel} title="Update Order Status">
                <UpdateOrderForm
                    open={updateOpenModel}
                    setOpen={setUpdateOpenModel}
                    selectedId={selectedItem.id}
                    selectedItem={selectedItem}></UpdateOrderForm>
            </Model>
        </div>
    )
}

export default OrderTable
