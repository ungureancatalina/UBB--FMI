import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import CheckInForm from '../components/CheckInForm';
import { wrapper, card, title, subtitle } from '../styles/checkinStyles';

const CheckIn = () => {
    const [checkInTime, setCheckInTime] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user') || '{}');

    const isValidTime = (time: string) => {
        const [hour] = time.split(':').map(Number);
        return hour >= 6 && hour <= 12;
    };

    const handleCheckIn = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!isValidTime(checkInTime)) {
            setError('Please select a valid check-in time between 06:00 and 12:00.');
            return;
        }

        try {
            await axios.post('http://localhost:5000/api/presence/checkin', {
                userId: user.id,
                checkIn: checkInTime
            });
            navigate('/tasks');
        } catch (err) {
            console.error(err);
            setError('Check-in failed. Please try again.');
        }
    };

    return (
        <div className={wrapper}>
            <div className={card}>
                <h1 className={title}>Employee Check-In</h1>
                <p className={subtitle}>Please enter your check-in time</p>
                <CheckInForm
                    checkInTime={checkInTime}
                    error={error}
                    onTimeChange={(e) => {
                        setCheckInTime(e.target.value);
                        setError('');
                    }}
                    onSubmit={handleCheckIn}
                />
            </div>
        </div>
    );
};

export default CheckIn;
