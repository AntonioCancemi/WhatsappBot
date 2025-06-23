import React, { FormEvent, useState } from 'react';
import useOnboarding from '../hooks/useOnboarding';
import { OnboardStartRequest } from '../types';

interface Props {
  open: boolean;
  onClose(): void;
}

export default function Wizard({ open, onClose }: Props) {
  const { step, data, setData, next, prev, start, verify, token } = useOnboarding();
  const [code, setCode] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [completed, setCompleted] = useState(false);

  if (!open) return null;

  const handleStart = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      await start(data as OnboardStartRequest);
      next();
    } catch (err: any) {
      if (err.code === 'ECONNABORTED') setError('Timeout');
      else if (err.response?.status === 401) setError('Non autorizzato');
      else setError('Errore');
    } finally {
      setLoading(false);
    }
  };

  const handleVerify = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      await verify(code);
      localStorage.setItem('authToken', token);
      setCompleted(true);
    } catch (err: any) {
      if (err.code === 'ECONNABORTED') setError('Timeout');
      else if (err.response?.status === 401) setError('Non autorizzato');
      else setError('Errore');
    } finally {
      setLoading(false);
    }
  };

  if (completed) {
    return (
      <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
        <div className="bg-white rounded-lg p-6 w-full max-w-md text-center">Completato!</div>
      </div>
    );
  }

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white rounded-lg p-6 w-full max-w-md">
        <div className="mb-4 text-sm text-gray-500">Passo {step} di 2</div>
        {error && <div className="text-red-500 mb-2">{error}</div>}
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
              <button
                type="submit"
                className="px-4 py-2 bg-blue-600 text-white rounded"
                disabled={loading}
              >
                {loading ? 'Attendere...' : 'Avanti'}
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
              <button
                type="submit"
                className="px-4 py-2 bg-blue-600 text-white rounded"
                disabled={loading}
              >
                {loading ? 'Verifica...' : 'Conferma'}
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
}
