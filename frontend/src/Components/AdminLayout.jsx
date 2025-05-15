import { Outlet } from "react-router";

function AdminLayout() {
    return (
        <div>
            <h2>This is the admin</h2>
            <Outlet></Outlet>
        </div>


    );
}

export default AdminLayout;