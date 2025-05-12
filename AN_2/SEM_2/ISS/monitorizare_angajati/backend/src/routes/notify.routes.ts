import { Router } from 'express';
import { sendNotification } from '../index';

const router = Router();

router.post('/', (req, res) => {
    const { userId, message } = req.body;
    sendNotification(userId, message);
    res.status(200).json({ status: 'sent' });
});

export default router;
