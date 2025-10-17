// importam modulele necesare
import express from 'express';
import http from 'http';
import { Server } from 'socket.io';
import booksRouter from './routes/books';
import Logger from './logger';
import cors from 'cors';
import logsRouter from './routes/logs';

const app = express();
const server = http.createServer(app);
const io = new Server(server, { cors: { origin: '*' , methods: ['GET', 'POST']} });

// salvare instanta de WebSocket in Express
app.set('io', io);

// middleware pentru CORS (permite requesturi din orice origine)
app.use(cors());

//  middleware pentru parsarea JSON din request body
app.use(express.json());

// ruta pentru log-uri
app.use('/api/logs', logsRouter);

// ruta pentru carti
app.use('/api/books', booksRouter);

// eveniment pentru conectarea unui client WebSocket
io.on('connection', (socket) => {
    Logger.log(`Socket connected: ${socket.id}`);

    // eveniment pentru deconectarea clientului
    socket.on('disconnect', () => {
        Logger.log(`Socket disconnected: ${socket.id}`);
    });
});

// pornire serverul HTTP pe portul 4000
const PORT = 4000;
server.listen(PORT, () => {
    Logger.log(`Server running on http://localhost:${PORT}`);
});
