import React, { useEffect, useState } from 'react';
import { IonPage, IonContent } from '@ionic/react';
import { useParams, useHistory } from 'react-router-dom';
import api from '../services/api';
import { logger } from '../utils/Logger';
import { createBook, updateBook } from '../services/sync';
import {Camera, CameraResultType, CameraSource} from '@capacitor/camera';
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
    id?: number;
    title: string;
    author: string;
    isRead: boolean;
    addedAt: string;
    photo?: string;
    lat?: number;
    lng?: number;
}

const CACHE_KEY = 'cached_books';

const BookDetail: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const history = useHistory();
    const [setLoading] = useState(false);
    const [showMap, setShowMap] = useState(false);

    const [book, setBook] = useState<Book>({
        title: '',
        author: '',
        isRead: false,
        addedAt: new Date().toISOString(),
    });

    useEffect(() => {
        const fetchBook = async () => {
            if (id === 'new') return;
            setLoading(true);
            try {
                logger.add(`Fetching book id=${id}`);
                const res = await api.get<Book[]>('/books');
                const found = res.data.find((b) => b.id === Number(id));

                if (found) {
                    setBook(found);
                    logger.add(`Book loaded: ${found.title}`);
                } else {
                    const cached: Book[] = JSON.parse(localStorage.getItem(CACHE_KEY) || '[]');
                    const offlineBook = cached.find((b) => b.id === Number(id));
                    if (offlineBook) setBook(offlineBook);
                }
            } catch {
                const cached: Book[] = JSON.parse(localStorage.getItem(CACHE_KEY) || '[]');
                const offlineBook = cached.find((b) => b.id === Number(id));
                if (offlineBook) setBook(offlineBook);
                logger.add('Offline mode → loaded book from cache');
            } finally {
                setLoading(false);
            }
        };

        fetchBook();
    }, [id]);

    const saveBook = async () => {
        if (!book.title.trim()) return alert('Enter title');
        if (!book.author.trim()) return alert('Enter author');

        const payload = {
            title: book.title,
            author: book.author,
            isRead: book.isRead,
            addedAt: new Date().toISOString(),
            photo: book.photo,
            lat: book.lat,
            lng: book.lng,
        };

        try {
            let saved: Book;
            if (id === 'new') {
                saved = await createBook(payload);
                logger.add('Book created');
            } else {
                saved = await updateBook(Number(id), payload);
                logger.add('Book updated');
            }

            const cached: Book[] = JSON.parse(localStorage.getItem(CACHE_KEY) || '[]');
            const updated =
                id === 'new'
                    ? [saved, ...cached]
                    : cached.map((x) => (x.id === Number(id) ? saved : x));

            localStorage.setItem(CACHE_KEY, JSON.stringify(updated));
            window.dispatchEvent(new Event('books-changed'));
            history.push('/books');
        } catch (err) {
            console.error(err);
            alert('Failed to save');
        }
    };

    const handleUsePhoto = async () => {
        try {
            if ((window as any).Capacitor?.isNativePlatform()) {
                const { Camera, CameraResultType, CameraSource } = await import('@capacitor/camera');
                const photo = await Camera.getPhoto({
                    quality: 80,
                    allowEditing: false,
                    resultType: CameraResultType.Base64,
                    source: CameraSource.Prompt,
                });
                if (photo.base64String) {
                    const dataUrl = `data:image/jpeg;base64,${photo.base64String}`;
                    setBook((prev) => ({ ...prev, photo: dataUrl }));
                }
                return;
            }

            const stream = await navigator.mediaDevices.getUserMedia({ video: true });
            const video = document.createElement('video');
            video.srcObject = stream;
            video.autoplay = true;
            video.style.width = '100%';
            video.style.borderRadius = '12px';
            video.style.marginTop = '10px';

            const container = document.createElement('div');
            container.style.position = 'fixed';
            container.style.top = '0';
            container.style.left = '0';
            container.style.width = '100vw';
            container.style.height = '100vh';
            container.style.background = 'rgba(0,0,0,0.8)';
            container.style.display = 'flex';
            container.style.justifyContent = 'center';
            container.style.alignItems = 'center';
            container.style.zIndex = '9999';
            container.appendChild(video);

            const captureBtn = document.createElement('button');
            captureBtn.innerText = '📸 Capture';
            captureBtn.style.position = 'absolute';
            captureBtn.style.bottom = '20px';
            captureBtn.style.padding = '12px 20px';
            captureBtn.style.background = '#3e55ce';
            captureBtn.style.color = '#fff';
            captureBtn.style.fontSize = '1rem';
            captureBtn.style.borderRadius = '12px';
            captureBtn.style.border = 'none';
            captureBtn.style.cursor = 'pointer';
            container.appendChild(captureBtn);

            document.body.appendChild(container);

            captureBtn.onclick = () => {
                const canvas = document.createElement('canvas');
                canvas.width = video.videoWidth;
                canvas.height = video.videoHeight;
                const ctx = canvas.getContext('2d');
                if (ctx) ctx.drawImage(video, 0, 0, canvas.width, canvas.height);

                const dataUrl = canvas.toDataURL('image/jpeg');
                setBook((prev) => ({ ...prev, photo: dataUrl }));

                stream.getTracks().forEach((t) => t.stop());
                document.body.removeChild(container);
                alert(' Photo captured successfully!');
            };
        } catch (err) {
            console.error('Camera error:', err);
            alert('Could not access camera — check permissions.');
        }
    };

    const handleUploadFromDevice = async (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        if (!file) return;

        try {
            const reader = new FileReader();
            reader.onload = async () => {
                const base64String = (reader.result as string).split(',')[1];
                const fileName = `upload_${Date.now()}_${file.name}`;

                const formData = new FormData();
                formData.append('photo', file, fileName);

                const res = await api.post('/upload', formData, {
                    headers: { 'Content-Type': 'multipart/form-data' },
                });

                if (res.data?.photo) {
                    setBook((prev) => ({ ...prev, photo: res.data.photo }));
                    logger.add(`Uploaded: ${res.data.photo}`);
                } else {
                    alert('Upload failed — no response path');
                }
            };

            reader.readAsDataURL(file);
        } catch (err) {
            console.error('Upload error:', err);
            alert('Could not upload image');
        }
    };



    return (
        <IonPage>
            <IonContent
                style={{
                    '--background': '#705357',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    padding: '20px',
                    minHeight: '100vh',
                }}
            >
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
                    <h2
                        style={{
                            textAlign: 'center',
                            color: '#800020',
                            fontSize: '2rem',
                            marginBottom: '15px',
                        }}
                    >
                        {id === 'new' ? 'Add New Book' : 'Edit Book'}
                    </h2>

                    <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
                        <label
                            style={{
                                color: '#831f54',
                                fontWeight: 600,
                                fontSize: '1rem',
                            }}
                        >
                            Title
                        </label>
                        <input
                            type="text"
                            value={book.title}
                            onChange={(e) => setBook({ ...book, title: e.target.value })}
                            placeholder="Enter book title..."
                            style={{
                                padding: '12px 15px',
                                borderRadius: '12px',
                                border: '2px solid #d8a4b0',
                                backgroundColor: '#fff',
                                color: '#831f54',
                                fontSize: '1rem',
                                fontWeight: 500,
                                outline: 'none',
                                boxShadow: 'inset 0 2px 4px rgba(0,0,0,0.05)',
                                transition: '0.2s',
                            }}
                            onFocus={(e) =>
                                (e.currentTarget.style.borderColor = '#831f54')
                            }
                            onBlur={(e) =>
                                (e.currentTarget.style.borderColor = '#d8a4b0')
                            }
                        />
                    </div>

                    <div style={{ display: 'flex', flexDirection: 'column', gap: '6px' }}>
                        <label
                            style={{
                                color: '#831f54',
                                fontWeight: 600,
                                fontSize: '1rem',
                            }}
                        >
                            Author
                        </label>
                        <input
                            type="text"
                            value={book.author}
                            onChange={(e) => setBook({ ...book, author: e.target.value })}
                            placeholder="Enter author name..."
                            style={{
                                padding: '12px 15px',
                                borderRadius: '12px',
                                border: '2px solid #d8a4b0',
                                backgroundColor: '#fff',
                                color: '#831f54',
                                fontSize: '1rem',
                                fontWeight: 500,
                                outline: 'none',
                                boxShadow: 'inset 0 2px 4px rgba(0,0,0,0.05)',
                                transition: '0.2s',
                            }}
                            onFocus={(e) =>
                                (e.currentTarget.style.borderColor = '#831f54')
                            }
                            onBlur={(e) =>
                                (e.currentTarget.style.borderColor = '#d8a4b0')
                            }
                        />
                    </div>

                    <div style={{ display: 'flex', alignItems: 'center', marginTop: '10px' }}>
                        <input
                            type="checkbox"
                            checked={book.isRead}
                            onChange={(e) => setBook({ ...book, isRead: e.target.checked })}
                        />
                        <label style={{ marginLeft: '8px', color: '#800020', fontWeight: 'bold' }}>
                            Read?
                        </label>
                    </div>

                    <button onClick={handleUsePhoto} style={btn('#3e55ce')}>
                        Take Photo
                    </button>

                    <label
                        style={{
                            ...btn('#8e44ad'),
                            textAlign: 'center',
                            display: 'block',
                            marginTop: '10px',
                        }}
                    >
                        Upload from Device
                        <input
                            type="file"
                            accept="image/*"
                            onChange={handleUploadFromDevice}
                            style={{ display: 'none' }}
                        />
                    </label>

                    {book.photo && (
                        <img
                            src={
                                book.photo.startsWith('data:') ||
                                book.photo.startsWith('file:')
                                    ? book.photo
                                    : book.photo.startsWith('http')
                                        ? book.photo
                                        : `http://localhost:4000${book.photo}`
                            }
                            alt="Book"
                            style={{
                                width: '100%',
                                maxHeight: '240px',
                                objectFit: 'cover',
                                borderRadius: '12px',
                                marginTop: '10px',
                            }}
                        />
                    )}

                    <button
                        onClick={() => setShowMap(true)}
                        style={btn('#3e55ce')}
                    >
                        Select location on map
                    </button>

                    <button onClick={saveBook} style={btn('#831f54')}>
                        Save
                    </button>
                </div>
            </IonContent>
            {showMap && (
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
                        }}
                    >
                        <MapContainer
                            center={[book.lat || 45.9432, book.lng || 24.9668]}
                            zoom={7}
                            style={{ width: '100%', height: '100%' }}
                        >
                            <TileLayer
                                attribution="&copy; OpenStreetMap contributors"
                                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                            />

                            <MapClickHandler
                                onSelect={(lat, lng) => {
                                    setBook((prev) => ({ ...prev, lat, lng }));
                                    alert(` Selected location:\nLat: ${lat}\nLng: ${lng}`);
                                }}
                            />

                            {book.lat && book.lng && (
                                <Marker
                                    position={[book.lat, book.lng]}
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
            )}
        </IonPage>
    );
};

const btn = (color: string) => ({
    width: '100%',
    padding: '12px',
    borderRadius: '12px',
    backgroundColor: color,
    color: '#fff',
    fontWeight: 'bold',
    border: 'none',
    cursor: 'pointer',
});

export default BookDetail;
