import request from 'supertest';
import app from '../app';
import mongoose from 'mongoose';
import connectToMongo from '../db';

beforeAll(async () => {
    await connectToMongo();
});

afterAll(async () => {
    await mongoose.connection.close();
});


describe('User Routes', () => {
    jest.setTimeout(30000);
    it('should create a new user', async () => {
        const res = await request(app).post('/api/user/add-user').send({
            name: 'Test User',
            email: 'test@user.com',
            password: '123456',
            role: 'employee'
        });
        expect(res.status).toBe(201);
    });


    it('rejects duplicate user', async () => {
        await request(app).post('/api/user/add-user').send({
            name: 'Test',
            email: 'test@example.com',
            password: '123456',
            role: 'employee'
        });
        const res = await request(app).post('/api/user/add-user').send({
            name: 'Test2',
            email: 'test@example.com',
            password: '123456',
            role: 'employee'
        });
        expect(res.status).toBe(400);
    });

    it('logs in user', async () => {
        await request(app).post('/api/user/add-user').send({
            name: 'Login User',
            email: 'login@example.com',
            password: '123456',
            role: 'employee'
        });
        const res = await request(app).post('/api/user/login').send({
            email: 'login@example.com',
            password: '123456'
        });
        expect(res.status).toBe(200);
        expect(res.body).toHaveProperty('token');
    });
});