import { Step, StepLabel, Stepper } from "@mui/material";
import { useEffect, useState } from "react";
import AddressInfo from "./AddressInfo";
import { useDispatch, useSelector } from "react-redux";
import { fetchUserAddresses } from "../../store/actions";

const Checkout = () => {
    const [activeStep, setActiveStep] = useState(0);
    const { address } = useSelector((state) => state.auth);
    const dispatch = useDispatch();
    const steps = ["Address", "Payment Method", "Order Summary", "Payment"];

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

            <div className="mt-5">
                {activeStep === 0 && <AddressInfo addresses={address} />}
            </div>
        </div>
    )
}

export default Checkout;
