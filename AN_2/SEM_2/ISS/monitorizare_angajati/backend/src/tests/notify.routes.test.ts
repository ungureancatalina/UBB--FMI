import request from 'supertest';
import app from '../app';

describe('Notify Routes', () => {
    jest.setTimeout(30000);
    it('sends notification', async () => {
        const res = await request(app).post('/api/notify').send({ userId: 'test-user', message: 'Hello!' });
        expect(res.status).toBe(200);
        expect(res.body.status).toBe('sent');
    });
});