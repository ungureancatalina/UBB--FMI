import mongoose, { Schema, Document } from 'mongoose';

export interface ITask extends Document {
    employeeId: mongoose.Types.ObjectId;
    description: string;
    status: 'new' | 'in_progress' | 'done';
}

const TaskSchema = new Schema<ITask>({
    employeeId: { type: Schema.Types.ObjectId, ref: 'User', required: true },
    description: { type: String, required: true },
    status: { type: String, enum: ['new', 'in_progress', 'done'], default: 'new' }
});

export default mongoose.model<ITask>('Task', TaskSchema);
