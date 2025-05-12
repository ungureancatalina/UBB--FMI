import mongoose from 'mongoose';

const connectToMongo = async () => {
    try {
        await mongoose.connect(process.env.MONGO_URI!, {
            bufferCommands: false,
            serverSelectionTimeoutMS: 10000
        });
        await mongoose.connection.asPromise();
        console.log('MongoDB connected');
    } catch (error) {
        console.error('MongoDB connection error:', error);
        if (process.env.NODE_ENV !== 'test') {
            process.exit(1);
        }
    }
};

export default connectToMongo;
