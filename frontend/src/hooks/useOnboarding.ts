import { useState } from 'react';
import { onboardStart, onboardVerify } from '../api';
import { OnboardStartRequest, OnboardStartResponse, OnboardVerifyResponse } from '../types';

export default function useOnboarding() {
  const [step, setStep] = useState(1);
  const [data, setData] = useState<Partial<OnboardStartRequest>>({});
  const [tenantId, setTenantId] = useState('');
  const [token, setToken] = useState('');

  const next = () => setStep((s) => Math.min(s + 1, 2));
  const prev = () => setStep((s) => Math.max(s - 1, 1));

  const start = async (values: OnboardStartRequest): Promise<OnboardStartResponse> => {
    const res = await onboardStart(values);
    setTenantId(res.tenantId);
    setToken(res.token);
    return res;
  };

  const verify = async (code: string): Promise<OnboardVerifyResponse> => {
    return onboardVerify({ tenantId, token, code });
  };

  return { step, data, setData, tenantId, token, next, prev, start, verify };
}
