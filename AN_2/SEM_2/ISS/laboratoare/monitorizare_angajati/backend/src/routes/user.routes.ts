import { Router, Request, Response } from 'express';
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import User from '../models/User';
import Presence from "../models/Presence";

const router = Router();
const JWT_SECRET = process.env.JWT_SECRET as string;

router.post('/login', async (req: Request, res: Response): Promise<Response> => {
    const { email, password } = req.body;
    try {
        const user = await User.findOne({ email });
        if (!user) return res.status(400).json({ message:'Invalid email address.'  });

        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) return res.status(400).json({ message: 'Incorrect password.' });

        const token = jwt.sign(
            { id: user._id, role: user.role },
            process.env.JWT_SECRET as string,
            { expiresIn: '1d' }
        );

        return res.status(200).json({
            token,
            user: {
                id: user._id,
                name: user.name,
                email: user.email,
                role: user.role,
            }
        });
    } catch (error) {
        console.error('Login error:', error)
        return res.status(500).json({ message: 'Server error during login.', error });
    }
});

router.post('/add-user', async (req: Request, res: Response): Promise<Response> => {
    const { name, email, password, role } = req.body;
    try {
        const existing = await User.findOne({ email });
        if (existing) return res.status(400).json({ message: 'User already exists.' });

        const hashedPassword = await bcrypt.hash(password, 10);
        const user = new User({ name, email, password: hashedPassword, role });
        await user.save();

        return res.status(201).json({ message: 'User successfully created.', user });
    } catch (error) {
        return res.status(500).json({ message: 'Server error while creating user.', error });
    }
});

router.get('/employees', async (req, res) => {
    try {
        const employees = await User.find({ role: 'employee' });

        const employeesWithCheckIn = await Promise.all(
            employees.map(async (emp) => {
                const presence = await Presence.findOne({user: emp._id}).sort({createdAt: -1});
                return {
                    _id: emp._id,
                    name: emp.name,
                    lastCheckIn: presence?.checkIn || null,
                };
            })
        );

        res.status(200).json(employeesWithCheckIn);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Server error' });
    }
});

export default router;
