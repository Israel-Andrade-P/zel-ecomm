import { useForm } from "react-hook-form"
import InputField from "../../shared/InputField";
import { Button } from "@mui/material";
import Spinners from "../../shared/Spinners";
import { useDispatch, useSelector } from "react-redux";
import TextArea from "../../shared/TextArea";
import { useEffect } from "react";
import { updateProduct } from "../../../store/actions";
import toast from "react-hot-toast";

const AddProductForm = ({ setOpen, product, isUpdate = false }) => {
    const { register, handleSubmit, reset, setValue, formState: { errors } } = useForm({ mode: "onTouched" });
    const { isLoading } = useSelector((state) => state.uiStates);
    const dispatch = useDispatch();

    const saveProductHandler = async (data) => {
        if (!isUpdate) {

        } else {
            const sendData = { ...data, id: product.id }

            const result = await dispatch(updateProduct(sendData));

            if (!result.success) {
                toast.error(String(result.errorMessage))
                return;
            }

            toast.success("Product updated");
            reset();
            setOpen(false);
        }
    }

    useEffect(() => {
        if (isUpdate && product) {
            setValue("productName", product.productName);
            setValue("price", product.price);
            setValue("quantity", product.quantity);
            setValue("discount", product.discount);
            setValue("specialPrice", product.specialPrice);
            setValue("description", product.description);
        }
    }, [isUpdate, product])

    return (
        <div className="py-5 relative h-full">
            <form className="space-y-4" onSubmit={handleSubmit(saveProductHandler)}>
                <div className="flex md:flex-row flex-col gap-4 w-full">
                    <InputField label="Product Name" required id="productName" type="text" message="This field is required*" register={register}
                        placeholder="Name" errors={errors} />
                </div>
                <div className="flex md:flex-row flex-col gap-4 w-full">
                    <InputField label="Price" required id="price" type="number" message="This field is required*" register={register}
                        placeholder="Price" errors={errors} />
                    <InputField label="Quantity" required id="quantity" type="number" message="This field is required*" register={register}
                        placeholder="Quantity" errors={errors} />
                </div>
                <div className="flex md:flex-row flex-col gap-4 w-full">
                    <InputField label="Discount" required id="discount" type="number" message="This field is required*" register={register}
                        placeholder="Discount" errors={errors} />
                    <InputField label="Special Price" required id="specialPrice" type="number" message="This field is required*" register={register}
                        placeholder="Special Price" errors={errors} />
                </div>

                <div className="flex flex-col gap-2 w-full">
                    <TextArea label="Description" id="description" errors={errors} register={register} required message="Description is required" placeholder="Add product description..." className="" />
                </div>
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

export default AddProductForm
