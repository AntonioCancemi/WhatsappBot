import { useState } from 'react';
import axios from 'axios';
import { OnboardStartRequest, OnboardStartResponse, OnboardVerifyResponse } from '../types';

export default function useOnboarding() {
  const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL ?? 'http://localhost:8080',
    headers: { 'Content-Type': 'application/json' },
    timeout: 5000,
  });
  const [step, setStep] = useState(1);
  const [data, setData] = useState<Partial<OnboardStartRequest>>({});
  const [tenantId, setTenantId] = useState('');
  const [token, setToken] = useState('');

  const next = () => setStep((s) => Math.min(s + 1, 2));
  const prev = () => setStep((s) => Math.max(s - 1, 1));

  const start = async (values: OnboardStartRequest): Promise<OnboardStartResponse> => {
    const { data } = await api.post<OnboardStartResponse>('/onboard/start', values);
    setTenantId(data.tenantId);
    setToken(data.token);
    return data;
  };

  const verify = async (code: string): Promise<OnboardVerifyResponse> => {
    const { data } = await api.post<OnboardVerifyResponse>('/onboard/verify', {
      tenantId,
      token,
      smsCode: code,
    });
    return data;
  };

  return { step, data, setData, tenantId, token, next, prev, start, verify };
}
