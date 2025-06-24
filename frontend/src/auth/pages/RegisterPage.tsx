import React, { FormEvent, useState } from 'react';
import useRegister from '../hooks/useRegister';
import { useNavigate } from 'react-router-dom';

export default function RegisterPage() {
  const { register, error } = useRegister();
  const [email, setEmail] = useState('');
  const [fullName, setFullName] = useState('');
  const [password, setPassword] = useState('');
  const [tenantName, setTenantName] = useState('');

  const navigate = useNavigate();

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await register({ email, password, fullName, tenantName });

    navigate('/dashboard');
  };

  return (
    <div className="flex items-center justify-center min-h-screen">
      <form onSubmit={handleSubmit} className="space-y-4 w-full max-w-sm">
        <input
          className="w-full border p-2 rounded"
          placeholder="Tenant"
          value={tenantName}
          onChange={(e) => setTenantName(e.target.value)}
          required
        />
        <input
          className="w-full border p-2 rounded"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          className="w-full border p-2 rounded"
          placeholder="Full name"
          value={fullName}
          onChange={(e) => setFullName(e.target.value)}
 
          required
        />
        <input
          className="w-full border p-2 rounded"
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        {error && <div className="text-red-600 text-sm">{error}</div>}
        <button className="w-full bg-blue-600 text-white p-2 rounded" type="submit">
          Register
        </button>
      </form>
    </div>
  );
}
