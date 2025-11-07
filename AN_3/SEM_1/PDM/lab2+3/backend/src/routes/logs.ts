import { Router, Request, Response } from 'express';
import Logger from '../logger';

const router = Router();

router.post('/', (req: Request, res: Response): void => {
    const { message } = req.body as { message?: string };
    if (!message) {
        res.status(400).json({ error: 'Missing message' });
        return;
    }
    Logger.log(`[FRONTEND] ${message}`);
    res.json({ success: true });
});

export default router;
