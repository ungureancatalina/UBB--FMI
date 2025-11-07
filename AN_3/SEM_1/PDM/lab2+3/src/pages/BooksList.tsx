import React, { useEffect, useState, useCallback, useRef } from 'react';
import { IonPage, IonContent, IonInfiniteScroll, IonInfiniteScrollContent } from '@ionic/react';
import { useHistory } from 'react-router-dom';
import api from '../services/api';
import { getSocket } from '../services/socket';
import { logger } from '../utils/Logger';
import { deleteBook, flushQueue } from '../services/sync';
import { logout } from '../services/auth';
import { createAnimation } from '@ionic/react';
import { MapContainer, TileLayer, Marker, useMapEvents } from 'react-leaflet';
import L from 'leaflet';

interface MapClickHandlerProps {
    onSelect: (lat: number, lng: number) => void;
}

const MapClickHandler: React.FC<MapClickHandlerProps> = ({ onSelect }) => {
    useMapEvents({
        click(e) {
            onSelect(e.latlng.lat, e.latlng.lng);
        },
    });
    return null;
};

interface Book {
    id: number;
    title: string;
    author: string;
    isRead: boolean;
    addedAt: string;
    photo?: string;
    lat?: number;
    lng?: number;
}

const BooksList: React.FC = () => {
    const [books, setBooks] = useState<Book[]>([]);
    const [online, setOnline] = useState(navigator.onLine);
    const [page, setPage] = useState(1);
    const [hasMore, setHasMore] = useState(true);
    const [search, setSearch] = useState('');
    const [unsyncedCount] = useState(0);
    const history = useHistory();
    const [filter, setFilter] = useState<'all' | 'read' | 'unread'>('all');
    const [showMap, setShowMap] = useState(false);
    const [selectedBook, setSelectedBook] = useState<Book | null>(null);

    const CACHE_KEY = 'cached_books';


    const openBookOnMap = (book: Book) => {
        if (!book.lat || !book.lng) {
            alert('No location saved for this book yet.');
            return;
        }
        setSelectedBook(book);
        setShowMap(true);
    };

    const loadBooks = useCallback(
        async (reset = false) => {
            logger.add('Loading books...');
            try {
                const res = await api.get<Book[]>('/books', {
                    params: { page: reset ? 1 : page, limit: 5, search },
                });
                const data = res.data;

                setBooks((prev) => (reset ? data : [...prev, ...data]));
                localStorage.setItem(CACHE_KEY, JSON.stringify(reset ? data : [...books, ...data]));
                setHasMore(data.length === 5);
                logger.add(`Loaded ${data.length} books`);
            } catch {
                const cached = localStorage.getItem(CACHE_KEY);
                if (cached) {
                    setBooks(JSON.parse(cached));
                    logger.add('Offline mode → loaded from cache');
                }
            }
        },
        [page, search]
    );

    const listRef = useRef<HTMLDivElement | null>(null);

    const filteredBooks = books.filter((b) => {
        if (filter === 'read') return b.isRead;
        if (filter === 'unread') return !b.isRead;
        return true;
    });


    useEffect(() => {
        loadBooks(true);
    }, [search]);

    useEffect(() => {
        logger.add('Loading books...');
        api.get<Book[]>('/books')
            .then(res => {
                setBooks(res.data);
                logger.add(`Loaded ${res.data.length} books`);
            })
            .catch(() => logger.add('Failed to fetch books (offline mode).'));

        const socket = getSocket();

        socket.on('book:created', (b: Book) =>
            setBooks((prev) => {
                const exists = prev.some(
                    (x) =>
                        (x.id && b.id && x.id === b.id) ||
                        x.title.trim().toLowerCase() === b.title.trim().toLowerCase()
                );

                if (exists) {
                    logger.add(`Ignored duplicate (book: ${b.title})`);
                    return prev;
                }

                const filtered = prev.filter(
                    (x) => x.title.trim().toLowerCase() !== b.title.trim().toLowerCase()
                );

                logger.add(`Added synced book: ${b.title}`);
                return [b, ...filtered];
            })
        );


        socket.on('book:updated', (b: Book) =>
            setBooks((prev) => prev.map((x) => (x.id === b.id ? b : x)))
        );

        socket.on('book:deleted', (b: Book) =>
            setBooks((prev) => prev.filter((x) => x.id !== b.id))
        );


        window.addEventListener('online', () => {
            setOnline(true);
            flushQueue();
            logger.add('Back online → syncing queued changes');
        });
        window.addEventListener('offline', () => setOnline(false));

        window.addEventListener('books-changed', () => {
            const cached = localStorage.getItem('cached_books');
            if (cached) {
                setBooks(JSON.parse(cached));
                logger.add('Local cache updated (offline edit)');
            }
        });

    }, []);

    useEffect(() => {
        if (!listRef.current) return;

        const animation = createAnimation()
            .addElement(listRef.current)
            .duration(700)
            .easing('cubic-bezier(0.36, 0.66, 0.04, 1)')
            .keyframes([
                { offset: 0, opacity: '0', transform: 'translateY(50px) scale(0.9)' },
                { offset: 0.5, opacity: '0.7', transform: 'translateY(15px) scale(1.02)' },
                { offset: 1, opacity: '1', transform: 'translateY(0) scale(1)' },
            ]);

        animation.play();
    }, [filteredBooks.length]);


    const handleDelete = async (id: number) => {
        if (!window.confirm('Delete this book?')) return;
        await deleteBook(id);
        setBooks((prev) => prev.filter((b) => b.id !== id));
        localStorage.setItem(CACHE_KEY, JSON.stringify(books.filter((b) => b.id !== id)));
    };

    const loadMore = async (e: CustomEvent<void>) => {
        setPage((prev) => prev + 1);
        await loadBooks();
        (e.target as HTMLIonInfiniteScrollElement).complete();
    };


    return (
        <IonPage>
            <IonContent style={{ '--background': '#705357', padding: '15px' }}>
                <h1
                    style={{
                        textAlign: 'center',
                        color: '#fff',
                        fontSize: '2.5rem',
                        marginBottom: '15px',
                        animation: 'fadeInUpSoft 1s ease-out',
                    }}
                >
                    My Book Library
                </h1>


                <div
                    style={{
                        textAlign: 'center',
                        color: online ? '#2ecc71' : '#f1c40f',
                        marginBottom: '10px',
                    }}
                >
                    {online ? '🟢 Online' : '🟠 Offline Mode'}
                    {unsyncedCount > 0 && (
                        <div style={{ color: '#ffeb3b' }}>
                             {unsyncedCount} changes pending sync
                        </div>
                    )}
                </div>

                <input
                    placeholder="Search books..."
                    value={search}
                    onChange={(e) => {
                        setPage(1);
                        setSearch(e.target.value);
                    }}
                    style={{
                        width: '100%',
                        padding: '12px',
                        borderRadius: '12px',
                        border: '1px solid #d2c0ab',
                        backgroundColor: '#fff3e6',
                        color: '#800020',
                        fontWeight: '600',
                        fontSize: '1rem',
                        outline: 'none',
                        boxShadow: '0 3px 6px rgba(0,0,0,0.15)',
                        cursor: 'pointer',
                        transition: 'all 0.2s ease',
                    }}
                />

                <div
                    style={{
                        display: 'flex',
                        justifyContent: 'center',
                        marginBottom: '15px',
                    }}
                >
                    <select
                        value={filter}
                        onChange={(e) => {
                            setPage(1);
                            setFilter(e.target.value as 'all' | 'read' | 'unread');
                        }}
                        style={{
                            width: '100%',
                            padding: '12px',
                            borderRadius: '12px',
                            border: '1px solid #d2c0ab',
                            backgroundColor: '#fff3e6',
                            color: '#800020',
                            fontWeight: '600',
                            fontSize: '1rem',
                            outline: 'none',
                            boxShadow: '0 3px 6px rgba(0,0,0,0.15)',
                            cursor: 'pointer',
                            transition: 'all 0.2s ease',
                        }}
                        onFocus={(e) => (e.target.style.boxShadow = '0 0 0 3px rgba(128,0,32,0.3)')}
                        onBlur={(e) => (e.target.style.boxShadow = '0 3px 6px rgba(0,0,0,0.15)')}
                    >
                        <option value="all">All Books</option>
                        <option value="read">Read</option>
                        <option value="unread">Unread</option>
                    </select>
                </div>


                <button
                    onClick={() => history.push('/books/new')}
                    style={{
                        width: '100%',
                        padding: '12px',
                        marginBottom: '10px',
                        backgroundColor: '#831f54',
                        color: '#fff',
                        borderRadius: '12px',
                        fontWeight: 'bold',
                        cursor: 'pointer',
                        animation: 'pulsePrimary 1.5s infinite',
                    }}
                >
                    + Add New Book
                </button>

                <button
                    onClick={() => {
                        if (window.confirm('Are you sure you want to log out?')) logout();
                    }}
                    style={{
                        width: '100%',
                        padding: '12px',
                        marginBottom: '20px',
                        backgroundColor: '#cb2f3e',
                        color: '#fff',
                        borderRadius: '12px',
                        fontWeight: 'bold',
                        cursor: 'pointer',
                        animation: 'pulsePrimary 1.5s infinite',

                    }}
                >
                     Logout
                </button>

                <div ref={listRef}>
                {filteredBooks.map((book) => (
                    <div
                        key={book.id}
                        style={{
                            background: '#ffe7e7',
                            borderRadius: '15px',
                            padding: '15px',
                            marginBottom: '15px',
                            boxShadow: '0 6px 15px rgba(0,0,0,0.15)',
                            animation: 'fadeInUpSoft 0.4s ease-out',
                        }}
                    >
                        <h2 style={{ color: '#800020' }}>{book.title}</h2>
                        {(() => {
                            const unsyncedTitles = JSON.parse(localStorage.getItem('unsynced_books') || '[]');
                            const isUnsynced = unsyncedTitles.includes(book.title);
                            return (
                                isUnsynced && (
                                    <span
                                        style={{
                                            color: '#cb2f3e',
                                            fontWeight: 'bold',
                                            fontSize: '0.9rem',
                                            display: 'inline-block',
                                            marginTop: '4px',
                                        }}
                                    >
                                        Added offline
                                    </span>
                                )
                            );
                        })()}
                        <p>
                            <strong>Author:</strong> {book.author}
                        </p>
                        <p>
                            <strong>Status:</strong>{' '}
                            <span
                                style={{
                                    backgroundColor: book.isRead ? '#28a745' : '#ffc107',
                                    color: '#fff',
                                    borderRadius: '8px',
                                    padding: '3px 8px',
                                }}
                            >
                            {book.isRead ? 'Read' : 'Unread'}
                          </span>
                        </p>
                        <p>
                            <strong>Added:</strong>{' '}
                            {new Date(book.addedAt).toLocaleDateString()}
                        </p>

                        {book.photo && (
                            <img
                                src={book.photo.startsWith('data:') ? book.photo : `http://localhost:4000${book.photo}`}
                                alt={book.title}
                                style={{ width: '100%', borderRadius: '10px' }}
                            />
                        )}


                        <div style={{ display: 'flex', gap: '10px' }}>
                            <button
                                onClick={() => history.push(`/books/${book.id}`)}
                                style={{
                                    flex: 1,
                                    background: '#3e55ce',
                                    color: '#fff',
                                    borderRadius: '10px',
                                    padding: '10px',
                                }}
                            >
                                Edit
                            </button>
                            <button
                                onClick={() => handleDelete(book.id)}
                                style={{
                                    flex: 1,
                                    background: '#cb2f3e',
                                    color: '#fff',
                                    borderRadius: '10px',
                                    padding: '10px',
                                }}
                            >
                                Delete
                            </button>
                            <button
                                onClick={() => openBookOnMap(book)}
                                style={{
                                    flex: 1,
                                    background: '#2ecc71',
                                    color: '#fff',
                                    borderRadius: '10px',
                                    padding: '10px',
                                }}
                            >
                                Map
                            </button>
                        </div>
                    </div>
                ))}
                </div>
                <IonInfiniteScroll threshold="100px" disabled={!hasMore} onIonInfinite={loadMore}>
                    <IonInfiniteScrollContent loadingSpinner="bubbles" />
                </IonInfiniteScroll>
            </IonContent>
            {showMap && selectedBook && (
                <div
                    style={{
                        position: 'fixed',
                        top: 0,
                        left: 0,
                        width: '100vw',
                        height: '100vh',
                        background: '#000000aa',
                        zIndex: 9999,
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                    }}
                >
                    <div
                        style={{
                            width: '90%',
                            height: '80%',
                            background: '#fff',
                            borderRadius: '12px',
                            overflow: 'hidden',
                            position: 'relative',
                            display: 'flex',
                            flexDirection: 'column',
                        }}
                    >

                        <div style={{ flex: 1, position: 'relative' }}>
                            <MapContainer
                                center={[selectedBook.lat || 45.9432, selectedBook.lng || 24.9668]}
                                zoom={7}
                                style={{
                                    width: '100%',
                                    height: '100%',
                                    zIndex: 1,
                                }}
                            >
                                <TileLayer
                                    attribution='&copy; OpenStreetMap contributors'
                                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                                />

                                <MapClickHandler
                                    onSelect={(lat, lng) => {
                                        const confirmChange = window.confirm(
                                            `Change location?\nNew Lat: ${lat}\nNew Lng: ${lng}`
                                        );
                                        if (confirmChange) {
                                            setSelectedBook((prev) => (prev ? { ...prev, lat, lng } : null));
                                            alert('Location updated locally');
                                        }
                                    }}
                                />

                                {selectedBook.lat && selectedBook.lng && (
                                    <Marker
                                        position={[selectedBook.lat, selectedBook.lng]}
                                        icon={L.icon({
                                            iconUrl: 'https://cdn-icons-png.flaticon.com/512/684/684908.png',
                                            iconSize: [32, 32],
                                        })}
                                    />
                                )}
                            </MapContainer>

                            <button
                                onClick={() => setShowMap(false)}
                                style={{
                                    position: 'absolute',
                                    top: 10,
                                    right: 10,
                                    background: '#cb2f3e',
                                    color: '#fff',
                                    border: 'none',
                                    borderRadius: '8px',
                                    padding: '8px 12px',
                                    cursor: 'pointer',
                                    zIndex: 1000,
                                }}
                            >
                                Close
                            </button>
                        </div>
                    </div>
                </div>
            )}


        </IonPage>
    );
};

export default BooksList;
