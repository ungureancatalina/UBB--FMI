## About the Project


**Moto Contest** is a Java-based client-server desktop application for managing motocross races and participants. Users can authenticate, search for races and participants, and assign participants to teams. This project uses JavaFX for the GUI, SQLite for persistent storage, and Java Sockets for communication between the client and server.

This application demonstrates the use of **MVC architecture**, **JavaFX**, **SQLite**, and **multi-threaded socket programming**.

---

## 🛠 Technologies Used

| Tool             | Purpose                                   |
|------------------|-------------------------------------------|
| Java             | Core language for client and server       |
| JavaFX           | UI framework                              |
| SQLite           | Embedded relational database              |
| JDBC             | Database connectivity                     |
| Java Sockets     | Client-server communication               |

---

## Features

- User login/logout
- View list of races
- Search participants by name
- Add participants to teams
- View team assignments
- Persistent data in SQLite
- Clean, responsive GUI with JavaFX
  
---

## Project Structure

```
moto_contest_java/
├── client/               → JavaFX client code
│   ├── gui/              → JavaFX controllers and scenes
│   └── StartClient.java  → Client entry point
├── server/               → Java socket server
│   └── StartServer.java  → Server entry point
├── model/                → Shared domain entities (Race, Team, Participant, User)
├── persistence/          → Helpers for database access (SQLite, JDBC)
├── service/              → Business logic and functionalities
├── resources/            → FXML layouts
└── database/             → SQLite DB file and schema
```

---

## How to Run

### Requirements

- Java 17+
- JavaFX SDK (set in your IDE)
- An IDE like IntelliJ IDEA or VS Code
- PostgreSQL installed and running

### Steps

1. Clone the repository:
```bash
git clone https://github.com/ungureancatalina/UBB--FMI/new/main/AN_2/SEM_2/MPP
cd moto_contest_java
```

2. Make sure JavaFX is configured in your IDE:
   - Add it to libraries/dependencies
   - Set VM options:
     ```
     --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
     ```

3. Run `Main.java` to launch the application.
4. This is how the app looks like:



![Screenshot 2025-07-02 203046](https://github.com/user-attachments/assets/573c0a5b-cdc4-46f1-832f-ed46adb2222b)
![Screenshot 2025-07-02 203122](https://github.com/user-attachments/assets/47bf49db-4682-448a-9368-1252b02cbc83)
![Screenshot 2025-07-02 203204](https://github.com/user-attachments/assets/5096859c-6280-4ee2-a32d-8d976fe88e06)

