import { useEffect, useState } from 'react';
import useBooking from '../../hooks/booking/useBooking';

export default function BookingDashboard() {
  const booking = useBooking();
  const [items, setItems] = useState([] as any[]);

  const load = () => {
    booking.pending().then(setItems);
  };
  useEffect(load, []);

  const approve = (id: string) => {
    booking.approve(id).then(load);
  };

  return (
    <div>
      <h3>Prenotazioni in attesa</h3>
      <ul className="list-group">
        {items.map((b) => (
          <li key={b.id} className="list-group-item d-flex justify-content-between">
            {b.date} {b.time}
            <button className="btn btn-sm btn-success" onClick={() => approve(b.id)}>
              Approva
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}
