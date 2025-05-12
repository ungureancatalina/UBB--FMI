import React from 'react';
import { input, button } from '../styles/forgotPasswordStyles';

type Props = {
    email: string;
    onEmailChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSubmit: (e: React.FormEvent) => void;
};

const ForgotPasswordForm = ({ email, onEmailChange, onSubmit }: Props) => (
    <form onSubmit={onSubmit} className="space-y-6">
        <input
            type="email"
            value={email}
            onChange={onEmailChange}
            required
            className={input}
            placeholder="you@example.com"
        />
        <button type="submit" className={button}>
            Send Reset Instructions
        </button>
    </form>
);

export default ForgotPasswordForm;
