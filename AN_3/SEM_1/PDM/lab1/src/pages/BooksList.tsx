import React, { useEffect, useState } from 'react';
import { IonPage, IonContent } from '@ionic/react';
import { useHistory } from 'react-router-dom';
import api from '../services/api';
import { socket } from '../services/socket';
import { logger } from '../utils/Logger';

// interfata pentru carte
interface Book {
    id: number;
    title: string;
    author: string;
    publishedYear: number;
    isRead: number;
    addedAt: string;
}

const BooksList: React.FC = () => {
    // starea pentru lista de carti
    const [books, setBooks] = useState<Book[]>([]);
    const history = useHistory();


    // efect care ruleaza la montarea componentei
    useEffect(() => {
        // preluare carti initial din API
        logger.add('Fetching initial books...');
        api.get('/books').then(res => {
            logger.add(`Books fetched: ${res.data.length}`);
            setBooks(res.data);
        });

        // aevenimente websocket pentru carti create
        socket.on('book:created', (book) => {
            logger.add(`WebSocket: book:created – ${book.title}`);
            setBooks(prev => [book, ...prev]);
        });

        // evenimente websocket pentru carti actualizate
        socket.on('book:updated', (book) => {
            logger.add(`WebSocket: book:updated – ${book.title}`);
            setBooks(prev => prev.map(b => b.id === book.id ? book : b));
        });

        // evenimente websocket pentru carti sterse
        socket.on('book:deleted', (book) => {
            logger.add(`WebSocket: book:deleted – ${book.title}`);
            setBooks(prev => prev.filter(b => b.id !== book.id));
        });

        // curatare socket la demontarea componentei
        return () => {
            socket.off('book:created');
            socket.off('book:updated');
            socket.off('book:deleted');
        };
    }, []);

    // functie pentru stergerea unei carti
    const deleteBook = async (id: number) => {
        if (window.confirm('Are you sure you want to delete this book?')) {
            logger.add(`Deleting book with id=${id}`);
            await api.delete(`/books/${id}`);
        }
    };

    // render pentru lista de carti
    return (
        <IonPage>
            <IonContent style={{ '--background': '#705357', padding: '15px' }}>
                {/* titlu pagina */}
                <h1 style={{
                    textAlign: 'center',
                    color: '#ffffff',
                    fontSize: '2.5rem',
                    marginBottom: '15px',
                    fontFamily: 'Arial, sans-serif'
                }}>
                    📚 My Book Library
                </h1>

                {/* buton pentru adaugarea unei carti noi */}
                <button
                    onClick={() => {
                        logger.add('Navigating to Add New Book page');
                        history.push('/books/new');
                    }}
                    style={{
                        width: '100%',
                        padding: '12px',
                        marginBottom: '20px',
                        backgroundColor: '#831f54',
                        color: '#fff',
                        fontSize: '1.2rem',
                        borderRadius: '12px',
                        border: 'none',
                        fontWeight: 'bold',
                        cursor: 'pointer'
                    }}
                >
                    + Add New Book
                </button>

                {/* mapam lista de carti */}
                {books.map(book => (
                    // container pentru fiecare carte
                    <div key={book.id} style={{
                        background: '#ffe7e7',
                        borderRadius: '15px',
                        padding: '15px',
                        marginBottom: '15px',
                        boxShadow: '0 6px 15px rgba(0,0,0,0.15)'
                    }}>
                        {/* titlu si detalii carte */}
                        <h2 style={{ color: '#800020', fontSize: '1.8rem', marginBottom: '8px'}}>{book.title}</h2>
                        <p style={{ margin: '3px 0', color: '#000' }}><strong>Author:</strong> {book.author}</p>
                        <p style={{ margin: '3px 0', color: '#000' }}><strong>Year:</strong> {book.publishedYear}</p>

                        {/* status carte (Read / Unread) */}
                        <p style={{ margin: '3px 0' }}>
                            <strong>Status:</strong>{' '}
                            <span style={{
                                padding: '3px 8px',
                                borderRadius: '8px',
                                backgroundColor: book.isRead ? '#28a745' : '#ffc107',
                                color: '#fff',
                                fontWeight: 'bold'
                            }}>
                                {book.isRead ? 'Read' : 'Unread'}
                            </span>
                        </p>

                        {/* data adaugarii */}
                        <p style={{ margin: '3px 0', color: '#555' }}>
                            <strong>Added:</strong> {new Date(book.addedAt).toLocaleDateString()}
                        </p>

                        {/* butoane Edit si Delete */}
                        <div style={{ display: 'flex', gap: '10px', marginTop: '12px' }}>
                            <button
                                onClick={() => {
                                    logger.add(`Editing book with id=${book.id}`);
                                    history.push(`/books/${book.id}`);
                                }}
                                style={{
                                    flex: 1,
                                    padding: '10px',
                                    backgroundColor: '#3e55ce',
                                    color: '#fff',
                                    borderRadius: '10px',
                                    border: 'none',
                                    cursor: 'pointer',
                                    fontWeight: 'bold'
                                }}
                            >
                                Edit
                            </button>
                            <button
                                onClick={() => deleteBook(book.id)}
                                style={{
                                    flex: 1,
                                    padding: '10px',
                                    backgroundColor: '#cb2f3e',
                                    color: '#fff',
                                    borderRadius: '10px',
                                    border: 'none',
                                    cursor: 'pointer',
                                    fontWeight: 'bold'
                                }}
                            >
                                Delete
                            </button>
                        </div>
                    </div>
                ))}
            </IonContent>
        </IonPage>
    );
};

export default BooksList;
