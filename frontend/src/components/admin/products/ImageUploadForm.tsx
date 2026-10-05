import { Button } from "@mui/material";
import { useRef, useState } from "react"
import { FaCloudUploadAlt } from "react-icons/fa"
import { useDispatch, useSelector } from "react-redux";
import Spinners from "../../shared/Spinners";
import toast from "react-hot-toast";
import { uploadProductImage } from "../../../store/actions";

const ImageUploadForm = ({ setOpen, product }) => {
  const { isLoading, errorMessage } = useSelector((state) => state.uiStates);
  const dispatch = useDispatch();
  const fileInputRef = useRef();
  const [preview, setPreview] = useState(null);
  const [selectedFile, setSelectedFile] = useState(null);

  const onImageChange = (e) => {
    const file = e.target.files[0];
    if (!file || !["image/jpeg", "image/jpg", "image/png"].includes(file.type)) {
      toast.error("Please select a valid image file (.jpeg, .jpg, .png)")
      setPreview(null);
      setSelectedFile(null);
      return;
    }

    const reader = new FileReader();
    reader.onloadend = () => {
      setPreview(reader.result);
    };
    reader.readAsDataURL(file);
    setSelectedFile(file);
  }

  //TEST IMAGE UPLOAD FEATURE
  const addNewImageHandler = async (event) => {
    event.preventDefault();
    if(!selectedFile) {
      toast.error("Please select an image before updating")
      return;
    }
    const formData = new FormData();
    formData.append("image", selectedFile);
    const result = await dispatch(uploadProductImage(product.id, formData));

    if (!result.success) {
        toast.error(String(result.errorMessage))
        return;
      }

    setOpen(false);
    toast.success("Image uploaded");
  }

  const handleClearImage = () => {
    setPreview(null);
    setSelectedFile(null);
    fileInputRef.current.value = null;
  }

  return (
    <div className="py-5 relative h-full">
      <form className="space-y-4" onSubmit={addNewImageHandler}>
        <div className="flex flex-col gap-4 w-full">
          <label className="flex items-center gap-2 cursor-pointer text-custom-blue border border-dashed border-custom-blue rounded-md p-3 w-full justify-center">
            <FaCloudUploadAlt size={24} />
            <span>Upload Image</span>
            <input type="file" ref={fileInputRef} onChange={onImageChange} className="hidden" accept=".jpeg, .jpg, .png" />
          </label>

          {
            preview && (
              <div>
                <img src={preview} alt="Image Preview" className="h-60 rounded-md mb-2" />
                <button type="button" onClick={handleClearImage} className="bg-rose-600 text-white px-2 py-1 rounded-md">Clear</button>
              </div>
            )
          }
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
              </div>) : ("Upload")
            }
          </Button>
        </div>
      </form>
    </div>
  )
}

export default ImageUploadForm
