import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import CheckIn from "./pages/CheckIn.tsx";
import CheckOut from "./pages/CheckOut.tsx";
import ForgotPassword from "./pages/ForgotPassword.tsx";
import EmployeePage from './pages/EmployeePage';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/checkin" element={<CheckIn />} />
                <Route path="/forgot-password" element={<ForgotPassword />} />
                <Route path="/tasks" element={<EmployeePage />} />
                <Route path="/checkout" element={<CheckOut />} />
            </Routes>
        </Router>
    );
}

export default App;
