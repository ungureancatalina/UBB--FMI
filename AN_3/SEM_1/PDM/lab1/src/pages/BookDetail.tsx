import React, { useEffect, useState } from 'react';
import { IonPage, IonContent } from '@ionic/react';
import { useParams, useHistory } from 'react-router-dom';
import api from '../services/api';
import { logger } from '../utils/Logger';

// interfata pentru o carte
interface Book {
    id?: number;
    title: string;
    author: string;
    publishedYear: number;
    isRead: boolean;
}

const BookDetail: React.FC = () => {
    // preluare parametrul 'id' din ruta (pentru edit sau new)
    const { id } = useParams<{ id: string }>();
    const history = useHistory();

    // starea pentru carte
    const [book, setBook] = useState<Book>({
        title: '',
        author: '',
        publishedYear: new Date().getFullYear(),
        isRead: false,
    });

    // efect care se executa la montarea componentei sau cand id se schimba
    useEffect(() => {
        if (id !== 'new') {
            // la editarea unei carti existente, luam datele din API
            logger.add(`Fetching book details for id=${id}`);
            api.get('/books').then(res => {
                const b = res.data.find((x: Book) => x.id === Number(id));
                if (b) setBook({ ...b, isRead: !!b.isRead });
            });
        }
    }, [id]);

    // functie pentru salvarea cartii (new sau edit)
    const saveBook = async () => {
        logger.add('Save button clicked');

        // validare campuri
        if (!book.title.trim()) return alert('Please enter a title');
        if (!book.author.trim()) return alert('Please enter the author');
        if (!book.publishedYear || book.publishedYear < 0) return alert('Enter a valid year');

        // creare sau actualizare cartea in API
        if (id === 'new') {
            logger.add('Creating new book...');
            await api.post('/books', { ...book, addedAt: new Date().toISOString() });
        } else {
            logger.add(`Updating book with id=${id}...`);
            await api.put(`/books/${id}`, book);
        }

        logger.add('Book saved successfully');
        // revenire la lista de carti
        history.push('/books');
    };

    // render
    return (
        // pagina Ionic
        <IonPage>
            <IonContent
                style={{
                    '--background': '#705357',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    padding: '20px',
                    minHeight: '100vh',
                    flexDirection: 'column',
                }}
            >
                {/* containerul pentru formular */}
                <div
                    style={{
                        width: '100%',
                        maxWidth: '450px',
                        background: '#ffe7e7',
                        padding: '25px',
                        borderRadius: '20px',
                        boxShadow: '0 6px 20px rgba(0,0,0,0.2)',
                        display: 'flex',
                        flexDirection: 'column',
                        gap: '15px',
                    }}
                >
                    {/* titlul formularului */}
                    <h2 style={{ textAlign: 'center', color: '#800020', fontSize: '2rem', marginBottom: '15px' }}>
                        {id === 'new' ? 'Add New Book' : 'Edit Book'}
                    </h2>

                    {/* camp pentru titlu */}
                    <label style={{ color: '#800020', fontWeight: 'bold' }}>Title</label>
                    <input
                        type="text"
                        value={book.title}
                        onChange={e => setBook({ ...book, title: e.target.value })}
                        style={{
                            width: '100%',
                            padding: '12px',
                            borderRadius: '10px',
                            border: '1px solid #d2c0ab',
                            backgroundColor: '#fff3e6',
                            fontSize: '1rem',
                        }}
                    />

                    {/* camp pentru autor */}
                    <label style={{ color: '#800020', fontWeight: 'bold' }}>Author</label>
                    <input
                        type="text"
                        value={book.author}
                        onChange={e => setBook({ ...book, author: e.target.value })}
                        style={{
                            width: '100%',
                            padding: '12px',
                            borderRadius: '10px',
                            border: '1px solid #d2c0ab',
                            backgroundColor: '#fff3e6',
                            fontSize: '1rem',
                        }}
                    />

                    {/* camp pentru anul publicarii */}
                    <label style={{ color: '#800020', fontWeight: 'bold' }}>Published Year</label>
                    <input
                        type="number"
                        value={book.publishedYear}
                        onChange={e => setBook({ ...book, publishedYear: Number(e.target.value) })}
                        style={{
                            width: '100%',
                            padding: '12px',
                            borderRadius: '10px',
                            border: '1px solid #d2c0ab',
                            backgroundColor: '#fff3e6',
                            fontSize: '1rem',
                        }}
                    />

                    {/* checkbox pentru statusul de citit */}
                    <div style={{ display: 'flex', alignItems: 'center', marginTop: '10px' }}>
                        <input
                            type="checkbox"
                            checked={book.isRead}
                            onChange={e => setBook({ ...book, isRead: e.target.checked })}
                            style={{ marginRight: '10px' }}
                        />
                        <label style={{ color: '#800020', fontWeight: 'bold' }}>Read?</label>
                    </div>

                    {/* butonul de salvare */}
                    <button
                        onClick={saveBook}
                        style={{
                            width: '100%',
                            padding: '14px',
                            marginTop: '20px',
                            backgroundColor: '#831f54',
                            color: '#fff',
                            fontSize: '1.2rem',
                            borderRadius: '12px',
                            border: 'none',
                            fontWeight: 'bold',
                            cursor: 'pointer',
                        }}
                    >
                        Save
                    </button>
                </div>
            </IonContent>
        </IonPage>
    );
};

export default BookDetail;
