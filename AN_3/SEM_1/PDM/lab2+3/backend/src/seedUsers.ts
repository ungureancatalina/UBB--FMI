import bcrypt from 'bcryptjs';
import db from './db/sqlite';
import Logger from './logger';

const users = [
    { username: 'ana', password: 'parolaAna' },
    { username: 'bob', password: 'parolaBob' },
] as const;

for (const u of users) {
    const hash = bcrypt.hashSync(u.password, 10);
    db.prepare('INSERT OR IGNORE INTO users (username, passwordHash) VALUES (?, ?)').run(u.username, hash);
    Logger.log(`User ensured: ${u.username}`);
}

Logger.log('Users seeded successfully!');
