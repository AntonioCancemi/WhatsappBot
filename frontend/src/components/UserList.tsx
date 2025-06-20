import { useEffect, useState } from 'react';
import api from '../api';
import { User } from '../types';

interface Props {
  onSelect(user: User): void;
}

export default function UserList({ onSelect }: Props) {
  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
    api.get<User[]>('/users').then((res) => setUsers(res.data));
  }, []);

  return (
    <ul className="list-group">
      {users.map((u) => (
        <li
          key={u.id}
          className="list-group-item d-flex justify-content-between align-items-center"
          role="button"
          onClick={() => onSelect(u)}
        >
          <span>{u.name || u.phone}</span>
          {u.lastMessage && <small className="text-muted">{u.lastMessage}</small>}
        </li>
      ))}
    </ul>
  );
}
