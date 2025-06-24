import api from '../../api';
import { LoginRequest, LoginResponse } from '../../types';

export async function login(body: LoginRequest): Promise<LoginResponse> {
  const { data } = await api.post<LoginResponse>('/api/auth/login', body);
  return data;
}

export async function me() {
  const { data } = await api.get('/api/auth/me');
  return data;
}
