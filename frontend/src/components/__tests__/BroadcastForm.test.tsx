import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { vi } from 'vitest';
import BroadcastForm from '../BroadcastForm';
import * as api from '../../api';

vi.mock('../../api');

const mocked = api as { createBroadcast: any };

test('submit invokes API', async () => {
  const create = vi.fn().mockResolvedValue({});
  mocked.createBroadcast = create;
  render(<BroadcastForm onCreated={() => {}} />);
  await userEvent.type(screen.getByPlaceholderText(/Messaggio/i), 'hello');
  await userEvent.type(screen.getByLabelText(/datetime-local/i), '2024-01-01T12:00');
  await userEvent.click(screen.getByRole('button', { name: /Pianifica/i }));
  expect(create).toHaveBeenCalled();
});
