import { useEffect, useState } from 'react';
import api from '../../api';
import { Slot } from '../../types';

export default function useSlots(serviceId: string, date: string) {
  const [slots, setSlots] = useState<Slot[]>([]);
  useEffect(() => {
    if (serviceId && date) {
      api
        .get<Slot[]>(`/booking/slots?serviceId=${serviceId}&date=${date}`)
        .then((r) => setSlots(r.data));
    }
  }, [serviceId, date]);
  return slots;
}
