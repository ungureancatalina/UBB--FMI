import express from 'express';
import cors from 'cors';
import http from 'http';
import { Server } from 'socket.io';
import booksRoutes from './routes/books';
import logsRouter from './routes/logs';

const app = express();

// activeaza CORS pentru a permite cereri de la frontend
app.use(cors());

// permite parsarea request-urilor cu JSON
app.use(express.json());

// rutele pentru carti
app.use('/api/books', booksRoutes);

app.use('/api/logs', logsRouter);


// creaza server HTTP
const server = http.createServer(app);

// server WebSocket pentru notificari in timp real
const io = new Server(server, { cors: { origin: '*' } });
app.set('io', io);

// logheaza cand un client se conecteaza prin WebSocket
io.on('connection', () => {
    console.log('Client connected via websocket');
});

// porneste serverul pe portul 4000
server.listen(4000, () => console.log('Server running on port 4000'));
