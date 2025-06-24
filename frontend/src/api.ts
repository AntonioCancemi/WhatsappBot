import axios from 'axios';
import {
  OnboardStartRequest,
  OnboardStartResponse,
  OnboardVerifyRequest,
  OnboardVerifyResponse,
  LoginRequest,
  LoginResponse,
} from './types';

const api = axios.create({
  baseURL: 'http://localhost:8080/',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers = config.headers || {};
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export async function onboardStart(body: OnboardStartRequest): Promise<OnboardStartResponse> {
  const { data } = await api.post<OnboardStartResponse>('/onboard/start', body);
  return data;
}

export async function onboardVerify(body: OnboardVerifyRequest): Promise<OnboardVerifyResponse> {
  const { data } = await api.post<OnboardVerifyResponse>('/onboard/verify', body);
  return data;
}

export async function login(body: LoginRequest): Promise<LoginResponse> {
  const { data } = await api.post<LoginResponse>('/api/auth/login', body);
  return data;
}

export default api;
