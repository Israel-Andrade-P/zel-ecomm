import { Button } from "@mui/material";
import { useRef, useState } from "react"
import { FaCloudUploadAlt } from "react-icons/fa"
import { useSelector } from "react-redux";
import Spinners from "../../shared/Spinners";

const ImageUploadForm = ({setOpen, product}) => {
  const { isLoading, errorMessage } = useSelector((state) => state.uiStates);
  const fileInputRef = useRef();
  const [preview, setPreview] = useState(null);

  const onImageChange = (e) => {

  }
  const addNewImageHandler = () => {

  }

  const handleClearImage = () => {

  }

  return (
    <div className="py-5 relative h-full">
      <form className="space-y-4" onSubmit={addNewImageHandler}>
        <div className="flex flex-col gap-4 w-full">
          <label className="flex items-center gap-2 cursor-pointer text-custom-blue border border-dashed border-custom-blue rounded-md p-3 w-full justify-center"> 
            <FaCloudUploadAlt size={24}/>
            <span>Upload Image</span>
            <input type="file" ref={fileInputRef} onChange={onImageChange} className="hidden" accept=".jpeg .jpg .png"/>
          </label>

          {
            preview && (
            <div>
                <img src={preview} alt="Image Preview" className="h-60 rounded-md mb-2"/>
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
