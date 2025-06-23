import React, { FormEvent, useState } from 'react';
import useOnboarding from '../hooks/useOnboarding';
import { OnboardStartRequest } from '../types';

export default function OnboardingWizard() {
  const { step, data, setData, next, prev, start, verify } = useOnboarding();
  const [code, setCode] = useState('');
  const [completed, setCompleted] = useState(false);

  const handleStart = async (e: FormEvent) => {
    e.preventDefault();
    await start(data as OnboardStartRequest);
    next();
  };

  const handleVerify = async (e: FormEvent) => {
    e.preventDefault();
    const res = await verify(code);
    if (res.success) {
      setCompleted(true);
    }
  };

  if (completed) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-center">Setup completato</div>
      </div>
    );
  }

  return (
    <div className="flex items-center justify-center min-h-screen">
      <div className="bg-white p-6 rounded shadow-md w-full max-w-md">
        <div className="mb-4 text-sm text-gray-500">Passo {step} di 2</div>
        {step === 1 && (
          <form onSubmit={handleStart} className="space-y-4">
            <label className="block text-sm font-medium">
              Nome azienda
              <input
                className="mt-1 w-full border rounded p-2"
                value={data.businessName || ''}
                onChange={(e) => setData({ ...data, businessName: e.target.value })}
                required
              />
            </label>
            <label className="block text-sm font-medium">
              Numero di telefono
              <input
                className="mt-1 w-full border rounded p-2"
                value={data.phoneNumber || ''}
                onChange={(e) => setData({ ...data, phoneNumber: e.target.value })}
                required
              />
            </label>
            <div className="flex justify-end">
              <button type="submit" className="px-4 py-2 bg-blue-600 text-white rounded">
                Avanti
              </button>
            </div>
          </form>
        )}
        {step === 2 && (
          <form onSubmit={handleVerify} className="space-y-4">
            <label className="block text-sm font-medium">
              Codice SMS
              <input
                className="mt-1 w-full border rounded p-2"
                value={code}
                onChange={(e) => setCode(e.target.value)}
                required
              />
            </label>
            <div className="flex justify-between">
              <button type="button" onClick={prev} className="px-4 py-2 border rounded">
                Indietro
              </button>
              <button type="submit" className="px-4 py-2 bg-blue-600 text-white rounded">
                Avanti
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
}
