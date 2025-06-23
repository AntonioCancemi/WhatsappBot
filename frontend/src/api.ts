import axios from 'axios';
import {
  OnboardStartRequest,
  OnboardStartResponse,
  OnboardVerifyRequest,
  OnboardVerifyResponse,
} from './types';

const api = axios.create({
  baseURL: '/',
  headers: {
    'Content-Type': 'application/json',
  },
});

export async function onboardStart(body: OnboardStartRequest): Promise<OnboardStartResponse> {
  const { data } = await api.post<OnboardStartResponse>('/onboard/start', body);
  return data;
}

export async function onboardVerify(body: OnboardVerifyRequest): Promise<OnboardVerifyResponse> {
  const { data } = await api.post<OnboardVerifyResponse>('/onboard/verify', body);
  return data;
}

export default api;
