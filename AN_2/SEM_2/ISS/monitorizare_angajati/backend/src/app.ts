import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import userRoutes from './routes/user.routes';
import presenceRoutes from './routes/presence.routes';
import taskRoutes from './routes/task.routes';
import notifyRoutes from './routes/notify.routes';

dotenv.config({ path: process.env.NODE_ENV === 'test' ? '.env.test' : '.env' });

const app = express();
app.use(cors());
app.use(express.json());

app.use('/api/user', userRoutes);
app.use('/api/presence', presenceRoutes);
app.use('/api/task', taskRoutes);
app.use('/api/notify', notifyRoutes);

export default app;
