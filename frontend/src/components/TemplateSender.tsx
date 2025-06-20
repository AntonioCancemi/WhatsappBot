import { useEffect, useState } from 'react';
import api from '../api';
import { Template } from '../types';

interface Props {
  userId: string;
}

export default function TemplateSender({ userId }: Props) {
  const [templates, setTemplates] = useState<Template[]>([]);
  const [selected, setSelected] = useState('');

  useEffect(() => {
    api.get<Template[]>('/templates').then((res) => {
      setTemplates(res.data);
      if (res.data.length > 0) {
        setSelected(res.data[0].name);
      }
    });
  }, []);

  const send = () => {
    if (!selected) return;
    api
      .post('/template/send', {
        userId,
        templateName: selected,
      })
      .then(() => alert('Template sent'))
      .catch(() => alert('Error sending template'));
  };

  return (
    <div className="input-group mb-3">
      <select
        className="form-select"
        value={selected}
        onChange={(e) => setSelected(e.target.value)}
      >
        {templates.map((t) => (
          <option key={t.name} value={t.name}>
            {t.name}
          </option>
        ))}
      </select>
      <button className="btn btn-primary" onClick={send} disabled={!selected}>
        Invia template
      </button>
    </div>
  );
}
