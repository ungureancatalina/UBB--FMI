import http from 'http';
import { Server } from 'socket.io';
import app from './app';
import connectToMongo from './db';

const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: '*',
        methods: ['GET', 'POST'],
    },
});

const PORT = process.env.PORT || 5000;

io.on('connection', (socket) => {
    console.log(`🔌 Client connected: ${socket.id}`);

    socket.on('join', (userId: string) => {
        socket.join(userId);
        console.log(`${userId} joined their room`);
    });
});

export const sendNotification = (userId: string, message: string) => {
    io.to(userId).emit('notification', message);
};

if (process.env.NODE_ENV !== 'test') {
    connectToMongo().then(() => {
        server.listen(PORT, () => {
            console.log(`Server running on port ${PORT}`);
        });
    });
}
