## About the Project

**Book Tracker** is a cross–platform mobile application built in two parallel implementations: Ionic + TypeScript (cross–platform web-based mobile app) and Android Kotlin with Jetpack Compose (native Android app). Both implementations provide the same core functionality for managing a personal book collection, functioning in both online and offline modes, supporting local storage, image capture, map integration, and smooth UI animations. This project demonstrates practical mobile development concepts such as offline-first architecture, state management, camera integration, API synchronization, and animated interfaces.

---

## 🛠 Technologies Used

| Tool                              | Purpose                                         |
|-----------------------------------|-------------------------------------------------|
| Ionic + TypeScript                | Hybrid mobile UI, cross–platform implementation |
| Android Kotlin + Jetpack Compose  | Native Android UI, declarative mobile interface |
| SQLite                            | Embedded relational database                    |

---

## Features

- User login/logout
- View list of books
- Add/Edit/Delete books
- Search & filter
- Online & offline support
- Local persistence
- Camera photo capture for covers
- Map integration
- Smooth animations across the app
  
---

## Project Structure

```
book_tracker/
├── src/
│   ├── pages/                 → Screens (Home, AddBook, Details, Map, Camera)
│   ├── utils/                 → Logger
│   ├── services/              → API calls, local storage handler
│   └── theme/                 → Global styling
└── App.tsx / main.tsx         → Entry files

```

---

## This is how the app looks like (for example in Ionic):

<img width="694" height="1515" alt="Screenshot 2025-11-24 120406" src="https://github.com/user-attachments/assets/91c15bd8-ac4b-447d-a71c-9a3d868d6fcb" />
<img width="696" height="1525" alt="Screenshot 2025-11-24 120439" src="https://github.com/user-attachments/assets/4428319b-b021-4d8c-83ce-c9ca329f4903" />
<img width="691" height="1510" alt="Screenshot 2025-11-24 120447" src="https://github.com/user-attachments/assets/82b1e405-fbd9-4885-8f37-487344be8cf6" />


