const express = require("express");
const cors = require("cors");
const jwt = require("jsonwebtoken");
const bodyParser = require("body-parser");
const db = require("./database");
const http = require("http");
const { Server } = require("socket.io");

const app = express();
const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: "*",
    },
});

app.use(cors());
app.use(bodyParser.json());

const SECRET_KEY = "secret123";

io.use((socket, next) => {
    // token poate veni în auth sau query
    const token =
        socket.handshake.auth?.token ||
        socket.handshake.query?.token ||
        null;

    if (!token) {
        return next(new Error("Missing auth token"));
    }

    jwt.verify(token.replace("Bearer ", ""), SECRET_KEY, (err, user) => {
        if (err) {
            return next(new Error("Invalid token"));
        }
        socket.user = user; // { id, username }
        next();
    });
});

io.on("connection", (socket) => {
    const user = socket.user;
    if (!user) {
        socket.disconnect();
        return;
    }

    const room = `user:${user.id}`;
    socket.join(room);
    console.log(`Socket connected for user ${user.username} in room ${room}`);

    socket.on("disconnect", () => {
        console.log(`Socket disconnected for user ${user.username}`);
    });
});


app.post("/auth/login", (req, res) => {
    const { username, password } = req.body;

    db.get(
        "SELECT * FROM users WHERE username = ? AND password = ?",
        [username, password],
        (err, user) => {
            if (err) {
                console.error("DB error:", err);
                return res.status(500).json({ message: "Database error" });
            }
            if (!user) {
                return res.status(401).json({ message: "Invalid credentials" });
            }

            const token = jwt.sign(
                { id: user.id, username: user.username },
                SECRET_KEY,
                { expiresIn: "4h" }
            );

            res.json({ token });
        }
    );
});


function authenticateToken(req, res, next) {
    const authHeader = req.headers["authorization"];
    if (!authHeader)
        return res.status(401).json({ message: "Missing Authorization header" });

    const token = authHeader.split(" ")[1];
    if (!token) return res.status(401).json({ message: "Missing token" });

    jwt.verify(token, SECRET_KEY, (err, user) => {
        if (err) return res.status(403).json({ message: "Invalid token" });
        // user: { id, username, iat, exp }
        req.user = user;
        next();
    });
}

app.get("/books", authenticateToken, (req, res) => {
    const userId = req.user.id;

    db.all(
        "SELECT * FROM books WHERE userId = ? ORDER BY id DESC",
        [userId],
        (err, rows) => {
            if (err) {
                console.error("DB error:", err);
                return res.status(500).json({ message: "Database error" });
            }

            const books = rows.map((r) => ({
                id: r.id,
                title: r.title,
                author: r.author,
                publishDate: r.publishDate,
                available: r.available === 1,
                photo: r.photo,
                lat: r.lat,
                lng: r.lng,
            }));

            res.json(books);
        }
    );
});

app.post("/books", authenticateToken, (req, res) => {
    const userId = req.user.id;
    const { title, author, publishDate, available, photo, lat, lng } = req.body;

    db.run(
        `
    INSERT INTO books (userId, title, author, publishDate, available, photo, lat, lng)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
  `,
        [
            userId,
            title,
            author,
            publishDate,
            available ? 1 : 0,
            photo || null,
            lat ?? null,
            lng ?? null,
        ],
        function (err) {
            if (err) {
                console.error("Insert error:", err);
                return res.status(500).json({ message: "Insert failed" });
            }

            const created = {
                id: this.lastID,
                title,
                author,
                publishDate,
                available: !!available,
                photo: photo || null,
                lat: lat ?? null,
                lng: lng ?? null,
            };

            io.emit("book_created", created);

            res.status(201).json(created);
        }
    );
});

app.put("/books/:id", authenticateToken, (req, res) => {
    const userId = req.user.id;
    const { id } = req.params;
    const { title, author, publishDate, available, photo, lat, lng } = req.body;

    db.get(
        "SELECT * FROM books WHERE id = ? AND userId = ?",
        [id, userId],
        (err, row) => {
            if (err) {
                console.error("DB error:", err);
                return res.status(500).json({ message: "Database error" });
            }
            if (!row) {
                return res
                    .status(404)
                    .json({ message: "Book not found or not owned by user" });
            }

            db.run(
                `
        UPDATE books
        SET title = ?, author = ?, publishDate = ?, available = ?, photo = ?, lat = ?, lng = ?
        WHERE id = ? AND userId = ?
      `,
                [
                    title,
                    author,
                    publishDate,
                    available ? 1 : 0,
                    photo || null,
                    lat ?? null,
                    lng ?? null,
                    id,
                    userId,
                ],
                function (err2) {
                    if (err2) {
                        console.error("Update error:", err2);
                        return res.status(500).json({ message: "Update failed" });
                    }

                    const updated = {
                        id: Number(id),
                        title,
                        author,
                        publishDate,
                        available: !!available,
                        photo: photo || null,
                        lat: lat ?? null,
                        lng: lng ?? null,
                    };

                    io.emit("book_updated", updated);

                    res.json(updated);
                }
            );
        }
    );
});

app.delete("/books/:id", authenticateToken, (req, res) => {
    const userId = req.user.id;
    const { id } = req.params;

    db.get(
        "SELECT * FROM books WHERE id = ? AND userId = ?",
        [id, userId],
        (err, row) => {
            if (err) {
                console.error("DB error:", err);
                return res.status(500).json({ message: "Database error" });
            }
            if (!row) {
                return res
                    .status(404)
                    .json({ message: "Book not found or not owned by user" });
            }

            db.run(
                "DELETE FROM books WHERE id = ? AND userId = ?",
                [id, userId],
                function (err2) {
                    if (err2) {
                        console.error("Delete error:", err2);
                        return res.status(500).json({ message: "Delete failed" });
                    }

                    io.emit("book_deleted", { id: Number(id) });

                    res.json({ message: "Book deleted" });
                }
            );
        }
    );
});


const PORT = 3000;
server.listen(PORT, "0.0.0.0", () => {
    console.log(`Server running on http://0.0.0.0:${PORT}`);
});
