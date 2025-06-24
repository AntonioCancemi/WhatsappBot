import { useState } from 'react';
import useServices from '../../hooks/booking/useServices';
import useBooking from '../../hooks/booking/useBooking';
import CalendarView from './CalendarView';

interface Props {
  userId: string;
}

export default function BookingForm({ userId }: Props) {
  const { services } = useServices();
  const booking = useBooking();
  const [serviceId, setServiceId] = useState('');
  const [time, setTime] = useState('');
  const [date, setDate] = useState('');

  const submit = () => {
    booking.create({ userId, serviceId, date, time }).then(() => alert('Richiesta inviata'));
  };

  return (
    <div>
      <select className="form-select mb-2" value={serviceId} onChange={(e) => setServiceId(e.target.value)}>
        <option value="">Seleziona servizio</option>
        {services.map((s) => (
          <option key={s.id} value={s.id}>
            {s.name}
          </option>
        ))}
      </select>
      {serviceId && (
        <div>
          <CalendarView
            serviceId={serviceId}
            onSelect={(d, t) => {
              setDate(d);
              setTime(t);
            }}
          />
        </div>
      )}
      {time && (
        <button className="btn btn-primary mt-2" onClick={submit}>
          Prenota {date} {time}
        </button>
      )}
    </div>
  );
}
