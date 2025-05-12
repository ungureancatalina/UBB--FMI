import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { wrapper, card, title, subtitle } from '../styles/checkinStyles';
import CheckOutForm from '../components/CheckOutForm';

const CheckOut = () => {
    const [checkOutTime, setCheckOutTime] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user') || '{}');

    const isValidTime = (time: string) => {
        const [hour] = time.split(':').map(Number);
        return hour >= 12 && hour <= 23;
    };

    const handleCheckOut = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!isValidTime(checkOutTime)) {
            setError('Please select a valid check-out time between 12:00 and 23:00.');
            return;
        }

        try {
            await axios.post('http://localhost:5000/api/presence/checkout', {
                userId: user.id,
                checkOut: checkOutTime
            });
            localStorage.clear();
            navigate('/');
        } catch (err) {
            console.error(err);
            setError('Check-out failed. Please try again.');
        }
    };

    return (
        <div className={wrapper}>
            <div className={card}>
                <h1 className={title}>Employee Check-Out</h1>
                <p className={subtitle}>Please enter your check-out time</p>
                <CheckOutForm
                    checkOutTime={checkOutTime}
                    error={error}
                    onTimeChange={(e) => {
                        setCheckOutTime(e.target.value);
                        setError('');
                    }}
                    onSubmit={handleCheckOut}
                />
            </div>
        </div>
    );
};

export default CheckOut;