import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export function Layout({ children }) {
  const { user, signOut } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await signOut();
    navigate('/login');
  };

  return (
    <div className="layout">
      <header className="header">
        <nav className="nav">
          <Link to="/dashboard" className="nav-brand">
            EMS
          </Link>
          <div className="nav-links">
            <Link to="/dashboard">Dashboard</Link>
            <Link to="/employees">Employees</Link>
            <Link to="/departments">Departments</Link>
          </div>
          <div className="nav-user">
            <span className="user-email">{user?.email}</span>
            <button type="button" onClick={handleLogout} className="btn btn-outline btn-sm">
              Logout
            </button>
          </div>
        </nav>
      </header>
      <main className="main">{children}</main>
    </div>
  );
}
