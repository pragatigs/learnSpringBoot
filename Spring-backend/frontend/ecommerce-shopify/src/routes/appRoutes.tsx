import { Route, Routes, Navigate} from "react-router-dom";
import Login from "../pages/authentication/login";
import { Register } from "../pages/authentication/register";
import { UserProfile } from "../pages/profile/userProfile";

export function AppRoutes() {
  return (
      <Routes>
            <Route path="/" element={<Navigate to="/login" replace />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/userProfile" element={<UserProfile />}/>
        </Routes>
  );
}