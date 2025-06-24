import api from '../../api';
import { LoginRequest, LoginResponse, RegisterRequest, Role } from '../../types';

export async function login(body: LoginRequest): Promise<LoginResponse> {
  const { data } = await api.post<LoginResponse>('/api/auth/login', body);
  return data;
}

export async function register(body: RegisterRequest): Promise<LoginResponse> {
  const { data } = await api.post<LoginResponse>('/api/auth/register', body);
  return data;
}

export async function me() {
  const { data } = await api.get('/api/auth/me');
  return data;
}

export async function getRoles() {
  const { data } = await api.get<Role[]>('/api/auth/roles');
  return data;
}
