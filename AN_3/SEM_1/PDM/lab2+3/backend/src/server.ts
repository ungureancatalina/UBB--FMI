import express, { Application } from 'express';
import cors from 'cors';
import http from 'http';
import { Server as SocketIOServer, Socket } from 'socket.io';
import jwt from 'jsonwebtoken';
import path from 'path';
import multer from 'multer';
import fs from 'fs';

import Logger from './logger';
import authRoutes from './routes/auth';
import createBooksRouter from './routes/books';
import logsRoutes from './routes/logs';

const SECRET = process.env.JWT_SECRET ?? 'supersecret123';
const PORT = Number(process.env.PORT ?? 4000);

const app: Application = express();

const allowedOrigins = ['http://localhost:5173', 'http://localhost:8100'];

app.use(
    cors({
        origin: (origin, callback) => {
            if (!origin || allowedOrigins.includes(origin)) {
                callback(null, true);
            } else {
                callback(new Error('Not allowed by CORS'));
            }
        },
        methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
        allowedHeaders: ['Content-Type', 'Authorization'],
        credentials: true,
    })
);

app.use(express.json());

const server = http.createServer(app);
const io: SocketIOServer = new SocketIOServer(server, {
    cors: { origin: allowedOrigins, methods: ['GET', 'POST', 'PUT', 'DELETE'] },
});

io.use((socket: Socket, next) => {
    const token = socket.handshake.auth?.token as string | undefined;
    if (!token) return next(new Error('No token'));
    try {
        const decoded = jwt.verify(token, SECRET) as { userId: number };
        (socket.data as { userId: number }).userId = decoded.userId;
        next();
    } catch {
        next(new Error('Invalid token'));
    }
});

io.on('connection', (socket: Socket) => {
    const userId = (socket.data as { userId: number }).userId;
    Logger.log(`WebSocket connected: userId=${userId}`);

    const room = `user:${userId}`;
    socket.join(room);

    socket.on('disconnect', (reason: string) => {
        Logger.log(`WebSocket disconnected (userId=${userId}): ${reason}`);
    });
});


const photoDir = path.join(process.cwd(), 'poze');
if (!fs.existsSync(photoDir)) fs.mkdirSync(photoDir, { recursive: true });

console.log('Photo upload folder:', photoDir);

const storage = multer.diskStorage({
    destination: (req, file, cb) => cb(null, photoDir),
    filename: (req, file, cb) => {
        const unique = Date.now() + '-' + Math.round(Math.random() * 1e9);
        cb(null, `${unique}-${file.originalname}`);
    },
});
const upload = multer({ storage });

app.post('/upload', upload.single('photo'), (req, res) => {
    if (!req.file) return res.status(400).json({ error: 'No photo uploaded' });
    const relativePath = `/poze/${req.file.filename}`;
    res.json({ photo: relativePath });
});


app.use('/poze', express.static(photoDir));
app.use('/api/auth', authRoutes);
app.use('/api/books', createBooksRouter(io));
app.use('/api/logs', logsRoutes);

server.listen(PORT, () => {
    Logger.log(`Server running on http://localhost:${PORT}`);
});
