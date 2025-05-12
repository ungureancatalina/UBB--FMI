import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import CheckOut from '../pages/CheckOut';
import axios from 'axios';
import { MemoryRouter } from 'react-router-dom';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('CheckOut Page', () => {
    beforeEach(() => {
        localStorage.setItem('user', JSON.stringify({ id: '123' }));
    });

    it('should validate check-out time and call API', async () => {
        render(
            <MemoryRouter>
                <CheckOut />
            </MemoryRouter>
        );

        const input = screen.getByLabelText(/Check-out Time/i);
        const button = screen.getByRole('button', { name: /Save Check-Out/i });

        fireEvent.change(input, { target: { value: '10:00' } });
        fireEvent.click(button);

        expect(
            await screen.findByText(/between 12:00 and 23:00/i)
        ).toBeInTheDocument();

        fireEvent.change(input, { target: { value: '18:30' } });
        mockedAxios.post.mockResolvedValue({ data: { message: 'Check-out saved' } });

        fireEvent.click(button);

        await waitFor(() => {
            expect(mockedAxios.post).toHaveBeenCalledWith(
                'http://localhost:5000/api/presence/checkout',
                { userId: '123', checkOut: '18:30' }
            );
        });
    });
});
