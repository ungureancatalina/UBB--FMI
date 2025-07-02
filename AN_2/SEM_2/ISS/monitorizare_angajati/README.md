## About the Project

Employee Monitoring is a modern web application designed to manage employee activities within a company. Built with the MERN stack (MongoDB, Express, React, Node.js), 
the app enables secure user authentication, tracks work hours through check-in and check-out features, and facilitates comprehensive task management.

---

## Technologies Used

| Tool                       | Purpose                                |
|----------------------------|----------------------------------------|
| React                      | Frontend with dynamic user interface   |
| Tailwind CSS               | Fast styling with responsive design    | 
| Typescript                 | Strong typing for safer code           |
| Node.js + Express          | Backend REST API                       |
| MongoDB                    | NoSQL database for data storage        |          
| Mongoose                   | ODM for MongoDB                        |
| Vite	                     | Fast frontend bundler                  |


---

## Features

- Authentication: Secure employee login and logout
- Check-in / Check-out: Track employee work hours
- Task Management: Add new tasks / View task list / Edit and delete tasks / Mark tasks as completed
- Activity History: View work hours and completed tasks
- Modern, responsive UI built with Tailwind CSS
  
---

## Project Structure

```
social_network/
├── backend/                   # Backend API (Node.js + Express)
│   ├── models/                # Domain models / database schemas
│   ├── routes/                # API route definitions
│   └── tests/                 # Backend tests
│
├── frontend/                  # Frontend React app
│   ├── components/            # Reusable UI components
│   ├── hooks/                 # Custom React hooks (e.g., notifications)
│   ├── pages/                 # React pages / views
│   ├── styles/                # CSS / Tailwind / styling files
│   ├── types/                 # TypeScript type definitions
│   ├── utils/                 # Utility/helper functions
│   └── tests/                 # Frontend tests

```

---

## How to Run

### Requirements

- Node.js 18+ installed
- MongoDB installed locally or MongoDB Atlas account
- Code editor (e.g., VS Code)

### Steps

1. Clone the repository:
```bash
git clone https://github.com/ungureancatalina/UBB--FMI/new/main/AN_2/SEM_2/ISS/monitorizare_angajati
cd employee_tracker
```

2. Install and start the backend server:
   - cd server
   - npm install
   - npm run dev

3. Install and start the React frontend:
   - cd ../client
   - npm install
   - npm run dev

4. This is how the app looks like:


![Screenshot 2025-07-02 205343](https://github.com/user-attachments/assets/3ff76e20-376d-41d2-b7d5-3346f9270264)
![Screenshot 2025-07-02 205712](https://github.com/user-attachments/assets/c5f066e1-de67-4762-a4de-b522985a73bb)
![Screenshot 2025-07-02 205743](https://github.com/user-attachments/assets/d8a22d68-d31d-44dc-b98c-378738c94280)
![Screenshot 2025-07-02 205759](https://github.com/user-attachments/assets/798e6bbe-2d15-4e0e-bd1f-38be1b5bfed1)
![Screenshot 2025-07-02 205754](https://github.com/user-attachments/assets/67366e41-b523-4632-9b96-2718dc926749)
