import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function App() {
    const [curse, setCurse] = useState([]);
    const [nume, setNume] = useState('');
    const [capacitateMinima, setCapMin] = useState('');
    const [capacitateMaxima, setCapMax] = useState('');
    const [editId, setEditId] = useState(null);

    const apiBase = 'http://localhost:8000/api/curse';
    const token = localStorage.getItem('token');
    const navigate = useNavigate();

    useEffect(() => {
        if (!token) navigate('/login');
        else loadCurse();
    }, []);

    const loadCurse = async () => {
        try {
            const res = await fetch(apiBase, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            console.log("Status fetch curse:", res.status);

            if (!res.ok) {
                console.error("Fetch curse failed with status:", res.status);
                throw new Error(`Eroare HTTP: ${res.status}`);
            }

            const data = await res.json();
            console.log("Date curse:", data);
            setCurse(data);

        } catch (err) {
            console.error("Eroare la incarcarea curselor:", err.message);
        }
    };



    const handleSubmit = async (e) => {
        e.preventDefault();
        const cursa = {
            nume,
            capacitateMinima: +capacitateMinima,
            capacitateMaxima: +capacitateMaxima
        };

        try {
            const url = editId ? `${apiBase}/${editId}` : apiBase;
            const method = editId ? 'PUT' : 'POST';

            const res = await fetch(url, {
                method,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(cursa)
            });

            if (!res.ok) throw new Error(`Eroare HTTP: ${res.status}`);

            setNume('');
            setCapMin('');
            setCapMax('');
            setEditId(null);
            loadCurse();
        } catch (err) {
            console.error("Eroare la salvare:", err.message);
        }
    };

    const handleDelete = async (id) => {
        try {
            const res = await fetch(`${apiBase}/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (!res.ok) throw new Error(`Eroare HTTP: ${res.status}`);
            loadCurse();
        } catch (err) {
            console.error("Eroare la stergere:", err.message);
        }
    };

    const handleEdit = (cursa) => {
        setNume(cursa.nume);
        setCapMin(cursa.capacitateMinima);
        setCapMax(cursa.capacitateMaxima);
        setEditId(cursa.id);
    };

    const logout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    return (
        <div className="min-h-screen bg-blue-50 flex items-center justify-center p-6">
            <div className="bg-white shadow-xl rounded-xl p-8 w-full max-w-2xl border border-blue-200">
                <div className="flex justify-between items-center mb-6">
                    <h1 className="text-4xl font-extrabold text-sky-600">Administrare Curse</h1>
                    <button onClick={logout} className="text-red-500 hover:underline">Logout</button>
                </div>

                <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-10">
                    <input value={nume} onChange={e => setNume(e.target.value)} placeholder="Nume cursa"
                           className="border border-sky-300 rounded p-2" required />
                    <input type="number" value={capacitateMinima} onChange={e => setCapMin(e.target.value)}
                           placeholder="Capacitate minima" className="border border-sky-300 rounded p-2" required />
                    <input type="number" value={capacitateMaxima} onChange={e => setCapMax(e.target.value)}
                           placeholder="Capacitate maxima" className="border border-sky-300 rounded p-2" required />
                    <button type="submit" className="bg-sky-500 text-white px-4 py-2 rounded hover:bg-sky-600">
                        {editId ? 'Modifica cursa' : 'Adauga cursa'}
                    </button>
                </form>

                <ul className="space-y-4">
                    {curse.map(c => (
                        <li key={c.id} className="border rounded-lg p-4 flex flex-col md:flex-row md:justify-between md:items-center bg-blue-100 shadow-md">
                            <div className="mb-2 md:mb-0">
                                <strong className="text-lg text-blue-900 font-semibold">{c.nume}</strong>
                                <span className="ml-2 text-sm text-blue-700">{c.capacitateMinima} / {c.capacitateMaxima}</span>
                            </div>
                            <div className="space-x-2">
                                <button onClick={() => handleEdit(c)} className="bg-blue-300 text-white px-3 py-1 rounded hover:bg-blue-400">Edit</button>
                                <button onClick={() => handleDelete(c.id)} className="bg-red-400 text-white px-3 py-1 rounded hover:bg-red-500">Delete</button>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}
