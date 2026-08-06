import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import { useDispatch, useSelector } from "react-redux"
import PaymentForm from "./PaymentForm";
import { useEffect } from "react";
import { getStripeClientSecret } from "../../store/actions";
import { Skeleton } from "@mui/material";

const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);

const StripePayment = () => {
    const dispatch = useDispatch();
    const { clientSecret } = useSelector((state) => state.payment);
    const { currentOrderId, orderTotalPrice } = useSelector((state) => state.order);
    const { isLoading, errorMessage } = useSelector((state) => state.errors);

    console.log("Order Id: ", currentOrderId);
    console.log("Total Price: ", orderTotalPrice);


    useEffect(() => {
        if (currentOrderId && !clientSecret) {
            dispatch(getStripeClientSecret(currentOrderId));
        }
    }, [currentOrderId, clientSecret, dispatch]);
    console.log("Stripe Secret:", clientSecret);

    if (isLoading) {
        return (
            <div className="max-w-lg mx-auto">
                <Skeleton />
            </div>
        )
    }

    return (
        <>
            {
                clientSecret && (
                    <Elements stripe={stripePromise} options={{ clientSecret }}>
                        <PaymentForm clientSecret={clientSecret} totalPrice={orderTotalPrice} />
                    </Elements>
                )
            }
        </>
    )
}

export default StripePayment
