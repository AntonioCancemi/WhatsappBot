import BookingForm from '../components/booking/BookingForm';

export default function BookingPage() {
  const userId = localStorage.getItem('userId') || '';
  return (
    <div className="container mt-3">
      <h2>Prenota un servizio</h2>
      <BookingForm userId={userId} />
    </div>
  );
}
