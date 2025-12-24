const sqlite3 = require("sqlite3").verbose();
const path = require("path");

const dbPath = path.resolve(__dirname, "books.db");
const db = new sqlite3.Database(dbPath);

db.serialize(() => {
  db.run(`
    CREATE TABLE IF NOT EXISTS users (
                                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       username TEXT UNIQUE NOT NULL,
                                       password TEXT NOT NULL
    )
  `);

  db.run(
      `INSERT OR IGNORE INTO users (id, username, password) VALUES
      (1, 'ana', 'ana'),
      (2, 'bob', 'bob')
      `
  );

  db.run(`
    CREATE TABLE IF NOT EXISTS books (
                                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       userId INTEGER NOT NULL,
                                       title TEXT NOT NULL,
                                       author TEXT NOT NULL,
                                       publishDate TEXT NOT NULL,
                                       available INTEGER NOT NULL,
                                       photo TEXT,
                                       lat REAL,
                                       lng REAL,
                                       FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE
      )
  `);

  db.run(
      `INSERT OR IGNORE INTO books (id, userId, title, author, publishDate, available)
      VALUES
      (1, 1, 'Pride and Prejudice', 'Jane Austen', '2024-01-01', 1),
      (2, 1, 'The Hobbit', 'J.R.R. Tolkien', '2023-05-12', 0),
      (3, 2, '1984', 'George Orwell', '2022-11-10', 1)
      `
  );
});

module.exports = db;
