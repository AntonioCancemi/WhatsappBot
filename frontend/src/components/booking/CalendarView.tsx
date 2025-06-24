import { useState } from 'react';
import useSlots from '../../hooks/booking/useSlots';

interface Props {
  serviceId: string;
  onSelect: (date: string, time: string) => void;
}

export default function CalendarView({ serviceId, onSelect }: Props) {
  const [date, setDate] = useState('');
  const slots = useSlots(serviceId, date);

  return (
    <div>
      <input
        type="date"
        className="form-control mb-2"
        value={date}
        onChange={(e) => setDate(e.target.value)}
      />
      <div className="d-flex flex-wrap gap-2">
        {slots.map((s) => (
          <button
            key={s.time}
            className="btn btn-outline-primary"
            onClick={() => onSelect(date, s.time)}
          >
            {s.time} ({s.remaining})
          </button>
        ))}
      </div>
    </div>
  );
}
