import React, { useState } from 'react';
import Wizard from '../components/Wizard';

export default function Landing() {
  const [open, setOpen] = useState(false);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-4">
      <h1 className="text-3xl font-bold mb-4 text-center">
        Automatizza WhatsApp per la tua azienda
      </h1>
      <p className="text-lg mb-6 text-center">
        Configura in pochi passi il tuo bot personalizzato.
      </p>
      <button className="px-6 py-3 rounded-md bg-blue-600 text-white" onClick={() => setOpen(true)}>
        Inizia ora
      </button>
      <Wizard open={open} onClose={() => setOpen(false)} />
    </div>
  );
}
