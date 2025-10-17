// importare functia io din socket.io-client pentru a conecta la server WebSocket
import { io } from 'socket.io-client';

// conexiune WebSocket catre serverul local
export const socket = io('http://localhost:4000', { transports: ['websocket'] });

// evenimentul de conectare
socket.on('connect', () => console.log('Connected to WebSocket server'));
socket.on('connect_error', (err) => console.log('WebSocket connect error:', err));