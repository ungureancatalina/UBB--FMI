import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import Login from '../pages/Login';
import axios from 'axios';
import { MemoryRouter } from 'react-router-dom';
import { act } from 'react';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('Login Page Flow', () => {
    it('shows error for invalid email', async () => {
        render(
            <MemoryRouter>
                <Login />
            </MemoryRouter>
        );

        fireEvent.change(screen.getByLabelText(/Email/i), {
            target: { value: 'wrongemail' }
        });
        fireEvent.change(screen.getByLabelText(/Password/i), {
            target: { value: 'somepassword' }
        });

        await fireEvent.submit(screen.getByRole('button', { name: /^Log In$/i }));

        expect(await screen.findByText(/Invalid email address/i)).toBeInTheDocument();
    });

    it('shows error for short password', async () => {
        render(
            <MemoryRouter>
                <Login />
            </MemoryRouter>
        );

        fireEvent.change(screen.getByLabelText(/Email/i), {
            target: { value: 'test@mail.com' }
        });
        fireEvent.change(screen.getByLabelText(/Password/i), {
            target: { value: '123' }
        });

        await fireEvent.submit(screen.getByRole('button', { name: /^Log In$/i }));

        expect(await screen.findByText(/at least 6 characters/i)).toBeInTheDocument();
    });

    it('sends login data to API and stores user', async () => {
        render(
            <MemoryRouter>
                <Login />
            </MemoryRouter>
        );

        const mockUser = {
            id: '1',
            name: 'User',
            role: 'employee',
            email: 'user@mail.com'
        };

        mockedAxios.post.mockResolvedValue({
            data: { token: 'abc', user: mockUser }
        });

        fireEvent.change(screen.getByLabelText(/Email/i), {
            target: { value: 'user@mail.com' }
        });
        fireEvent.change(screen.getByLabelText(/Password/i), {
            target: { value: '123456' }
        });

        await act(async () => {
            fireEvent.click(screen.getByRole('button', { name: /^Log In$/i }));
        });

        await waitFor(() => {
            expect(mockedAxios.post).toHaveBeenCalledWith(
                'http://localhost:5000/api/user/login',
                { email: 'user@mail.com', password: '123456' }
            );
        });
    });
});
