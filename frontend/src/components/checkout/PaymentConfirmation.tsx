import { Skeleton } from "@mui/material";
import { useEffect } from "react";
import { FaCheckCircle } from "react-icons/fa";
import { useDispatch, useSelector } from "react-redux";
import { useLocation } from "react-router-dom"
import { stripePaymentConfirmation } from "../../store/actions";
import toast from "react-hot-toast";

const PaymentConfirmation = () => {
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const { isLoading, errorMessage, paymentConfirmed } = useSelector((state) => state.payment);
    const dispatch = useDispatch();

    const paymentIntent = searchParams.get("payment_intent");

    useEffect(() => {
        if (!paymentIntent) return;

        dispatch(stripePaymentConfirmation(paymentIntent, toast));
    }, [paymentIntent, dispatch])

    if (isLoading) {
        return (
            <div className="max-w-xl mx-auto">
                <Skeleton />
            </div>
        );
    }

    if (errorMessage) {
        return (
            <div className="text-center text-red-500">
                {errorMessage}
            </div>
        );
    }

    if (!paymentConfirmed) {
        return null;
    }

    if (!paymentIntent) {
        return (
            <div className="text-center">
                Invalid payment confirmation.
            </div>
        );
    }

    return (
        <div className="min-h-screen flex items-center justify-center">
            <div className="p-8 rounded-lg shadow-lg text-center max-w-md mx-auto border border-white">
                <div className="text-green-500 mb-4 flex justify-center">
                    <FaCheckCircle size={64} />
                </div>
                <h2 className="text-3xl font-bold text-gray-800 mb-2">Payment Successful</h2>
                <p className="text-gray-600 mb-6">
                    Thank you for your purchase! We are processing your order.
                </p>
            </div>
        </div>
    )
}

export default PaymentConfirmation
