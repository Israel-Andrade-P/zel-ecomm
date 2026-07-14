import { Button, Skeleton, Step, StepLabel, Stepper } from "@mui/material";
import { useEffect, useState } from "react";
import AddressInfo from "./AddressInfo";
import { useDispatch, useSelector } from "react-redux";
import { fetchUserAddresses } from "../../store/actions";
import toast from "react-hot-toast";
import ErrorPage from "../shared/ErrorPage";

const Checkout = () => {
    const [activeStep, setActiveStep] = useState(0);
    const { address, selectedAddress } = useSelector((state) => state.auth);
    const { isLoading, errorMessage } = useSelector((state) => state.errors);
    const dispatch = useDispatch();
    const steps = ["Address", "Payment Method", "Order Summary", "Payment"];
    const paymentMethod = false;

    const handleBack = () => {
        setActiveStep((prevStep) => prevStep - 1);
    }

    const handleNext = () => {
        if (activeStep === 0 && !selectedAddress) {
            toast.error("Please select an address")
            return;
        }
        if (activeStep === 1 && (!selectedAddress || !paymentMethod)) {
            toast.error("Please select a payment method")
            return;
        }
        setActiveStep((prevStep) => prevStep + 1);
    }

    useEffect(() => {
        dispatch(fetchUserAddresses());
    }, [dispatch]);

    return (
        <div className="py-14 min-h-[calc(100vh-100px)]">
            <Stepper activeStep={activeStep} alternativeLabel>
                {
                    steps.map((label, idx) => (
                        <Step key={idx}>
                            <StepLabel>{label}</StepLabel>
                        </Step>
                    ))
                }
            </Stepper>

            {
                isLoading ? (
                    <div className="lg:w-[80%] mx-auto py-5 ">
                        <Skeleton />
                    </div>
                ) : (
                    <div className="mt-5">
                        {activeStep === 0 && <AddressInfo addresses={address} />}
                    </div>
                )
            }

            <div className="flex justify-between items-center px-4 fixed z-50 h-24 bottom-0 bg-white left-0 w-full py-4 border-slate-200"
                style={{ boxShadow: "0 -2px 4px rgba(100, 100, 100, 0.15)" }}>
                <Button variant="outlined" disabled={activeStep === 0} onClick={handleBack}>
                    Back
                </Button>

                {
                    activeStep !== steps.length - 1 && (
                        <button disabled={
                            errorMessage || ((activeStep === 0 ? !selectedAddress
                                : activeStep === 1 ? !paymentMethod : false
                            ))
                        } className={`bg-custom-blue font-semibold px-6 h-10 rounded-md text-white 
                          ${errorMessage || (activeStep === 0 && !selectedAddress) || (activeStep === 1 && !paymentMethod) ? "opacity-60" : ""}`} onClick={handleNext}>
                            Proceed
                        </button>
                    )

                }
            </div>

            {errorMessage && <ErrorPage message={errorMessage} />}
        </div>
    )
}

export default Checkout;
