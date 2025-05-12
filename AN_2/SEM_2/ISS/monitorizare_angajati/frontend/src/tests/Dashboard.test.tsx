import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import Dashboard from '../pages/Dashboard';
import axios from 'axios';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

jest.mock('../hooks/useNotifications', () => ({
    useNotifications: () => {}
}));

describe('Dashboard Page - Task Assignment', () => {
    beforeEach(() => {
        localStorage.setItem(
            'user',
            JSON.stringify({ id: 'employer', name: 'Boss', role: 'employer' })
        );
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('renders and allows assigning task with valid input', async () => {
        mockedAxios.get.mockImplementation((url) => {
            if (url.includes('/employees')) {
                return Promise.resolve({
                    data: [{ _id: 'emp1', name: 'Alice' }]
                });
            }
            return Promise.resolve({ data: [] });
        });

        render(<Dashboard />);

        const taskInput = await screen.findByPlaceholderText(/monthly report/i);
        const button = screen.getByRole('button', { name: /Assign Task/i });

        fireEvent.change(taskInput, { target: { value: '' } });
        fireEvent.click(button);

        expect(await screen.findByTestId('error-message')).toHaveTextContent(
            /task description is required/i
        );

        fireEvent.change(taskInput, {
            target: { value: 'Complete Report' }
        });

        const select = screen.getByLabelText(/select employee/i);
        fireEvent.change(select, { target: { value: 'emp1' } });

        mockedAxios.post.mockResolvedValueOnce({ data: {} });
        fireEvent.click(button);

        await waitFor(() => {
            expect(mockedAxios.post).toHaveBeenCalled();
        });
    });
});
