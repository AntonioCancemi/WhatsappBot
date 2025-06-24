import api from '../../api';
import { Booking } from '../../types';

export default function useBooking() {
  const create = (body: any) => api.post<Booking>('/booking/create', body);
  const approve = (id: string) => api.post('/booking/approve', { bookingId: id });
  const pending = () => api.get<Booking[]>('/booking/pending').then((r) => r.data);
  return { create, approve, pending };
}
