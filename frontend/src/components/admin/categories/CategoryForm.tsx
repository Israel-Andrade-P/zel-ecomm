import { useForm } from "react-hook-form"
import InputField from "../../shared/InputField";
import { Button } from "@mui/material";
import Spinners from "../../shared/Spinners";
import { useDispatch, useSelector } from "react-redux";
import { addNewCategory, updateCategory } from "../../../store/actions";
import toast from "react-hot-toast";
import ErrorPage from "../../shared/ErrorPage";
import { useEffect } from "react";

const CategoryForm = ({ setOpen, category, isUpdate = false }) => {
  const { register, handleSubmit, reset, setValue, formState: { errors } } = useForm({ mode: "onTouched" });
  const { isLoading, errorMessage } = useSelector((state) => state.uiStates);
  const dispatch = useDispatch();
  console.log("Selected Category:", category);


  const saveCategoryHandler = async (data) => {
    if (!isUpdate) {
      const result = await dispatch(addNewCategory(data));

      if (!result.success) {
        toast.error(String(result.errorMessage))
        return;
      }

      toast.success("Category added");
      reset();
      setOpen(false);
    } else {
      console.log("Update data:", data);
      

      const result = await dispatch(updateCategory(data, category.name));

      if (!result.success) {
        toast.error(String(result.errorMessage))
        return;
      }

      toast.success("Category updated");
      reset();
      setOpen(false);
    }
  }

  useEffect(() => {
    if (isUpdate && category) {
      setValue("name", category.name);
    }
  }, [isUpdate, category])



  if (errorMessage) return <ErrorPage message={errorMessage} />

  return (
    <div className="py-5 relative h-full">
      <form className="space-y-4" onSubmit={handleSubmit(saveCategoryHandler)}>
        <div className="flex md:flex-row flex-col gap-4 w-full">
          <InputField label="Category Name" required id="name" type="text" message="This field is required*" register={register}
            placeholder="Name" errors={errors} />
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
              </div>) : (isUpdate ? "Update" : "Add")
            }
          </Button>
        </div>
      </form>

    </div>
  )
}

export default CategoryForm;
