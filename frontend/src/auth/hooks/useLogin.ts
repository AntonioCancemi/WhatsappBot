import { useState } from 'react';
import { login as loginApi } from '../api/auth';
import { LoginRequest } from '../../types';
import useAuth from './useAuth';

export default function useLogin() {
  const auth = useAuth();
  const [error, setError] = useState('');

  const login = async (req: LoginRequest, tenantName: string) => {
    try {
      const res = await loginApi(req, tenantName);
      auth.login(res.token);
    } catch {
      setError('Credenziali non valide');
    }
  };

  return { login, error };
}
