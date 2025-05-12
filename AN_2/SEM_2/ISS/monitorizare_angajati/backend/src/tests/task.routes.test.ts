import request from 'supertest';
import app from '../app';
import User from '../models/User';
import mongoose from 'mongoose';
import connectToMongo from '../db';

beforeAll(async () => {
    await connectToMongo();
});

afterAll(async () => {
    await mongoose.connection.close();
});


describe('Task Routes', () => {
    jest.setTimeout(30000);
    let employeeId: string;

    beforeEach(async () => {
        await User.deleteMany({});
        const employee = await User.create({
            name: 'Employee',
            email: 'emp@test.com',
            password: '123456',
            role: 'employee'
        });

        employeeId = (employee._id as mongoose.Types.ObjectId).toString();
    });

    it('creates a task', async () => {
        const res = await request(app).post('/api/task').send({ employeeId, description: 'New Task' });
        expect(res.status).toBe(201);
        expect(res.body.description).toBe('New Task');
    });

    it('gets tasks for employee', async () => {
        await request(app).post('/api/task').send({ employeeId, description: 'Employee Task' });
        const res = await request(app).get(`/api/task/employee/${employeeId}`);
        expect(res.status).toBe(200);
        expect(res.body.length).toBeGreaterThan(0);
    });
});
