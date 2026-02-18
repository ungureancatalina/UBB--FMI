## About the Project

**Multithreaded Booking System** is a robust client-server application designed to manage workshop reservations across multiple cultural centers. Built using **Java**, it focuses on high-concurrency handling, data consistency, and asynchronous processing. The system simulates a real-world booking environment where users can reserve seats, make payments, and cancel bookings, while the server handles race conditions using **Thread Pools**, **Futures/Promises**, and strict synchronization mechanisms.
The project demonstrates advanced concepts of **Parallel and Distributed Programming**, including deadlock prevention, atomic transactions, and periodic background validation for data integrity.

---

## 🛠 Technologies Used

| Tool | Purpose |
|---|---|
| **Java (Core & Concurrency)** | Main implementation logic & multithreading |
| **Java Futures & Promises** | Asynchronous request processing |
| **Thread Pools (ExecutorService)** | Efficient management of concurrent client connections |
| **Sockets (TCP/IP)** | Network communication between Client and Server |
| **Synchronized / Locks** | Data protection and race condition prevention |
| **File I/O Persistence** | Storage for reservations and transaction logs |

---

## Features

- High Concurrency Server
- Asynchronous Processing
- Reservation Workflow
- Automatic Expiry
- Consistency Checks
  - Ledger integrity (Money collected vs. Sold tickets).
  - Capacity limits (preventing overbooking).
  - Expiration logic.
- Race Condition Handlingy
- Detailed Logging

---

## Project Structure

```bash
booking_system/
├── src/
│   ├── client/
│   │   └── ClientSimulator.java           
│   ├── server/
│   │   ├── ServerApp.java     
│   │   ├── PeriodicAuditor.java     
│   │   └── DataManager.java  
│   └── model/                
└── logs                
