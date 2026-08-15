import Sidebar from "../shared/Sidebar"

const AdminLayout = () => {
    return (
        <div>
            <div className="fixed inset-y-0 z-50 flex w-72 flex-col">
                <Sidebar />
            </div>
        </div>
    )
}

export default AdminLayout
