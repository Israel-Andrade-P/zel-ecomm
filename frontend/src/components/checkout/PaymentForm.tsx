import { Skeleton } from "@mui/material";
import { PaymentElement, useElements, useStripe } from "@stripe/react-stripe-js";
import { useState } from "react";
import { priceCalculation } from "../../utils/formatPrice";

const PaymentForm = (clientSecret, totalPrice) => {
    const stripe = useStripe();
    const elements = useElements();
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const paymentElementOptions = { layout: "tabs" };

    const handleSubmit = async (e) => {

    }

    return (
        <form onSubmit={handleSubmit} className="max-w-lg mx-auto p-4">
            <h2 className="text-xl font-semibold mb-4">Payment Information</h2>
            {
                loading ? (<Skeleton />) :
                    (
                        <>
                            {clientSecret && <PaymentElement options={paymentElementOptions} />}
                            {errorMessage && (<div className="text-red-500 mt-2">{errorMessage}</div>)}

                            <button disabled={!stripe || loading}>
                                {!loading ? `Pay $${priceCalculation(totalPrice, 1)}` : "Processing"}
                            </button>
                        </>
                    )
            }
        </form>
    )
}

export default PaymentForm
