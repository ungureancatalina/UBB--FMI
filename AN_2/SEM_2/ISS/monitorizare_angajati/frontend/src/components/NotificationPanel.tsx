import { useEffect, useState } from "react";
import { notificationCenter } from "../utils/NotificationCenter";
import { notificationBox, notificationWrapper } from "../styles/notificationsStyles";

const NotificationPanel = ({ userId }: { userId: string }) => {
    const [messages, setMessages] = useState<string[]>([]);

    useEffect(() => {
        const handle = (msg: string) => {
            setMessages((prev) => [...prev, msg]);

            setTimeout(() => {
                setMessages((prev) => prev.slice(1));
            }, 5000);
        };

        notificationCenter.subscribe(userId, handle);
        return () => {
            notificationCenter.unsubscribe(userId, handle);
        };
    }, [userId]);

    if (messages.length === 0) return null;

    return (
        <div className={notificationWrapper}>
            {messages.map((msg, i) => (
                <div key={i} className={notificationBox}>
                    {msg}
                </div>
            ))}
        </div>
    );
};

export default NotificationPanel;
