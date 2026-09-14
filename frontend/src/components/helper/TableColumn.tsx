import { type GridColDef } from "@mui/x-data-grid";
import { FaEdit } from "react-icons/fa";

export const adminOrderTableColumns = (onEdit: (item: any) => void): GridColDef[] => [
    {
        sortable: false,
        disableColumnMenu: true,
        field: "id",
        headerName: "orderId",
        minWidth: 180,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold border",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: (params) => <span className="text-center">Order ID</span>
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "email",
        headerName: "Email",
        align: "center",
        minWidth: 250,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center border",
        cellClassName: "text-slate-700 font-normal border text-center",
        renderHeader: (params) => <span className="text-center">Email</span>
    },
    {
        sortable: true,
        disableColumnMenu: true,
        field: "totalAmount",
        headerName: "Total Amount",
        align: "center",
        width: 200,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center border",
        cellClassName: "text-slate-700 font-normal border text-center",
        renderHeader: (params) => <span>Total Amount</span>
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "status",
        headerName: "Status",
        align: "center",
        width: 200,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center border",
        cellClassName: "text-slate-700 font-normal border text-center",
        renderHeader: (params) => <span>Status</span>
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "date",
        headerName: "Order Date",
        align: "center",
        width: 200,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center border",
        cellClassName: "text-slate-700 font-normal border text-center",
        renderHeader: (params) => <span>Date</span>
    },
    {
        sortable: false,
        disableColumnMenu: true,
        field: "action",
        headerName: "Action",
        align: "center",
        width: 250,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center border",
        cellClassName: "text-slate-700 font-normal border text-center",
        renderHeader: (params) => <span>Action</span>,
        renderCell: (params) => {
            return (
                <div className="flex justify-center items-center space-x-2 h-full pt-2">
                    <button className="flex items-center bg-blue-500 text-white px-4 h-9 rounded-md" onClick={() => { onEdit(params.row) }}>
                        <FaEdit className="mr-2" />
                        Edit
                    </button>
                </div>
            )
        },
    },
];
