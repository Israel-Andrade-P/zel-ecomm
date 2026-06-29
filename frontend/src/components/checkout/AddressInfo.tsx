import { Skeleton } from "@mui/material";
import { useState } from "react";
import { FaAddressBook } from "react-icons/fa";
import AddressInfoModel from "./AddressInfoModel";
import AddAddressForm from "./AddAddressForm";
import { useSelector } from "react-redux";
import AddressList from "./AddressList";

const AddressInfo = ({ addresses }) => {
    const noAddress = !addresses || addresses.length === 0;
    const { isLoading, btnLoader } = useSelector((state) => state.errors);
    const [openAddressModel, setOpenAddressModel] = useState(false);
    const [selectedAddress, setSelectedAddress] = useState("");

    const addNewAddressHandler = () => {
        setSelectedAddress("");
        setOpenAddressModel(true);
    };

    return (
        <div className="pt-4">
            {
                noAddress ? (
                    <div className="p-6 rounded-lg max-w-md mx-auto flex flex-col items-center justify-center">
                        <FaAddressBook size={50} className="text-gray-500 mb-4" />
                        <h1 className="mb-2 text-slate-900 text-center font-semibold text-2xl">
                            No addresses found
                        </h1>
                        <p className="mb-6 text-slate-800 text-center">
                            Please add an address to complete purchase
                        </p>
                        <button onClick={addNewAddressHandler} className="px-4 py-2 bg-blue-600 text-white font-medium rounded hover:bg-blue-700 transition-all">
                            Add
                        </button>
                    </div>
                ) : (
                    <div className="relative p-6 rounded-lg max-w-md mx-auto">
                        <h1 className="text-slate-800 text-center font-bold text-2xl">
                            Select Address
                        </h1>

                        {
                            isLoading ? (
                                <div className="py-4 px-8">
                                    <Skeleton />
                                </div>
                            ) : (<>
                                <div className="space-y-4 pt-6">
                                    <AddressList
                                        addresses={addresses}
                                        setSelectedAddress={setSelectedAddress}
                                        setOpenAddressModel={setOpenAddressModel} />
                                </div>

                                {addresses.length > 0 && (
                                    <div className="mt-4">
                                        <button onClick={addNewAddressHandler} className="px-4 py-2 bg-blue-600 text-white font-medium rounded hover:bg-blue-700 transition-all">
                                            Add New Address
                                        </button>
                                    </div>
                                )}
                            </>)
                        }
                    </div>
                )
            }

            <AddressInfoModel open={openAddressModel} setOpen={setOpenAddressModel}>
                <AddAddressForm address={selectedAddress} setOpen={setOpenAddressModel} />
            </AddressInfoModel>
        </div>
    )
}

export default AddressInfo
