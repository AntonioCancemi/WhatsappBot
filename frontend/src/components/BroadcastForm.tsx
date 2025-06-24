import { useState } from 'react';
import { createBroadcast } from '../api';
import { BroadcastCreateRequest } from '../types';

interface Props {
  onCreated(): void;
}

export default function BroadcastForm({ onCreated }: Props) {
  const [text, setText] = useState('');
  const [date, setDate] = useState('');

  const submit = async () => {
    if (!text || !date) return;
    const payload: BroadcastCreateRequest = {
      text,
      scheduledAt: new Date(date).toISOString(),
    };
    await createBroadcast(payload);
    setText('');
    setDate('');
    onCreated();
  };

  return (
    <div className="card mb-3">
      <div className="card-body">
        <h5 className="card-title">Nuovo broadcast</h5>
        <div className="mb-2">
          <textarea
            className="form-control"
            value={text}
            onChange={(e) => setText(e.target.value)}
            placeholder="Messaggio"
          />
        </div>
        <div className="mb-2">
          <input
            type="datetime-local"
            className="form-control"
            value={date}
            onChange={(e) => setDate(e.target.value)}
          />
        </div>
        <button className="btn btn-primary" onClick={submit} disabled={!text || !date}>
          Pianifica
        </button>
      </div>
    </div>
  );
}
