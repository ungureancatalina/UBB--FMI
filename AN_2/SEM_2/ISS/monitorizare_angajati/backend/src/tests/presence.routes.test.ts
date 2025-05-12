import request from 'supertest';
import app from '../app';
import User, { IUser } from '../models/User';
import Presence from '../models/Presence';
import mongoose from 'mongoose';

describe('Presence Routes', () => {
    jest.setTimeout(30000);
    let userId: string;

    beforeAll(async () => {
        await mongoose.connect(process.env.MONGO_URI!);

        const user = await User.create({
            name: 'Presence Test',
            email: 'p@t.com',
            password: '123456',
            role: 'employee',
        });

        userId = (user._id as mongoose.Types.ObjectId).toString();
    });

    afterAll(async () => {
        await User.deleteMany({});
        await Presence.deleteMany({});
        await mongoose.disconnect();
    });

    it('should allow check-in', async () => {
        const res = await request(app).post('/api/presence/checkin').send({
            userId,
            checkIn: '08:00',
        });

        expect(res.status).toBe(201);
        expect(res.body.presence.checkIn).toBe('08:00');
    });

    it('should allow check-out', async () => {
        await request(app).post('/api/presence/checkin').send({ userId, checkIn: '08:00' });

        const res = await request(app).post('/api/presence/checkout').send({
            userId,
            checkOut: '17:00',
        });

        expect(res.status).toBe(200);
        expect(res.body.presence.checkOut).toBe('17:00');
    });

    it('should return presence status', async () => {
        await request(app).post('/api/presence/checkin').send({ userId, checkIn: '09:00' });
        const res = await request(app).get(`/api/presence/status/${userId}`);
        expect(res.status).toBe(200);
        expect(typeof res.body.present).toBe('boolean');
    });
});
