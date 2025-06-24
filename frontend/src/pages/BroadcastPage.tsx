import BroadcastForm from '../components/BroadcastForm';
import BroadcastList from '../components/BroadcastList';

export default function BroadcastPage() {
  return (
    <div className="container py-4">
      <h2 className="mb-3">Gestione Broadcast</h2>
      <BroadcastForm onCreated={() => {}} />
      <BroadcastList />
    </div>
  );
}
