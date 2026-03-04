import { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Login() {
  const { isAuthenticated, loading: authLoading, login, loginWithGoogle, loginWithGitHub } =
    useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || '/dashboard';
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!authLoading && isAuthenticated) {
      navigate('/dashboard', { replace: true });
    }
  }, [isAuthenticated, authLoading, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await login(email, password);
      navigate(from, { replace: true });
    } catch (err) {
      setError(err.message || 'Failed to sign in');
    } finally {
      setLoading(false);
    }
  };

  const handleGoogleSignIn = async () => {
    setError('');
    setLoading(true);
    try {
      await loginWithGoogle();
      navigate(from, { replace: true });
    } catch (err) {
      setError(err.message || 'Failed to sign in with Google');
    } finally {
      setLoading(false);
    }
  };

  const handleGitHubSignIn = async () => {
    setError('');
    setLoading(true);
    try {
      await loginWithGitHub();
      navigate(from, { replace: true });
    } catch (err) {
      setError(err.message || 'Failed to sign in with GitHub');
    } finally {
      setLoading(false);
    }
  };

  if (authLoading) {
    return (
      <div className="auth-page">
        <div className="spinner" />
      </div>
    );
  }

  return (
    <div className="auth-page">
      <div className="auth-card">
        <h1>Sign In</h1>
        <p className="auth-subtitle">Employee Management System</p>
        {error && <div className="alert alert-error">{error}</div>}
        <form onSubmit={handleSubmit} className="auth-form">
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              autoComplete="email"
              placeholder="you@example.com"
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              autoComplete="current-password"
              placeholder="••••••••"
            />
          </div>
          <button type="submit" className="btn btn-primary btn-block" disabled={loading}>
            {loading ? 'Signing in...' : 'Sign In'}
          </button>
          <div className="auth-divider">
            <span>or</span>
          </div>
          <div className="auth-providers">
            <button
              type="button"
              onClick={handleGoogleSignIn}
              className="btn btn-provider btn-google"
              disabled={loading}
            >
              Sign in with Google
            </button>
            <button
              type="button"
              onClick={handleGitHubSignIn}
              className="btn btn-provider btn-github"
              disabled={loading}
            >
              Sign in with GitHub
            </button>
          </div>
        </form>
        <p className="auth-footer">
          Don&apos;t have an account? <Link to="/register">Register</Link>
        </p>
      </div>
    </div>
  );
}
