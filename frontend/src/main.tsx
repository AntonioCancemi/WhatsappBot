import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Landing from './pages/Landing';
import OnboardPage from './pages/OnboardPage';
import 'bootstrap/dist/css/bootstrap.min.css';

const router = createBrowserRouter([
  { path: '/', element: <Landing /> },
  { path: '/onboard', element: <OnboardPage /> },
]);

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
);
