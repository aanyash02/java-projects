# 🎓 CodeAlpha Java Programming Internship Projects

This repository contains two Java applications developed for the **CodeAlpha Java Programming Internship**.

---

## 📁 Repository Structure

.
├── Hotel Reservation System/
│   └── HotelReservationSystem.java
├── Student Grade Tracker/
│   └── StudentGradeTracker.java
└── README.md

---

## 🏨 Project 1: Hotel Reservation System

A Java application designed to manage hotel room bookings, view reservations, and perform update/cancellation operations using a **MySQL** database backend via **JDBC**.

### Key Features
* 📝 **Reserve a Room:** Register guest details, room numbers, room categories (Standard, Deluxe, Suite), and contact info.
* 📋 **View Reservations:** Display active bookings in a structured tabular format.
* 🔍 **Get Room Details:** Look up assigned room numbers and categories using Reservation ID and Guest Name.
* ✏️ **Update Reservations:** Modify existing booking information easily.
* ❌ **Cancel Reservations:** Delete room reservations directly from the database.
* 🔒 **Secure Authentication:** Prompts for the MySQL password at runtime to keep sensitive credentials out of the source code.

### Database Setup
CREATE DATABASE hotel_oro;
USE hotel_oro;

CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(255) NOT NULL,
    room_number INT NOT NULL,
    room_category VARCHAR(50) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

---

## 📊 Project 2: Student Grade Tracker

A Java console application designed to record, manage, and analyze student test scores, calculating key statistical metrics.

### Key Features
* 👤 **Dynamic Entry:** Accepts custom number of student records using dynamic lists (`ArrayList<String>` and `ArrayList<Integer>`).
* 📊 **Student Report:** Displays student names alongside their recorded marks in a structured list.
* 📈 **Grade Analytics:** Calculates and outputs class Average Marks (as double precision), Highest Marks, and Lowest Marks.

---

## 🛠️ Requirements & Tech Stack

* **Language:** Java (JDK 8 or higher)
* **Database:** MySQL (for Hotel Reservation System)
* **Driver:** MySQL Connector/J (JDBC Driver)
* **IDE/Editor:** Visual Studio Code

---

## 🚀 How to Run

1. **Clone the Repository:**
   git clone https://github.com/aanyash02/CodeAlpha_Hotel_Reservation_System.git

2. **Run Hotel Reservation System:**
   * Open HotelReservationSystem.java.
   * Ensure your MySQL Connector JDBC JAR is added to your project classpath.
   * Run the file and enter your MySQL root password when prompted in the terminal.

3. **Run Student Grade Tracker:**
  javac CodeAlpha_StudentGradeTracker.java
   java CodeAlpha_StudentGradeTracker

---

## 🧑‍💻 Author

Developed as part of the **CodeAlpha Internship**.
* **GitHub:** [@aanyash02](https://github.com/aanyash02)

```
