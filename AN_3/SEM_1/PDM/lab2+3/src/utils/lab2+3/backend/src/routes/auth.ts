import { Router, Request, Response, NextFunction } from 'express';
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import db from '../db/sqlite';
import Logger from '../logger';

const router = Router();
const SECRET = process.env.JWT_SECRET ?? 'supersecret123';

export const verifyToken = (req: Request, res: Response, next: NextFunction): void => {
    const authHeader = req.headers.authorization;
    if (!authHeader) {
        res.status(401).json({ error: 'No token' });
        return;
    }
    const token = authHeader.split(' ')[1];
    try {
        const decoded = jwt.verify(token, SECRET) as { userId: number };
        req.user = decoded;
        next();
    } catch {
        res.status(403).json({ error: 'Invalid token' });
    }
};

router.post('/login', (req: Request, res: Response): void => {
    const { username, password } = req.body as { username: string; password: string };

    const user = db.prepare('SELECT id, username, passwordHash FROM users WHERE username = ?').get(username) as
        | { id: number; username: string; passwordHash: string }
        | undefined;

    if (!user) {
        res.status(401).json({ error: 'Invalid username or password' });
        return;
    }

    const valid = bcrypt.compareSync(password, user.passwordHash);
    if (!valid) {
        res.status(401).json({ error: 'Invalid username or password' });
        return;
    }

    const token = jwt.sign({ userId: user.id }, SECRET, { expiresIn: '2h' });
    Logger.log(`User logged in: ${username}`);
    res.json({ token });
});

router.post('/logout', (req: Request, res: Response): void => {
    let username = 'unknown';
    try {
        const token = (req.headers.authorization?.split(' ')[1]) ?? '';
        const decoded = jwt.verify(token, SECRET) as { userId: number };
        const row = db.prepare('SELECT username FROM users WHERE id = ?').get(decoded.userId) as
            | { username: string }
            | undefined;
        username = row?.username ?? 'unknown';
    } catch {
        // ignore errors
    }
    Logger.log(`User logged out: ${username}`);
    res.json({ success: true });
});

router.get('/me', verifyToken, (req: Request, res: Response): void => {
    res.json({ userId: req.user!.userId });
});

export default router;
