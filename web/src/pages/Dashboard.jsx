import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Dashboard() {
  const { user } = useAuth();

  return (
    <div className="page">
      <h1>Dashboard</h1>
      <p className="welcome-text">Welcome, {user?.email}</p>
      <div className="dashboard-cards">
        <Link to="/employees" className="card card-link">
          <h2>Employees</h2>
          <p>Manage employee records</p>
        </Link>
        <Link to="/departments" className="card card-link">
          <h2>Departments</h2>
          <p>Manage departments</p>
        </Link>
      </div>
    </div>
  );
}
