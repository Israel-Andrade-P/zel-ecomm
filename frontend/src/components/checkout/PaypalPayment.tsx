import { Alert, AlertTitle } from "@mui/material"

const PaypalPayment = () => {
    return (
        <div className="h-96 flex justify-center items-center">
            <Alert severity="warning" variant="filled" style={{ maxWidth: "400px" }}>
                <AlertTitle>Payment method not available</AlertTitle>
                Paypal not available. Please select a different payment method
            </Alert>
        </div>
    )
}

export default PaypalPayment
