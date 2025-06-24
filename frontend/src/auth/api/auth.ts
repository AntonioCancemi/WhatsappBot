import api from '../../api';
import { LoginRequest, LoginResponse, RegisterRequest, RegisterResponse, Role } from '../../types';

export async function login(body: LoginRequest, tenantName: string): Promise<LoginResponse> {
  const { data } = await api.post<LoginResponse>('/api/auth/login', body, {
    headers: { 'X-Tenant-ID': tenantName },
  });
  return data;
}

export async function register(body: RegisterRequest): Promise<RegisterResponse> {
  const { data } = await api.post<RegisterResponse>('/api/auth/register', body);
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
