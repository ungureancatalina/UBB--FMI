import api from './api';
import { logger } from '../utils/Logger';

export interface Book {
    id?: number;
    title: string;
    author: string;
    isRead: boolean;
    addedAt: string;
    photo?: string;
    lat?: number;
    lng?: number;
}

type CreateMutation = { type: 'create'; payload: Omit<Book, 'id'> };
type UpdateMutation = { type: 'update'; id: number; payload: Omit<Book, 'id'> };
type DeleteMutation = { type: 'delete'; id: number };
type Mutation = CreateMutation | UpdateMutation | DeleteMutation;

const QUEUE_KEY = 'pendingMutations';

function getQueue(): Mutation[] {
    try {
        return JSON.parse(localStorage.getItem(QUEUE_KEY) || '[]');
    } catch {
        return [];
    }
}

function setQueue(q: Mutation[]): void {
    localStorage.setItem(QUEUE_KEY, JSON.stringify(q));
}

export function enqueue(m: Mutation): void {
    const q = getQueue();
    q.push(m);
    setQueue(q);
    logger.add(`Queued mutation: ${m.type}`);

    try {
        const unsynced = JSON.parse(localStorage.getItem('unsynced_books') || '[]');
        if (m.type === 'create') {
            unsynced.push(m.payload.title);
        } else if (m.type === 'update') {
            unsynced.push(`update-${m.id}`);
        } else if (m.type === 'delete') {
            unsynced.push(`delete-${m.id}`);
        }
        localStorage.setItem('unsynced_books', JSON.stringify(unsynced));
    } catch {
        // ignore
    }
}

export async function flushQueue(): Promise<void> {
    if (!navigator.onLine) return;
    const q = getQueue();
    if (q.length === 0) return;

    const remaining: Mutation[] = [];

    for (const m of q) {
        try {
            if (m.type === 'create') {
                await api.post('/api/books', m.payload);
            } else if (m.type === 'update') {
                await api.put(`/api/books/${m.id}`, m.payload);
            } else if (m.type === 'delete') {
                await api.delete(`/api/books/${m.id}`);
            }
        } catch {
            remaining.push(m);
        }
    }

    const unsynced = JSON.parse(localStorage.getItem('unsynced_books') || '[]');
    const syncedTitles = q
        .filter((m): m is CreateMutation => m.type === 'create')
        .map((m) => m.payload.title);

    const filtered = unsynced.filter((t: string) => !syncedTitles.includes(t));
    localStorage.setItem('unsynced_books', JSON.stringify(filtered));

    setQueue(remaining);
}

export async function createBook(book: Omit<Book, 'id'>): Promise<Book> {
    if (!navigator.onLine) {
        enqueue({ type: 'create', payload: book });
        const unsynced = JSON.parse(localStorage.getItem('unsynced_books') || '[]');
        if (!unsynced.includes(book.title)) {
            unsynced.push(book.title);
            localStorage.setItem('unsynced_books', JSON.stringify(unsynced));
        }
        return book as Book;
    }

    const res = await api.post('/api/books', book);
    return res.data;
}

export async function updateBook(id: number, payload: Omit<Book, 'id'>): Promise<Book> {
    if (navigator.onLine) {
        try {
            const res = await api.put<Book>(`/api/books/${id}`, payload);
            logger.add(`Book updated: ${payload.title}`);
            return res.data;
        } catch {
            logger.add('Failed online update, queued offline');
        }
    }
    enqueue({ type: 'update', id, payload });
    return { id, ...payload };
}

export async function deleteBook(id: number): Promise<void> {
    if (navigator.onLine) {
        try {
            await api.delete(`/api/books/${id}`);
            logger.add(`Book deleted id=${id}`);
            return;
        } catch {
            logger.add('Failed online delete, queued offline');
        }
    }
    enqueue({ type: 'delete', id });
}

export function getPendingCount(): number {
    return getQueue().length;
}
