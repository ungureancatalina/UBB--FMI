import { render, screen, fireEvent } from '@testing-library/react';
import ForgotPassword from '../pages/ForgotPassword';
import { MemoryRouter } from 'react-router-dom';

jest.mock('axios');

describe('ForgotPassword Page', () => {
    it('submits email and calls reset API', async () => {
        render(
            <MemoryRouter>
                <ForgotPassword />
            </MemoryRouter>
        );

        const input = screen.getByPlaceholderText(/you@example.com/i);
        const button = screen.getByRole('button', { name: /Send Reset Instructions/i });

        fireEvent.change(input, { target: { value: 'reset@test.com' } });
        fireEvent.click(button);

        expect(
            await screen.findByText(/we sent instructions to reset your password/i)
        ).toBeInTheDocument();
    });
});
