import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Landing from './pages/Landing';
import OnboardPage from './pages/OnboardPage';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import RequireAuth from './components/auth/RequireAuth';
import { AuthProvider } from './hooks/useAuth';
import 'bootstrap/dist/css/bootstrap.min.css';

const router = createBrowserRouter([
  { path: '/', element: <Landing /> },
  { path: '/onboard', element: <OnboardPage /> },
  { path: '/login', element: <LoginPage /> },
  { path: '/dashboard', element: (
      <RequireAuth>
        <DashboardPage />
      </RequireAuth>
    ) },
]);

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>,
);
