import mongoose, { Schema, Document } from 'mongoose';

export interface IPresence extends Document {
    user: mongoose.Types.ObjectId;
    checkIn: string;
    checkOut?: string;
}

const PresenceSchema = new Schema<IPresence>({
    user: { type: Schema.Types.ObjectId, ref: 'User', required: true },
    checkIn: { type: String, required: true },
    checkOut: { type: String }
}, { timestamps: true });

export default mongoose.model<IPresence>('Presence', PresenceSchema);
