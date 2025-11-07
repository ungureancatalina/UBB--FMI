import fs from 'fs';
import path from 'path';

const logFilePath = path.join(process.cwd(), 'server.log');

class Logger {
    static log(message: string): void {
        const timestamp = new Date().toISOString();
        const msg = `[${timestamp}] ${message}\n`;
        fs.appendFileSync(logFilePath, msg);
        console.log(msg.trim());
    }

    static error(message: string): void {
        const timestamp = new Date().toISOString();
        const msg = `[${timestamp}] ERROR: ${message}\n`;
        fs.appendFileSync(logFilePath, msg);
        console.error(msg.trim());
    }
}

export default Logger;
