import { useState } from 'react';
import { register as registerApi } from '../api/auth';
import { RegisterRequest } from '../../types';
import useAuth from './useAuth';

export default function useRegister() {
  const auth = useAuth();
  const [error, setError] = useState('');

  const register = async (req: RegisterRequest) => {
    try {
      const res = await registerApi(req);
      auth.login(res.token);
    } catch {
      setError('Registrazione non riuscita');
    }
  };

  return { register, error };
}
