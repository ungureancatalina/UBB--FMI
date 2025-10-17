import fs from 'fs';
import path from 'path';

// cale fisier log
const logFilePath = path.join(__dirname, 'server.log');

class Logger {
    // log mesaj
    static log(message: string) {
        const timestamp = new Date().toISOString(); // adauga data si ora
        const logMessage = `[${timestamp}] ${message}\n`;

        // scrie mesajul in fisier (async)
        fs.appendFile(logFilePath, logMessage, (err) => {
            if (err) console.error('Failed to write log:', err);
        });

        console.log(logMessage);
    }

    // log eroare
    static error(message: string) {
        const timestamp = new Date().toISOString();
        const logMessage = `[${timestamp}] ERROR: ${message}\n`;

        fs.appendFile(logFilePath, logMessage, (err) => {
            if (err) console.error('Failed to write log:', err);
        });

        console.error(logMessage);
    }
}

export default Logger;
