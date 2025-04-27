import React from 'react';
import { input, label, errorText, button } from '../styles/checkinStyles';

type Props = {
    checkInTime: string;
    error: string;
    onTimeChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSubmit: (e: React.FormEvent) => void;
};

const CheckInForm = ({ checkInTime, error, onTimeChange, onSubmit }: Props) => (
    <form onSubmit={onSubmit} className="space-y-6 text-left">
        <div>
            <label className={label}>Check-in Time</label>
            <input
                type="time"
                value={checkInTime}
                onChange={onTimeChange}
                required
                className={input}
            />
        </div>
        {error && <p className={errorText}>{error}</p>}
        <button type="submit" className={button}>Save Check-In</button>
    </form>
);

export default CheckInForm;
