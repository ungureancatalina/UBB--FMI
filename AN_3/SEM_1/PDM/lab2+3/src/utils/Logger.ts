export class Logger {
    private logs: string[] = [];

    add(msg: string): void {
        const line = `[${new Date().toISOString()}] ${msg}`;
        this.logs.push(line);
        console.log(line);

        try {
            fetch('http://localhost:4000/api/logs', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ message: line }),
            }).catch(() => {
            });
        } catch {
            // ignora
        }
    }

    getAll(): string[] {
        return [...this.logs];
    }

    clear(): void {
        this.logs = [];
    }
}

export const logger = new Logger();
