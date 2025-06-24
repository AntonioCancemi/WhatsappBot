import axios from 'axios';
import {
  OnboardStartRequest,
  OnboardStartResponse,
  OnboardVerifyRequest,
  OnboardVerifyResponse,
  BroadcastMessage,
  BroadcastCreateRequest,
} from './types';

const api = axios.create({
  baseURL: 'http://localhost:8080/',
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

export async function getBroadcasts() {
  const { data } = await api.get<BroadcastMessage[]>('/api/broadcasts');
  return data;
}

export async function createBroadcast(body: BroadcastCreateRequest) {
  const { data } = await api.post<BroadcastMessage>('/api/broadcasts', body);
  return data;
}

export default api;
