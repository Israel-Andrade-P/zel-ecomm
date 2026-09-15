import { Button, FormControl, FormHelperText, InputLabel, MenuItem, Select } from "@mui/material"
import { useState } from "react"
import Spinners from "../../shared/Spinners";
import { useDispatch, useSelector } from "react-redux";
import { updateOrderStatusField } from "../../../store/actions";
import toast from "react-hot-toast";

const ORDER_STATUS = [
    "PENDING_PAYMENT",
    "PAID",
    "PROCESSING",
    "SHIPPED",
    "DELIVERED",
    "CANCELED",
    "RETURNED",
    "FAILED",
]

const UpdateOrderForm = ({ setOpen, selectedId, selectedItem }) => {
    const [orderStatus, setOrderStatus] = useState(selectedItem?.status || "PROCESSING");
    const [error, setError] = useState("");
    const { isLoading, errorMessage } = useSelector((state) => state.errors);
    const dispatch = useDispatch();

    //OPTIMIZE REDUX AND USESTATE ERROR MANAGEMENT
    const updateOrderStatus = (e) => {
        e.preventDefault();

        if (!orderStatus) {
            setError("Order status required")
            return
        }

        dispatch(updateOrderStatusField(selectedId, orderStatus));

        if (errorMessage) {
            toast.error(errorMessage)
            return
        }
        toast.success("Order status updated")
    }

    return (
        <div className="py-5 relative h-full">
            <form className="space-y-4" onSubmit={updateOrderStatus}>
                <FormControl fullWidth variant="outlined" error={!!error}>
                    <InputLabel id="order-status-label">Order Status</InputLabel>
                    <Select labelId="order-status-label" label="Order Status" value={orderStatus} onChange={(e) => {
                        setOrderStatus(e.target.value)
                        setError("")
                    }}>
                        {
                            ORDER_STATUS.map((status) => (
                                <MenuItem key={status} value={status}>
                                    {status}
                                </MenuItem>
                            ))
                        }
                    </Select>

                    {error && <FormHelperText>{error}</FormHelperText>}
                </FormControl>

                <div className="flex w-full justify-between items-center absolute -bottom-9">
                    <Button disabled={isLoading} onClick={() => setOpen(false)} variant="outlined" className="text-white py-2.5 px-4 text-sm font-medium">
                        Cancel
                    </Button>
                    <Button disabled={isLoading} type="submit" variant="contained" color="primary" className="bg-custom-blue text-white py-2.5 px-4 text-sm font-medium">
                        {
                            isLoading ? (<div className="flex gap-2 items-center">
                                <Spinners />
                                Loading...
                            </div>) : ("Update")
                        }
                    </Button>
                </div>
            </form>
        </div>
    )
}

export default UpdateOrderForm
