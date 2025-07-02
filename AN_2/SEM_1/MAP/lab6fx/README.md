## About the Project


**Social Network** is a Java-based desktop application dthat enables users to authenticate, search and add friends, and manage friend requests. 
This project was developed for educational purposes and demonstrates the use of layered architecture (MVC) in a Java desktop environment.
The application uses a **PostgreSQL database** for persistent storage and supports user authentication.

---

## Technologies Used

| Tool             | Purpose                                |
|------------------|----------------------------------------|
| Java             | Core programming language              |
| JavaFX           | Graphical user interface               |
| PostgreSQL       | Relational database for data storage   |
| JDBC             | Database connectivity                  |

---

## Features

- User login system
- Search users by first name and last name
- Send and receive friend requests
- Accept / reject friend requests
- View and manage notifications
- Chat with friends
- Simple, modern UI with custom styling
  
---

## Project Structure

```
social_network/
├── controller/        -> JavaFX controllers for UI interaction
├── domain/            -> Task and user data classes
├── service/           -> Business logic
├── repository/        -> JDBC access to PostgreSQL
├── event/             -> Helper methods (e.g. notifications for friend requests)
├── resources/         -> FXML layouts, icons
└── Main.java          -> Entry point
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
git clone https://github.com/ungureancatalina/socia](https://github.com/ungureancatalina/UBB--FMI/tree/main/AN_2/SEM_1/MAP/lab6fx
cd social_network
```

2. Make sure JavaFX is configured in your IDE:
   - Add it to libraries/dependencies
   - Set VM options:
     ```
     --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
     ```

3. Run `Main.java` to launch the application.
4. This is how the app looks like:



![Screenshot 2025-07-02 201635](https://github.com/user-attachments/assets/00f3a19d-60f7-4545-b2f5-8e0e944b414b)
![Screenshot 2025-07-02 201555](https://github.com/user-attachments/assets/51486ea2-8b35-4e54-b315-e845823a007f)
![Screenshot 2025-07-02 201522](https://github.com/user-attachments/assets/965b2a93-143a-4df6-b914-70096e4fab01)
![Screenshot 2025-07-02 201439](https://github.com/user-attachments/assets/687a2583-ea35-4726-b994-5a42fbfe025c)
![Screenshot 2025-07-02 201400](https://github.com/user-attachments/assets/93360a4a-5d09-40d7-b427-3f7c9a44b93d)

