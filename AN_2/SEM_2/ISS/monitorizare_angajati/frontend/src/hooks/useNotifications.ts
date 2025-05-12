import { useEffect } from 'react';
import { io } from 'socket.io-client';
import { notificationCenter } from '../utils/NotificationCenter';

const socket = io('http://localhost:5000');

export const useNotifications = (userId: string) => {
    useEffect(() => {
        if (!userId) return;

        socket.emit('join', userId);
        console.log(`Joined socket room: ${userId}`);

        socket.on('notification', (msg: string) => {
            console.log(`Notification received for ${userId}:`, msg);
            notificationCenter.notify(userId, msg);
        });

        return () => {
            socket.off('notification');
        };
    }, [userId]);
};
