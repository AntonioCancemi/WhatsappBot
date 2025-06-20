import { useState } from 'react';
import UserList from './components/UserList';
import MessageHistory from './components/MessageHistory';
import TemplateSender from './components/TemplateSender';
import { User } from './types';

export default function App() {
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

  return (
    <div className="container mt-4">
      <div className="row">
        <div className="col-md-4">
          <UserList onSelect={setSelectedUser} />
        </div>
        <div className="col-md-8">
          {selectedUser && (
            <>
              <h5>{selectedUser.name || selectedUser.phone}</h5>
              <MessageHistory userId={selectedUser.id} />
              <TemplateSender userId={selectedUser.id} />
            </>
          )}
        </div>
      </div>
    </div>
  );
}
