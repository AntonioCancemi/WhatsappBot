import { useEffect, useState } from 'react';
import { getBroadcasts } from '../api';
import { BroadcastMessage } from '../types';

export default function BroadcastList() {
  const [items, setItems] = useState<BroadcastMessage[]>([]);

  useEffect(() => {
    load();
  }, []);

  const load = () => {
    getBroadcasts().then(setItems);
  };

  return (
    <div className="card">
      <div className="card-body">
        <h5 className="card-title">Broadcast programmati</h5>
        <ul className="list-group">
          {items.map((b) => (
            <li key={b.id} className="list-group-item d-flex justify-content-between">
              <span>{b.text}</span>
              <small>{new Date(b.scheduledAt).toLocaleString()}</small>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}
