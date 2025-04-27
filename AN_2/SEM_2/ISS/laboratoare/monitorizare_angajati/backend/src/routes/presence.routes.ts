import { Router, Request, Response } from 'express';
import Presence from '../models/Presence';

const router = Router();

router.post('/checkin', async (req: Request, res: Response) => {
    const { userId, checkIn } = req.body;

    try {
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        const existing = await Presence.findOne({
            user: userId,
            createdAt: { $gte: today }
        });

        if (existing) {
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
    const presence = await Presence.findOne({ user: userId, createdAt: { $gte: today } });

    if (!presence) return res.status(404).json({ message: 'No presence for today' });

    presence.checkOut = checkOut;
    await presence.save();

    res.status(200).json({ message: 'Check-out saved', presence });
});


export default router;
