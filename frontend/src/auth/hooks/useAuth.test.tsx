import { renderHook, act } from '@testing-library/react';
import useAuth, { AuthProvider } from './useAuth';

test('login stores token', () => {
  const wrapper = ({ children }: { children: React.ReactNode }) => (
    <AuthProvider>{children}</AuthProvider>
  );
  const { result } = renderHook(() => useAuth(), { wrapper });
  act(() => {
    result.current.login('t');
  });
  expect(result.current.token).toBe('t');
});
