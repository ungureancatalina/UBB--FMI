import Database from 'better-sqlite3';
import path from 'path';
import Logger from '../logger';

//baza de date books.db
const db = new Database(path.join(__dirname, 'books.db'));

// creare tabela books daca nu exista
db.prepare(`
    CREATE TABLE IF NOT EXISTS books (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT,
        author TEXT,
        publishedYear INTEGER,
        isRead INTEGER,
        addedAt TEXT
    )
`).run();

// log pt ca baza de date si tabela sunt gata
Logger.log('Database initialized and table "books" ensured.');

export default db;
