import { useState } from 'react';
import useServices from '../../hooks/booking/useServices';

export default function ServiceEditor() {
  const { services, create } = useServices();
  const [name, setName] = useState('');

  const add = () => {
    create({ name, durationMinutes: 30, bufferMinutes: 10, maxPerSlot: 1 }).then(() => setName(''));
  };

  return (
    <div>
      <div className="input-group mb-2">
        <input className="form-control" value={name} onChange={(e) => setName(e.target.value)} />
        <button className="btn btn-primary" onClick={add} disabled={!name}>
          Aggiungi
        </button>
      </div>
      <ul className="list-group">
        {services.map((s) => (
          <li key={s.id} className="list-group-item">
            {s.name}
          </li>
        ))}
      </ul>
    </div>
  );
}
