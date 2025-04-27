import React from 'react';
import { input, label, btnPrimary } from '../styles/loginStyles';

type Props = {
    email: string;
    password: string;
    onEmailChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onPasswordChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSubmit: (e: React.FormEvent) => void;
};

const LoginForm = ({
                       email,
                       password,
                       onEmailChange,
                       onPasswordChange,
                       onSubmit,
                   }: Props) => (
    <form onSubmit={onSubmit} className="space-y-6 text-left">
        <div>
            <label className={label}>Email</label>
            <input
                type="email"
                value={email}
                onChange={onEmailChange}
                className={input}
                placeholder="you@example.com"
            />
        </div>
        <div>
            <label className={label}>Password</label>
            <input
                type="password"
                value={password}
                onChange={onPasswordChange}
                className={input}
                placeholder="••••••••"
            />
        </div>
        <button type="submit" className={btnPrimary}>Log In</button>
    </form>
);

export default LoginForm;
