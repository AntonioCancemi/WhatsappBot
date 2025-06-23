import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/',
  headers: {
    'Content-Type': 'application/json',
  },
});

export function onboardStart(body: unknown) {
  return api.post<{ tenantId: string; token: string }>('/onboard/start', body);
}

export function onboardVerify(body: unknown) {
  return api.post('/onboard/verify', body);
}

export default api;
