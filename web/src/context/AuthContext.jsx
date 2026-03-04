import { createContext, useContext, useState, useEffect } from 'react';
import {
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  signInWithPopup,
  GoogleAuthProvider,
  GithubAuthProvider,
  signOut as firebaseSignOut,
  onAuthStateChanged,
} from 'firebase/auth';
import { auth } from '../firebase/firebase';

const AuthContext = createContext(null);

const TOKEN_KEY = 'ems_auth_token';

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const getStoredToken = () => {
    return sessionStorage.getItem(TOKEN_KEY);
  };

  const setStoredToken = (token) => {
    if (token) {
      sessionStorage.setItem(TOKEN_KEY, token);
    } else {
      sessionStorage.removeItem(TOKEN_KEY);
    }
  };

  const refreshToken = async (firebaseUser) => {
    if (!firebaseUser) return null;
    try {
      const token = await firebaseUser.getIdToken(true);
      setStoredToken(token);
      return token;
    } catch (err) {
      console.error('Token refresh failed:', err);
      setStoredToken(null);
      return null;
    }
  };

  const register = async (email, password) => {
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);
    const token = await userCredential.user.getIdToken();
    setStoredToken(token);
    setUser(userCredential.user);
    return userCredential.user;
  };

  const login = async (email, password) => {
    const userCredential = await signInWithEmailAndPassword(auth, email, password);
    const token = await userCredential.user.getIdToken();
    setStoredToken(token);
    setUser(userCredential.user);
    return userCredential.user;
  };

  const loginWithGoogle = async () => {
    const provider = new GoogleAuthProvider();
    const userCredential = await signInWithPopup(auth, provider);
    const token = await userCredential.user.getIdToken();
    setStoredToken(token);
    setUser(userCredential.user);
    return userCredential.user;
  };

  const loginWithGitHub = async () => {
    const provider = new GithubAuthProvider();
    const userCredential = await signInWithPopup(auth, provider);
    const token = await userCredential.user.getIdToken();
    setStoredToken(token);
    setUser(userCredential.user);
    return userCredential.user;
  };

  const signOut = async () => {
    await firebaseSignOut(auth);
    setStoredToken(null);
    setUser(null);
  };

  const getToken = async () => {
    const currentUser = auth.currentUser;
    if (!currentUser) return getStoredToken();
    const token = await currentUser.getIdToken();
    setStoredToken(token);
    return token;
  };

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, async (firebaseUser) => {
      setUser(firebaseUser);
      if (firebaseUser) {
        await refreshToken(firebaseUser);
      } else {
        setStoredToken(null);
      }
      setLoading(false);
    });

    return () => unsubscribe();
  }, []);

  const value = {
    user,
    loading,
    register,
    login,
    loginWithGoogle,
    loginWithGitHub,
    signOut,
    getToken,
    getStoredToken,
    isAuthenticated: !!user,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}
