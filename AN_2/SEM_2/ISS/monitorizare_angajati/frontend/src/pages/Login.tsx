import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import LoginForm from '../components/LoginForm';
import { wrapper, card, title, subtitle } from '../styles/loginStyles';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!email.includes('@')) {
            setError('Invalid email address.');
            return;
        }
        if (password.length < 6) {
            setError('Password must be at least 6 characters.');
            return;
        }
        try {
            const res = await axios.post('http://localhost:5000/api/user/login', { email, password });
            localStorage.setItem('token', res.data.token);
            localStorage.setItem('user', JSON.stringify(res.data.user));
            navigate(res.data.user.role === 'employee' ? '/checkin' : '/dashboard');
        } catch (err) {
            console.error(err);
            setError('Login failed. Please try again.');
        }
    };

    return (
        <div className={wrapper}>
            <div className={card}>
                <h1 className={title}>Welcome Back</h1>
                <p className={subtitle}>Please login to continue</p>
                <LoginForm
                    email={email}
                    password={password}
                    error={error}
                    onEmailChange={(e) => setEmail(e.target.value)}
                    onPasswordChange={(e) => setPassword(e.target.value)}
                    onSubmit={handleLogin}
                />
            </div>
        </div>
    );
};

export default Login;
