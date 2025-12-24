import db from './db/sqlite';
import Logger from './logger';

const existingUsers = db.prepare('SELECT id, username FROM users').all() as {
    id: number;
    username: string;
}[];

if (!existingUsers.length) {
    Logger.error('No users found. Run seedUsers.ts first.');
    process.exit(1);
}

db.exec(`
    CREATE TABLE IF NOT EXISTS books (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT NOT NULL,
        author TEXT NOT NULL,
        isRead INTEGER NOT NULL DEFAULT 0,
        addedAt TEXT NOT NULL,
        photo TEXT,
        lat REAL,
        lng REAL,
        userId INTEGER NOT NULL,
        FOREIGN KEY (userId) REFERENCES users(id)
    );
`);

const sampleBooks = [
    {
        title: 'To Kill a Mockingbird',
        author: 'Harper Lee',
        isRead: true,
        photo: null,
        lat: 40.7128,
        lng: -74.006,
    },
    {
        title: '1984',
        author: 'George Orwell',
        isRead: true,
        photo: null,
        lat: 51.5072,
        lng: -0.1276,
    },
    {
        title: 'The Great Gatsby',
        author: 'F. Scott Fitzgerald',
        isRead: false,
        photo: null,
        lat: 25.7617,
        lng: -80.1918,
    },
    {
        title: 'Pride and Prejudice',
        author: 'Jane Austen',
        isRead: true,
        photo: null,
        lat: 51.4545,
        lng: -2.5879,
    },
    {
        title: 'Moby Dick',
        author: 'Herman Melville',
        isRead: false,
        photo: null,
        lat: 41.5801,
        lng: -71.4774,
    },
    {
        title: 'The Catcher in the Rye',
        author: 'J.D. Salinger',
        isRead: false,
        photo: null,
        lat: 40.7831,
        lng: -73.9712,
    },
] as const;

for (const user of existingUsers) {
    Logger.log(`Seeding books for user: ${user.username}`);

    for (const book of sampleBooks) {
        const addedAt = new Date().toISOString();

        const existing = db
            .prepare(`SELECT id FROM books WHERE title = ? AND userId = ?`)
            .get(book.title, user.id);

        if (existing) {
            Logger.log(`Skipping existing book: ${book.title} for ${user.username}`);
            continue;
        }

        db.prepare(`
            INSERT INTO books (title, author, isRead, addedAt, photo, lat, lng, userId)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        `).run(
            book.title,
            book.author,
            book.isRead ? 1 : 0,
            addedAt,
            book.photo,
            book.lat,
            book.lng,
            user.id
        );
    }
}

Logger.log('Books seeded successfully!');
