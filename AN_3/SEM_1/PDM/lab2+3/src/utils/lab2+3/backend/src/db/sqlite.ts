import Database from 'better-sqlite3';
import path from 'path';
import Logger from '../logger';

const dbFile = path.join(process.cwd(), 'books.db');
const db = new Database(dbFile);

db.prepare(`
  CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    passwordHash TEXT NOT NULL
  )
`).run();

db.prepare(`
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
        )
`).run();


Logger.log('Database initialized with tables: users, books');
export default db;
