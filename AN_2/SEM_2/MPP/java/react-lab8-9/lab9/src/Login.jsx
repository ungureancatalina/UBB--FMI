import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Login() {
    const [nume, setNume] = useState('');
    const [parola, setParola] = useState('');
    const [eroare, setEroare] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const res = await fetch('http://localhost:8000/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ nume, parola })
            });

            if (!res.ok) throw new Error("Date incorecte!");

            const token = await res.text();
            localStorage.setItem('token', token);
            navigate('/');
        } catch (err) {
            setEroare(err.message);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-blue-100">
            <form onSubmit={handleLogin} className="bg-white p-8 rounded-lg shadow-md w-full max-w-sm border border-blue-200">
                <h2 className="text-2xl font-bold text-center mb-4 text-sky-600">Autentificare</h2>
                {eroare && <p className="text-red-500 text-center mb-2">{eroare}</p>}
                <input
                    type="text"
                    value={nume}
                    onChange={e => setNume(e.target.value)}
                    placeholder="Nume"
                    className="w-full p-2 mb-4 border border-blue-300 rounded"
                    required
                />
                <input
                    type="password"
                    value={parola}
                    onChange={e => setParola(e.target.value)}
                    placeholder="Parola"
                    className="w-full p-2 mb-4 border border-blue-300 rounded"
                    required
                />
                <button type="submit" className="w-full bg-sky-500 text-white p-2 rounded hover:bg-sky-600">
                    Login
                </button>
            </form>
        </div>
    );
}
