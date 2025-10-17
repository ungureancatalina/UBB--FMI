import { Router } from 'express';
import Logger from '../logger';

const router = Router();

// POST / - primeste un mesaj de la frontend
router.post('/', (req, res) => {
    const { message } = req.body; // ia mesajul trimis de frontend

    // daca nu exista mesaj, raspunde cu cod HTTP 400 si mesaj JSON
    if (!message) return res.status(400).json({ error: 'No message provided' });

    Logger.log(`[FRONTEND] ${message}`);

    // trimite inapoi un raspuns cu cod HTTP 200 si JSON de succes
    res.status(200).json({ success: true });
});

export default router;
