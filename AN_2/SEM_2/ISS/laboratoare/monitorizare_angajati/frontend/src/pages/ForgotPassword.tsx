import React, { useState } from 'react';
import ForgotPasswordForm from '../components/ForgotPasswordForm';
import {
    wrapper,
    card,
    title,
    subtitle,
    successText
} from '../styles/forgotPasswordStyles';

const ForgotPassword = () => {
    const [email, setEmail] = useState('');
    const [submitted, setSubmitted] = useState(false);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        setSubmitted(true);
    };

    return (
        <div className={wrapper}>
            <div className={card}>
                <h1 className={title}>Forgot Password</h1>
                <p className={subtitle}>
                    Enter your email and we’ll send you instructions.
                </p>

                {submitted ? (
                    <p className={successText}>
                        If this email exists, we sent instructions to reset your password.
                    </p>
                ) : (
                    <ForgotPasswordForm
                        email={email}
                        onEmailChange={(e) => setEmail(e.target.value)}
                        onSubmit={handleSubmit}
                    />
                )}
            </div>
        </div>
    );
};

export default ForgotPassword;
