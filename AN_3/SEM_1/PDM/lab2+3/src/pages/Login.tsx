import React, { useState } from 'react';
import { IonPage, IonContent } from '@ionic/react';
import { login } from '../services/auth';
import { logger } from '../utils/Logger';

const Login: React.FC = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = async (): Promise<void> => {
        setError('');
        try {
            await login(username, password);
            window.location.href = '/books';
        } catch (err) {
            const msg = (err as Error)?.message || 'Login failed';
            setError(msg);
            logger.add(`Login failed: ${msg}`);
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
                    minHeight: '100vh',
                    padding: '20px',
                }}
            >
                <div
                    style={{
                        width: '100%',
                        maxWidth: '420px',
                        background: '#ffe7e7',
                        borderRadius: '20px',
                        padding: '25px',
                        boxShadow: '0 6px 20px rgba(0,0,0,0.2)',
                    }}
                >
                    <h2 style={{ textAlign: 'center', color: '#800020', marginBottom: '20px' }}>Login</h2>

                    <label style={{ color: '#800020', fontWeight: 'bold' }}>Username</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        style={{
                            width: '100%',
                            padding: '12px',
                            borderRadius: '10px',
                            border: '1px solid #d2c0ab',
                            marginBottom: '15px',
                            backgroundColor: '#fff3e6',
                        }}
                    />

                    <label style={{ color: '#800020', fontWeight: 'bold' }}>Password</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={{
                            width: '100%',
                            padding: '12px',
                            borderRadius: '10px',
                            border: '1px solid #d2c0ab',
                            backgroundColor: '#fff3e6',
                        }}
                    />

                    {error && (
                        <p style={{ color: '#cb2f3e', marginTop: '10px', fontWeight: 'bold' }}>{error}</p>
                    )}

                    <button
                        onClick={handleLogin}
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
                        Login
                    </button>
                </div>
            </IonContent>
        </IonPage>
    );
};

export default Login;
