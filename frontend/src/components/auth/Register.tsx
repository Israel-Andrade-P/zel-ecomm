import { useState } from "react";
import { useForm } from "react-hook-form";
import { FaUserPlus } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";
import InputField from "../shared/InputField";
import { useDispatch } from "react-redux";
import { registerUser } from "../../store/actions";
import toast from "react-hot-toast";
import Spinners from "../shared/Spinners";

const Register = () => {
    const navigate = useNavigate();
    const [loader, setLoader] = useState(false);
    const dispatch = useDispatch();
    const { register, handleSubmit, reset, formState: { errors } } = useForm({ mode: "onTouched" });

    const registerHandler = async (data) => {
        console.log("Register Click");
        dispatch(registerUser(data, toast, reset, navigate, setLoader));
    };

    return (
        <div className="min-h-[calc(100vh-64px)] flex justify-center items-center">
            <form onSubmit={handleSubmit(registerHandler)} className="sm:w-112.5 w-90 shadow-custom py-8 sm:px-8 px-4 rounded-md">
                <div className="flex flex-col items-center justify-center space-y-4">
                    <FaUserPlus className="text-slate-800 text-5xl" />
                    <h1 className="text-slate-800 text-center font-montserrat lg:text-3xl text-2xl font-bold">
                        Register Here
                    </h1>
                </div>
                <hr className="mt-2 mb-5 text-black" />
                <div className="flex flex-col gap-3">
                    <InputField label="Username" required id="username" type="text" message="*Username is required"
                        placeholder="Enter Username" register={register} errors={errors} />
                    <InputField label="Email" required id="email" type="email" message="*Email is required"
                        placeholder="Enter Email" register={register} errors={errors} />
                    <InputField label="Password" required id="password" type="password" min={6} message="*Password is required"
                        placeholder="Enter Password" register={register} errors={errors} />
                    <InputField label="Telephone" required id="telephone" type="tel" message="*Telephone is required"
                        placeholder="Enter Telephone" register={register} errors={errors} />
                    <InputField label="Date of Birth" required id="dob" type="date" message="*Date of Birth is required"
                        placeholder="Enter Date of Birth" register={register} errors={errors} />
                    <InputField label="Country" required id="location.country" type="text" message="*Country is required"
                        placeholder="Enter Country" register={register} errors={errors} />
                    <InputField label="City" required id="location.city" type="text" message="*City is required"
                        placeholder="Enter City" register={register} errors={errors} />
                    <InputField label="Steet" required id="location.street" type="text" message="*Street is required"
                        placeholder="Enter Street" register={register} errors={errors} />
                    <InputField label="Zip Code" required id="location.zipCode" type="text" message="*Zip Code is required"
                        placeholder="Enter Zip Code" register={register} errors={errors} />
                </div>
                <button disabled={loader} className="bg-button-gradient flex gap-2 items-center justify-center font-semibold text-white w-full py-2 hover:text-slate-400 transition-colors duration-100 rounded-sm my-3" type="submit">
                    {loader ? (<><Spinners /> Loading...</>) : (<>Register</>)}
                </button>
                <p className="text-center text-sm text-slate-700 mt-6">
                    Already have an account?
                    <Link className="font-semibold underline hover:text-black" to="/login">
                        <span> Click here</span>
                    </Link>
                </p>
            </form>
        </div>
    )
}

export default Register
