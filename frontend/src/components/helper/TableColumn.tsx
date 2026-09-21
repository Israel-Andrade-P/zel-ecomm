import { type GridColDef } from "@mui/x-data-grid";
import { FaEdit, FaEye, FaImage, FaTrashAlt } from "react-icons/fa";

export const adminProductTableColumns = (
    onEdit: (item: any) => void,
    onDelete: (item: any) => void,
    onImageUpload: (item: any) => void,
    onProductView: (item: any) => void
): GridColDef[] => [
        {
            sortable: false,
            disableColumnMenu: true,
            field: "id",
            headerName: "productId",
            minWidth: 180,
            headerAlign: "center",
            editable: false,
            headerClassName: "text-black font-semibold border",
            cellClassName: "text-slate-700 font-normal border",
            renderHeader: (params) => <span className="text-center">Product ID</span>
        },
        {
            sortable: false,
            disableColumnMenu: true,
            field: "productName",
            headerName: "Name",
            align: "center",
            minWidth: 250,
            headerAlign: "center",
            editable: false,
            headerClassName: "text-black font-semibold text-center border",
            cellClassName: "text-slate-700 font-normal border text-center",
            renderHeader: (params) => <span className="text-center">Name</span>
        },
        {
            sortable: true,
            disableColumnMenu: true,
            field: "description",
            headerName: "Description",
            align: "center",
            width: 200,
            headerAlign: "center",
            editable: false,
            headerClassName: "text-black font-semibold text-center border",
            cellClassName: "text-slate-700 font-normal border text-center",
            renderHeader: (params) => <span>Description</span>
        },
        {
            sortable: false,
            disableColumnMenu: true,
            field: "quantity",
            headerName: "Quantity",
            align: "center",
            width: 200,
            headerAlign: "center",
            editable: false,
            headerClassName: "text-black font-semibold text-center border",
            cellClassName: "text-slate-700 font-normal border text-center",
            renderHeader: (params) => <span>Quantity</span>
        },
        {
            sortable: false,
            disableColumnMenu: true,
            field: "image",
            headerName: "Image",
            align: "center",
            width: 200,
            headerAlign: "center",
            editable: false,
            headerClassName: "text-black font-semibold text-center border",
            cellClassName: "text-slate-700 font-normal border text-center",
            renderHeader: (params) => <span>Image</span>
        },
        {
            sortable: false,
            disableColumnMenu: true,
            field: "price",
            headerName: "Price",
            align: "center",
            width: 200,
            headerAlign: "center",
            editable: false,
            headerClassName: "text-black font-semibold text-center border",
            cellClassName: "text-slate-700 font-normal border text-center",
            renderHeader: (params) => <span>Price</span>
        },
        {
            sortable: false,
            disableColumnMenu: true,
            field: "specialPrice",
            headerName: "Special Price",
            align: "center",
            width: 200,
            headerAlign: "center",
            editable: false,
            headerClassName: "text-black font-semibold text-center border",
            cellClassName: "text-slate-700 font-normal border text-center",
            renderHeader: (params) => <span>Special Price</span>
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
                        <button className="flex items-center bg-green-500 hover:bg-green-800 text-white px-4 h-9 rounded-md"
                            onClick={() => { onImageUpload(params.row) }}>
                            <FaImage className="mr-2" />
                            Image
                        </button>
                        <button className="flex items-center bg-blue-500 hover:bg-blue-800 text-white px-4 h-9 rounded-md" onClick={() => { onEdit(params.row) }}>
                            <FaEdit className="mr-2" />
                            Edit
                        </button>
                        <button className="flex items-center bg-red-500 hover:bg-red-800 text-white px-4 h-9 rounded-md" onClick={() => { onDelete(params.row) }}>
                            <FaTrashAlt className="mr-2" />
                            Delete
                        </button>
                        <button className="flex items-center bg-slate-800 hover:bg-slate-500 text-white px-4 h-9 rounded-md"
                            onClick={() => { onProductView(params.row) }}>
                            <FaEye className="mr-2" />
                            View
                        </button>
                    </div>
                )
            },
        },
    ];

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
