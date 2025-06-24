import React from 'react';
import useWizard from '../hooks/useWizard';

interface Props {
  open: boolean;
  onClose(): void;
}

export default function Wizard({ open, onClose }: Props) {
  const { step, data, setData, next, prev, submitStart, submitVerify } = useWizard();

  if (!open) return null;

  const handleNext = async () => {
    if (step === 1 && data.company) {
      next();
    } else if (step === 2 && data.phone) {
      await submitStart();
      next();
    } else if (step === 3) {
      next();
    } else if (step === 4 && data.accepted) {
      await submitVerify();
      onClose();
    }
  };

  const disabled =
    (step === 1 && !data.company) || (step === 2 && !data.phone) || (step === 4 && !data.accepted);

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white rounded-lg p-6 w-full max-w-md">
        <div className="mb-4 text-sm text-gray-500">Passo {step} di 4</div>
        {step === 1 && (
          <div className="space-y-4">
            <label className="block text-sm font-medium">
              Azienda
              <input
                className="mt-1 w-full border rounded-md p-2"
                value={data.company}
                onChange={(e) => setData({ ...data, company: e.target.value })}
                required
              />
            </label>
          </div>
        )}
        {step === 2 && (
          <div className="space-y-4">
            <label className="block text-sm font-medium">
              Numero WhatsApp
              <input
                className="mt-1 w-full border rounded-md p-2"
                value={data.phone}
                onChange={(e) => setData({ ...data, phone: e.target.value })}
                required
              />
            </label>
          </div>
        )}
        {step === 3 && (
          <div className="space-y-4">
            <label className="block text-sm font-medium">
              Codice SMS (opzionale)
              <input
                className="mt-1 w-full border rounded-md p-2"
                value={data.code || ''}
                onChange={(e) => setData({ ...data, code: e.target.value })}
              />
            </label>
          </div>
        )}
        {step === 4 && (
          <div className="space-y-4 text-sm">
            <p>Azienda: {data.company}</p>
            <p>Numero WhatsApp: {data.phone}</p>
            <label className="flex items-center space-x-2">
              <input
                type="checkbox"
                className="h-4 w-4"
                checked={!!data.accepted}
                onChange={(e) => setData({ ...data, accepted: e.target.checked })}
              />
              <span>Accetto i termini</span>
            </label>
          </div>
        )}
        <div className="mt-6 flex justify-between">
          {step > 1 && (
            <button className="px-4 py-2 text-sm rounded-md border" onClick={prev}>
              Indietro
            </button>
          )}
          <button
            className="px-4 py-2 text-sm rounded-md bg-blue-600 text-white ml-auto"
            onClick={handleNext}
            disabled={disabled}
          >
            {step === 4 ? 'Invia' : 'Avanti'}
          </button>
        </div>
      </div>
    </div>
  );
}
