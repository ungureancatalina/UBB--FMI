import { io, Socket } from 'socket.io-client';
import { logger } from '../utils/Logger';

let socket: Socket | null = null;

export function getSocket(): Socket {
    const token = localStorage.getItem('token');
    if (!token) {
        logger.add('[WS] No token → not connecting');
        return io({ autoConnect: false });
    }

    if (socket && socket.connected) {
        return socket;
    }

    if (socket) {
        socket.disconnect();
        socket = null;
    }

    socket = io('http://localhost:4000', {
        transports: ['websocket'],
        auth: { token },
        reconnectionAttempts: 5,
        reconnectionDelay: 1000,
    });

    socket.on('connect', () => logger.add(`[WS] Connected: ${socket?.id}`));
    socket.on('disconnect', (reason) => logger.add(`[WS] Disconnected: ${reason}`));
    socket.on('connect_error', (err) => logger.add(`[WS] Connect error: ${err.message}`));

    return socket;
}
