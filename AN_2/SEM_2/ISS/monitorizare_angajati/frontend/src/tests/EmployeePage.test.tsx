import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import EmployeePage from '../pages/EmployeePage';
import axios from 'axios';
import { MemoryRouter } from 'react-router-dom';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

jest.mock('../hooks/useNotifications', () => ({
    useNotifications: () => {}
}));

describe('EmployeePage Task Flow', () => {
    beforeEach(() => {
        localStorage.setItem('user', JSON.stringify({ id: '123', name: 'Worker' }));
        mockedAxios.get.mockResolvedValue({
            data: [
                {
                    _id: 't1',
                    description: 'Fix bug',
                    status: 'in_progress',
                    employeeName: 'Worker'
                }
            ]
        });
    });

    it('loads and selects task then completes it', async () => {
        render(
            <MemoryRouter>
                <EmployeePage />
            </MemoryRouter>
        );

        expect(await screen.findByRole('option', { name: /Fix bug/i })).toBeInTheDocument();

        const select = screen.getByRole('combobox');
        fireEvent.change(select, { target: { value: 't1' } });

        mockedAxios.put.mockResolvedValueOnce({});
        const doneButton = screen.getByRole('button', {
            name: /Set Task As Finished/i
        });
        fireEvent.click(doneButton);

        await waitFor(() => {
            expect(mockedAxios.put).toHaveBeenCalledWith(
                'http://localhost:5000/api/task/t1/complete'
            );
        });
    });
});
