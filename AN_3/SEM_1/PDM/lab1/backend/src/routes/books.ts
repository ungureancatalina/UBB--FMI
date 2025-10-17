import { Router } from 'express';
import db from '../db/sqlite';
import Logger from '../logger';

const router = Router();

// GET /books - returneaza toate cartile
router.get('/', (_req, res) => {
    Logger.log('GET /books called');
    try {
        // selecteaza toate cartile din baza de date, ordonate dupa data adaugarii
        const books = db.prepare('SELECT * FROM books ORDER BY addedAt DESC').all();
        Logger.log(`Found ${books.length} books`);

        // trimite lista de carti inapoi catre frontend in format JSON
        res.json(books);
    } catch (err: unknown) {
        // verifica daca eroarea este de tip Error si ia mesajul
        const msg = err instanceof Error ? err.message : String(err);
        Logger.error(`GET /books error: ${msg}`);

        // trimite inapoi cod HTTP 500 (Internal Server Error) si mesaj JSON
        res.status(500).json({ error: 'Internal server error' });
    }
});

// POST /books - adauga o carte noua
router.post('/', (req, res) => {
    const { title, author, publishedYear, isRead } = req.body;
    const addedAt = new Date().toISOString();
    Logger.log(`POST /books called with title="${title}"`);

    try {
        // insereaza cartea in baza de date
        const stmt = db.prepare('INSERT INTO books (title, author, publishedYear, isRead, addedAt) VALUES (?, ?, ?, ?, ?)');
        const info = stmt.run(title, author, publishedYear, isRead ? 1 : 0, addedAt);

        // selecteaza cartea nou creata
        const book = db.prepare('SELECT * FROM books WHERE id = ?').get(info.lastInsertRowid);

        Logger.log(`Book created with id=${book.id}`);

        // trimite eveniment WebSocket catre frontend ca s-a creat o carte
        req.app.get('io')?.emit('book:created', book);

        // trimite inapoi cartea creata in format JSON
        res.json(book);
    } catch (err: unknown) {
        const msg = err instanceof Error ? err.message : String(err);
        Logger.error(`POST /books error: ${msg}`);
        res.status(500).json({ error: 'Internal server error' });
    }
});

// PUT /books/:id - actualizeaza o carte existenta
router.put('/:id', (req, res) => {
    const { title, author, publishedYear, isRead } = req.body;
    const id = req.params.id;

    Logger.log(`PUT /books/${id} called`);

    try {
        // actualizeaza datele cartii in baza de date
        db.prepare('UPDATE books SET title=?, author=?, publishedYear=?, isRead=? WHERE id=?')
            .run(title, author, publishedYear, isRead ? 1 : 0, id);

        // preia cartea actualizata
        const book = db.prepare('SELECT * FROM books WHERE id=?').get(id);
        Logger.log(`Book updated with id=${book.id}`);

        // trimite eveniment WebSocket catre frontend ca s-a actualizat cartea
        req.app.get('io')?.emit('book:updated', book);

        // trimite inapoi cartea actualizata in format JSON
        res.json(book);
    } catch (err: unknown) {
        const msg = err instanceof Error ? err.message : String(err);
        Logger.error(`PUT /books/${id} error: ${msg}`);
        res.status(500).json({ error: 'Internal server error' });
    }
});

// DELETE /books/:id - sterge o carte
router.delete('/:id', (req, res) => {
    const id = req.params.id;
    Logger.log(`DELETE /books/${id} called`);

    try {
        // preia cartea care va fi stearsa
        const book = db.prepare('SELECT * FROM books WHERE id=?').get(id);
        // sterge cartea din baza de date
        db.prepare('DELETE FROM books WHERE id=?').run(id);

        Logger.log(`Book deleted with id=${id}`);

        // trimite eveniment WebSocket ca s-a sters cartea
        req.app.get('io')?.emit('book:deleted', book);

        // trimite inapoi cartea stearsa in format JSON
        res.json(book);
    } catch (err: unknown) {
        const msg = err instanceof Error ? err.message : String(err);
        Logger.error(`DELETE /books/${id} error: ${msg}`);
        res.status(500).json({ error: 'Internal server error' });
    }
});

export default router;
