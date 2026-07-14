import { FaCheckCircle, FaEdit, FaStreetView, FaTrash } from "react-icons/fa";
import { MdLocationCity, MdPinDrop, MdPublic } from "react-icons/md";
import { useDispatch, useSelector } from "react-redux"
import { selectUserAddress } from "../../store/actions";

const AddressList = ({ addresses, setSelectedAddress, setOpenAddressModel, setOpenDeleteModel }) => {
    const { selectedAddress } = useSelector((state) => state.auth);
    const dispatch = useDispatch();

    const handleAddressSelection = (address) => {
        dispatch(selectUserAddress(address));
    };

    const onEditButtonHandler = (address) => {
        setSelectedAddress(address);
        setOpenAddressModel(true);
    };

    const onDeleteButtonHandler = (address) => {
        setSelectedAddress(address);
        setOpenDeleteModel(true);
    };

    return (
        <div className="space-y-4 ">
            {
                addresses.map((address) => (
                    <div key={address.publicId} onClick={() => handleAddressSelection(address)}
                        className={`p-4 border rounded-md cursor-pointer relative ${selectedAddress?.publicId === address.publicId ? "bg-green-100" : "bg-white"}`}>
                        <div className="flex items-start">
                            <div className="space-y-1">
                                <div className="flex items-center">
                                    <FaStreetView size={17} className="mr-2 text-gray-600" />
                                    <p className="font-semibold">
                                        {address.street}, {address.zipCode}
                                    </p>
                                    {
                                        selectedAddress?.publicId === address.publicId && (<FaCheckCircle className="text-green-500 ml-2" />)
                                    }
                                </div>
                                <div className="flex items-center">
                                    <MdLocationCity size={14} className="mr-2 text-gray-600" />
                                    <p>{address.city}</p>
                                </div>
                                <div className="flex items-center">
                                    <MdPublic size={14} className="mr-2 text-gray-600" />
                                    <p>{address.country}</p>
                                </div>
                            </div>
                        </div>

                        <div className="flex gap-3 absolute top-4 right-2">
                            <button onClick={() => onEditButtonHandler(address)}>
                                <FaEdit size={18} className="text-teal-700" />
                            </button>
                            <button onClick={() => onDeleteButtonHandler(address)}>
                                <FaTrash size={17} className="text-rose-600" />
                            </button>
                        </div>
                    </div>
                ))
            }
        </div>
    )
}

export default AddressList
