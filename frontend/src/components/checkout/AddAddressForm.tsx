import { useForm } from "react-hook-form";
import InputField from "../shared/InputField";
import { FaAddressCard } from "react-icons/fa";
import { useDispatch, useSelector } from "react-redux";
import Spinners from "../shared/Spinners";
import toast from "react-hot-toast";
import { addUpdateUserAddress } from "../../store/actions";

const AddAddressForm = ({ address, setOpen }) => {
    const { btnLoader } = useSelector((state) => state.errors);
    const dispatch = useDispatch();
    const { register, handleSubmit, reset, formState: { errors } } = useForm({ mode: "onTouched" });

    const onSaveAddressHandler = async (data) => {
        dispatch(addUpdateUserAddress(data, toast, address?.publicId, setOpen));
    };

    return (
        <div className="">
            <form onSubmit={handleSubmit(onSaveAddressHandler)} className="">
                <div className="flex justify-center items-center mb-4 font-semibold text-2xl text-slate-800 py-2 px-4">
                    <FaAddressCard className="mr-2 text-2xl" />
                    Register New Address
                </div>
                <div className="flex flex-col gap-4">
                    <InputField label="Country" required id="country" type="text" message="*Country is required"
                        placeholder="Enter Country" register={register} errors={errors} />
                    <InputField label="City" required id="city" type="text" message="*City is required"
                        placeholder="Enter City" register={register} errors={errors} />
                    <InputField label="Steet" required id="street" type="text" message="*Street is required"
                        placeholder="Enter Street" register={register} errors={errors} />
                    <InputField label="Zip Code" required id="zipCode" type="text" message="*Zip Code is required"
                        placeholder="Enter Zip Code" register={register} errors={errors} />
                </div>
                <button disabled={btnLoader} className="text-white bg-custom-blue px-4 py-2 rounded-md mt-4" type="submit">
                    {btnLoader ? (<><Spinners /> Loading...</>) : (<>Save</>)}
                </button>
            </form>
        </div>
    )
}

export default AddAddressForm;
