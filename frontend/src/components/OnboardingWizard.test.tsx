import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { vi } from 'vitest';
import OnboardingWizard from './OnboardingWizard';

vi.mock('../api', () => ({
  onboardStart: vi.fn().mockResolvedValue({ tenantId: '1', token: 't' }),
  onboardVerify: vi.fn().mockResolvedValue({ verified: true }),
}));

test('advances to second step on Avanti click', async () => {
  render(<OnboardingWizard />);
  await userEvent.type(screen.getByLabelText(/Nome azienda/i), 'Acme');
  await userEvent.type(screen.getByLabelText(/Numero di telefono/i), '123');
  await userEvent.click(screen.getByRole('button', { name: /Avanti/i }));
  expect(screen.getByLabelText(/Codice SMS/i)).toBeInTheDocument();
});
