import { Skeleton } from "@mui/material";
import { PaymentElement, useElements, useStripe } from "@stripe/react-stripe-js";
import { useState } from "react";
import { priceCalculation } from "../../utils/formatPrice";

const PaymentForm = ({ clientSecret, totalPrice }) => {
    const stripe = useStripe();
    const elements = useElements();
    const [errorMessage, setErrorMessage] = useState("");
    const paymentElementOptions = { layout: "tabs" };
    const isLoading = !clientSecret || !stripe || !elements;

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!stripe || !elements) {
            return;
        }

        const { error: submitError } = await elements.submit();

        const { error } = await stripe.confirmPayment({
            elements,
            clientSecret,
            confirmParams: { return_url: `${import.meta.env.VITE_FRONT_END_URL}/order-confirm` }
        });

        if (error) {
            setErrorMessage(error.message);
            return false;
        }

    }

    return (
        <form onSubmit={handleSubmit} className="max-w-lg mx-auto p-4">
            <h2 className="text-xl font-semibold mb-4">Payment Information</h2>
            {
                isLoading ? (<Skeleton />) :
                    (
                        <>
                            {clientSecret && <PaymentElement options={paymentElementOptions} />}
                            {errorMessage && (<div className="text-red-500 mt-2">{errorMessage}</div>)}

                            <button disabled={!stripe || isLoading} className="text-white w-full px-5 py-2.5 bg-black mt-2 rounded-md font-bold disabled:opacity-50
                                                                             disabled:animate-pulse">
                                {!isLoading ? `Pay $${priceCalculation(totalPrice, 1)}` : "Processing"}
                            </button>
                        </>
                    )
            }
        </form>
    )
}

export default PaymentForm
