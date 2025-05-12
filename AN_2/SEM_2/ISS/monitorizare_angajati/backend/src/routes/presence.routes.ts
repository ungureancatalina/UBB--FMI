import { Router, Request, Response } from 'express';
import Presence from '../models/Presence';
import User from '../models/User';
import { sendNotification } from '../index';

const router = Router();

router.post('/checkin', async (req: Request, res: Response) => {
    const { userId, checkIn } = req.body;
    const employee = await User.findById(userId);
    if (!employee) return res.status(404).json({ message: 'User not found' });

    sendNotification("employer", `${employee.name} checked in at ${checkIn}`);


    try {
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        const existing = await Presence.findOne({
            user: userId,
            createdAt: { $gte: today }
        }).sort({ createdAt: -1 });

        if (existing && !existing.checkOut) {
            existing.checkIn = checkIn;
            await existing.save();
            return res.status(200).json({ message: 'Check-in updated', presence: existing });
        }

        const presence = new Presence({ user: userId, checkIn });
        await presence.save();

        res.status(201).json({ message: 'Check-in saved', presence });

    } catch (err) {
        console.error('Check-in error:', err);
        res.status(500).json({ message: 'Server error during check-in', err });
    }
});

router.post('/checkout', async (req, res) => {
    const { userId, checkOut } = req.body;
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const presence = await Presence.findOne({
        user: userId,
        createdAt: { $gte: today }
    });

    const employee = await User.findById(userId);
    if (!employee) return res.status(404).json({ message: 'User not found' });
    sendNotification("employer", `${employee.name} checked out at ${checkOut}`);

    if (!presence) return res.status(404).json({ message: 'No presence for today' });

    presence.checkOut = checkOut;
    await presence.save();

    res.status(200).json({ message: 'Check-out saved', presence });
});

router.get('/status/:userId', async (req: Request, res: Response) => {
    const { userId } = req.params;

    try {
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        const presence = await Presence.findOne({
            user: userId,
            createdAt: { $gte: today }
        }).sort({ createdAt: -1 });

        const isPresent = !!(presence && presence.checkIn && !presence.checkOut);

        res.status(200).json({ present: isPresent });
    } catch (err) {
        console.error('Presence status error:', err);
        res.status(500).json({ message: 'Server error checking presence status' });
    }
});

export default router;
