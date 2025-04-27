import { input, label, button, errorText } from '../styles/checkinStyles';

interface Props {
    checkOutTime: string;
    error: string;
    onTimeChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSubmit: (e: React.FormEvent) => void;
}

const CheckOutForm = ({ checkOutTime, error, onTimeChange, onSubmit }: Props) => (
    <form onSubmit={onSubmit} className="space-y-6 text-left">
        <div>
            <label className={label}>Check-out Time</label>
            <input
                type="time"
                value={checkOutTime}
                onChange={onTimeChange}
                required
                className={input}
            />
        </div>
        {error && <p className={errorText}>{error}</p>}
        <button type="submit" className={button}>Save Check-Out</button>
    </form>
);

export default CheckOutForm;
