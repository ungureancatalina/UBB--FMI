import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import CheckIn from '../pages/CheckIn';
import axios from 'axios';
import { MemoryRouter } from 'react-router-dom';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('CheckIn Page - Full flow', () => {
    beforeEach(() => {
        localStorage.setItem('user', JSON.stringify({ id: '123', name: 'Tester' }));
    });

    it('should render and validate input time', async () => {
        render(
            <MemoryRouter>
                <CheckIn />
            </MemoryRouter>
        );

        const input = screen.getByLabelText(/Check-in Time/i);
        const button = screen.getByRole('button', { name: /Save Check-In/i });

        fireEvent.change(input, { target: { value: '23:00' } });
        fireEvent.click(button);

        expect(await screen.findByText(/between 06:00 and 12:00/i)).toBeInTheDocument();

        fireEvent.change(input, { target: { value: '09:00' } });
        mockedAxios.post.mockResolvedValue({ data: { message: 'Check-in saved' } });

        fireEvent.click(button);

        await waitFor(() => {
            expect(mockedAxios.post).toHaveBeenCalledWith(
                'http://localhost:5000/api/presence/checkin',
                { userId: '123', checkIn: '09:00' }
            );
        });
    });
});