import BookingDashboard from '../components/booking/BookingDashboard';
import ServiceEditor from '../components/booking/ServiceEditor';

export default function AdminBookingPage() {
  return (
    <div className="container mt-3">
      <h2>Gestione Prenotazioni</h2>
      <ServiceEditor />
      <BookingDashboard />
    </div>
  );
}
