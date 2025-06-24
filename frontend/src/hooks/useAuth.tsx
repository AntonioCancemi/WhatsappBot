import React, { createContext, useContext, useState } from 'react';

interface AuthContextValue {
  token: string;
  login(token: string): void;
  logout(): void;
}

const AuthContext = createContext<AuthContextValue>(null!);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState(() => localStorage.getItem('token') || '');

  const login = (t: string) => {
    setToken(t);
    localStorage.setItem('token', t);
  };

  const logout = () => {
    setToken('');
    localStorage.removeItem('token');
  };

  return (
    <AuthContext.Provider value={{ token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export default function useAuth() {
  return useContext(AuthContext);
}
