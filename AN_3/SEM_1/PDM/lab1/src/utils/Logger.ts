class Logger {
    //  mesajele de log din sesiunea curenta
    private logs: string[] = [];

    add(msg: string) {
        // timestamp-ul curent
        const timestamp = new Date().toISOString();
        // combinare timestamp cu mesajul
        const logMsg = `[${timestamp}] ${msg}`;
        // salvare in array-ul local
        this.logs.push(logMsg);

        console.log(logMsg);

        // trimitere logul catre server printr-un request POST
        fetch('http://localhost:4000/api/logs', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ message: logMsg })
        }).catch(err => console.error('Failed to send log to server', err));
    }

    getLogs() {
        return [...this.logs];
    }

    clear() {
        this.logs = [];
    }
}

// un singur obiect logger care poate fi folosit in tot frontend-ul
export const logger = new Logger();
