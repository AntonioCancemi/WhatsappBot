import { useEffect, useState } from 'react';
import api from '../api';
import { Message } from '../types';

interface Props {
  userId: string;
}

export default function MessageHistory({ userId }: Props) {
  const [history, setHistory] = useState<Message[]>([]);

  useEffect(() => {
    api.get<Message[]>(`/users/${userId}/messages`).then((res) => setHistory(res.data));
  }, [userId]);

  return (
    <div className="border p-2 mb-3" style={{ height: '300px', overflowY: 'auto' }}>
      {history.map((m) => (
        <div key={m.id} className="mb-1">
          <span className={m.direction === 'OUT' ? 'text-success' : 'text-primary'}>
            [{new Date(m.timestamp).toLocaleTimeString()}]
          </span>{' '}
          {m.text}
        </div>
      ))}
    </div>
  );
}
