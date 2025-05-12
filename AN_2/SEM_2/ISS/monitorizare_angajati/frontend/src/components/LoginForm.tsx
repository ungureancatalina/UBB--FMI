import React from 'react';
import { input, label, btnPrimary } from '../styles/loginStyles';

type Props = {
    email: string;
    password: string;
    onEmailChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onPasswordChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSubmit: (e: React.FormEvent) => void;
    error: string;
};

const LoginForm = ({
                       email,
                       password,
                       onEmailChange,
                       onPasswordChange,
                       onSubmit,
                       error,
                   }: Props) => (
    <form onSubmit={onSubmit} className="space-y-6 text-left">
        <div>
            <label className={label} htmlFor="email">Email</label>
            <input
                id="email"
                type="email"
                value={email}
                onChange={onEmailChange}
                className={input}
                placeholder="you@example.com"
            />
        </div>
        <div>
            <label className={label} htmlFor="password">Password</label>
            <input
                id="password"
                type="password"
                value={password}
                onChange={onPasswordChange}
                className={input}
                placeholder="••••••••"
            />
        </div>
        {error && <p className="text-red-600 text-sm font-semibold mt-4">{error}</p>}
        <button type="submit" className={btnPrimary}>Log In</button>
    </form>
);

export default LoginForm;
