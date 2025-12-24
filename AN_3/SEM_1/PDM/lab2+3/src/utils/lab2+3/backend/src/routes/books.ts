import { Router, Request, Response } from 'express';
import type { Server as SocketIOServer } from 'socket.io';
import db from '../db/sqlite';
import { verifyToken } from './auth';
import fs from 'fs';
import path from 'path';
import multer from 'multer';
import { fileURLToPath } from 'url';
import { dirname } from 'path';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

const photoDir = path.join(__dirname, '/poze');

if (!fs.existsSync(photoDir)) {
    fs.mkdirSync(photoDir, { recursive: true });
}

export interface BookRow {
    id: number;
    title: string;
    author: string;
    isRead: number;
    addedAt: string;
    userId: number;
    photo?: string;
    lat?: number;
    lng?: number;
}

export default function createBooksRouter(io: SocketIOServer): Router {
    const router = Router();
    const upload = multer({ dest: photoDir });

    router.use(verifyToken);

    router.get('/', (req: Request, res: Response): void => {
        const userId = req.user!.userId;
        const search = String(req.query.search ?? '');
        const page = Number(req.query.page ?? '1');
        const limit = Number(req.query.limit ?? '10');
        const offset = (page - 1) * limit;

        const rows = db
            .prepare(
                `
                    SELECT * FROM books
                    WHERE userId = ? AND (title LIKE ? OR author LIKE ?)
                    ORDER BY datetime(addedAt) DESC
                        LIMIT ? OFFSET ?
                `
            )
            .all(userId, `%${search}%`, `%${search}%`, limit, offset);

        res.json(rows);
    });

    router.post('/', (req: Request, res: Response): void => {
        const userId = req.user!.userId;
        const { title, author, isRead, photo, lat, lng } = req.body as {
            title: string;
            author: string;
            isRead: boolean;
            photo?: string;
            lat?: number;
            lng?: number;
        };
        const addedAt = new Date().toISOString();

        const stmt = db.prepare(`
            INSERT INTO books (title, author, isRead, addedAt, photo, lat, lng, userId)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        `);
        const info = stmt.run(title, author, isRead ? 1 : 0, addedAt, photo, lat, lng, userId);

        const book = db
            .prepare('SELECT * FROM books WHERE id=? AND userId=?')
            .get(info.lastInsertRowid, userId) as BookRow;

        io.to(`user:${userId}`).emit('book:created', book);
        res.json(book);
    });

    router.put('/:id', (req: Request, res: Response): void => {
        const userId = req.user!.userId;
        const id = Number(req.params.id);
        const { title, author, isRead, photo, lat, lng } = req.body as {
            title: string;
            author: string;
            isRead: boolean;
            photo?: string;
            lat?: number;
            lng?: number;
        };

        db.prepare(
            `UPDATE books SET title=?, author=?, isRead=?, photo=?, lat=?, lng=? WHERE id=? AND userId=?`
        ).run(title, author, isRead ? 1 : 0, photo, lat, lng, id, userId);

        const updated = db
            .prepare('SELECT * FROM books WHERE id=? AND userId=?')
            .get(id, userId) as BookRow;

        io.to(`user:${userId}`).emit('book:updated', updated);
        res.json(updated);
    });

    router.delete('/:id', (req: Request, res: Response): void => {
        const userId = req.user!.userId;
        const id = Number(req.params.id);

        const book = db
            .prepare('SELECT * FROM books WHERE id=? AND userId=?')
            .get(id, userId) as BookRow;

        db.prepare('DELETE FROM books WHERE id=? AND userId=?').run(id, userId);
        io.to(`user:${userId}`).emit('book:deleted', book);
        res.json(book);
    });

    router.post('/photo/:id', upload.single('photo'), (req, res) => {
        const userId = req.user!.userId;
        const id = Number(req.params.id);

        if (!req.file) {
            return res.status(400).json({ error: 'No file uploaded' });
        }

        const filePath = `/uploads/${req.file.filename}`;
        db.prepare('UPDATE books SET photo=? WHERE id=? AND userId=?').run(filePath, id, userId);

        const updated = db
            .prepare('SELECT * FROM books WHERE id=? AND userId=?')
            .get(id, userId);

        io.to(`user:${userId}`).emit('book:updated', updated);
        res.json(updated);
    });

    return router;
}
