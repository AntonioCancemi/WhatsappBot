import { useState } from 'react';
import { onboardStart, onboardVerify } from '../api';

export interface WizardData {
  company: string;
  phone: string;
  code?: string;
  tenantId?: string;
  token?: string;
  accepted?: boolean;
}

export default function useWizard() {
  const [step, setStep] = useState(1);
  const [data, setData] = useState<WizardData>({
    company: '',
    phone: '',
  });

  const next = () => setStep((s) => (s < 4 ? s + 1 : s));
  const prev = () => setStep((s) => (s > 1 ? s - 1 : s));

  const submitStart = async () => {
    const res = await onboardStart({ company: data.company, phone: data.phone });
    setData((d) => ({ ...d, tenantId: res.data.tenantId, token: res.data.token }));
  };

  const submitVerify = async () => {
    await onboardVerify({ tenantId: data.tenantId, token: data.token, code: data.code });
  };

  return { step, data, setData, next, prev, submitStart, submitVerify };
}
