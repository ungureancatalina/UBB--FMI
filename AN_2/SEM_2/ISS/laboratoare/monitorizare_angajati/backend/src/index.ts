import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import connectToMongo from './db';
import userRoutes from './routes/user.routes';
import presenceRoutes from './routes/presence.routes';
import taskRoutes from './routes/task.routes';

dotenv.config();

const app = express();
const PORT = process.env.PORT || 5000;

app.use(cors());
app.use(express.json());

app.use('/api/user', userRoutes);
app.use('/api/presence', presenceRoutes);
app.use('/api/task', taskRoutes);

connectToMongo().then(() => {
    app.listen(PORT, () => {
        console.log(`Server running on port ${PORT}`);
    });
});
