import { useEffect, useState } from 'react';
import api from '../../api';
import { ServiceItem } from '../../types';

export default function useServices() {
  const [services, setServices] = useState<ServiceItem[]>([]);
  const load = () => {
    api.get<ServiceItem[]>('/admin/booking/services').then((r) => setServices(r.data));
  };
  useEffect(load, []);
  const create = (s: Partial<ServiceItem>) => api.post('/admin/booking/services', s).then(load);
  return { services, reload: load, create };
}
