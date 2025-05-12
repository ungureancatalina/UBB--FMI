import mongoose, { Schema, Document } from 'mongoose';

export interface IPresence extends Document {
    user: mongoose.Types.ObjectId;
    checkIn?: string | null;
    checkOut?: string | null;
}

const PresenceSchema = new Schema<IPresence>({
    user: { type: Schema.Types.ObjectId, ref: 'User', required: true },
    checkIn: { type: String, required: false },
    checkOut: { type: String, required: false }
}, { timestamps: true });

export default mongoose.model<IPresence>('Presence', PresenceSchema);
