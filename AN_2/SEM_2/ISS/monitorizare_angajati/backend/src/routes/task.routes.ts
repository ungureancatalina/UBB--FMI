import { Router, Request, Response } from 'express';
import Task from '../models/Task';
import User from '../models/User';
import { IUser } from '../models/User';
import { sendNotification } from '../index';

const router = Router();

router.post('/', async (req: Request, res: Response) => {
    const { employeeId, description } = req.body;

    sendNotification(employeeId, `New task assigned: "${description}"`);

    if (!description || !employeeId) {
        return res.status(400).json({ message: 'Missing description or employee ID.' });
    }
    try {
        const task = new Task({ employeeId, description });
        await task.save();
        res.status(201).json(task);
    } catch (err) {
        res.status(500).json({ message: 'Task creation failed', err });
    }
});

router.get('/employee/:id', async (req: Request, res: Response) => {
    try {
        const tasks = await Task.find({ employeeId: req.params.id }).populate('employeeId', 'name');
        const formatted = tasks.map(task => ({
            _id: task._id,
            description: task.description,
            status: task.status,
            employeeName: (task.employeeId as any).name,
        }));
        res.json(formatted);
    } catch (err) {
        res.status(500).json({ message: 'Failed to fetch tasks', err });
    }
});

router.get('/all', async (req, res) => {
    try {
        const tasks = await Task.find({}).populate('employeeId', 'name');
        const formatted = tasks.map((task) => ({
            _id: task._id,
            description: task.description,
            status: task.status,
            employeeName: (task.employeeId as any).name,
        }));
        res.json(formatted);
    } catch (err) {
        res.status(500).json({ message: 'Failed to fetch all tasks', err });
    }
});


router.put('/:id', async (req: Request, res: Response) => {
    const { description, employeeId } = req.body;
    sendNotification(employeeId, `Task updated: "${description}"`);

    if (!description || !employeeId) {
        return res.status(400).json({ message: 'Missing description or employee ID.' });
    }
    try {
        const task = await Task.findByIdAndUpdate(
            req.params.id,
            { description, employeeId },
            { new: true }
        );
        res.json(task);
    } catch (err) {
        res.status(500).json({ message: 'Failed to update task', err });
    }
});

router.delete('/:id', async (req: Request, res: Response) => {
    const task = await Task.findById(req.params.id);
    if (task) {
        sendNotification(task.employeeId.toString(), `Task removed from your list`);
    }

    try {
        await Task.findByIdAndDelete(req.params.id);
        res.status(200).json({ message: 'Task deleted successfully' });
    } catch (err) {
        res.status(500).json({ message: 'Failed to delete task', err });
    }
});


router.put('/:id/complete', async (req: Request, res: Response) => {
    const task = await Task.findByIdAndUpdate(
        req.params.id,
        { status: 'done' },
        { new: true }
    ).populate<{ employeeId: IUser }>('employeeId', 'name');

    sendNotification("employer", `${task?.employeeId.name} marked task as "${task?.status}"`);

    try {
        const task = await Task.findByIdAndUpdate(req.params.id, { status: 'done' }, { new: true });
        res.status(200).json(task);
    } catch (err) {
        res.status(500).json({ message: 'Failed to mark task as done', err });
    }
});


router.put('/:id/in-progress', async (req, res) => {
    const task = await Task.findByIdAndUpdate(
        req.params.id,
        { status: 'in_progress' },
        { new: true }
    ).populate<{ employeeId: IUser }>('employeeId', 'name');
    sendNotification("employer", `${task?.employeeId.name} marked task as "${task?.status}"`);

    try {
        const task = await Task.findByIdAndUpdate(
            req.params.id,
            { status: 'in_progress' },
            { new: true }
        );
        res.status(200).json(task);
    } catch (err) {
        res.status(500).json({ message: 'Failed to update task status', err });
    }
});


export default router;