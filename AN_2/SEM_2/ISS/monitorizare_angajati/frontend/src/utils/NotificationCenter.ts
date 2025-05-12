type Callback = (message: string) => void;

class NotificationCenter {
    private observers: { [userId: string]: Callback[] } = {};

    subscribe(userId: string, callback: Callback) {
        if (!this.observers[userId]) {
            this.observers[userId] = [];
        }
        this.observers[userId].push(callback);
    }

    unsubscribe(userId: string, callback: Callback) {
        if (this.observers[userId]) {
            this.observers[userId] = this.observers[userId].filter(cb => cb !== callback);
        }
    }

    notify(userId: string, message: string) {
        this.observers[userId]?.forEach(cb => cb(message));
    }
}

export const notificationCenter = new NotificationCenter();
