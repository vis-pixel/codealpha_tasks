🏨 Hotel Reservation System – Java Console Application

This project is a Hotel Reservation System developed in Java that allows users to manage room bookings efficiently through a menu-driven console interface.
It supports room availability checking, booking, cancellation, and persistent data storage using file handling.

This project is ideal for Java beginners and internship tasks (like CodeAlpha / InternPe / academic projects).


📌 Features
📋 View available rooms
🛏️ Book rooms by type:
1.Standard
2.Deluxe
3.Suite
❌ Cancel existing bookings
👤 View bookings for a specific guest
💾 Data persistence using file serialization
🔄 Automatically loads saved data on restart
🧩 Clean object-oriented design


🛠️ Technologies Used
Java
OOP Concepts
File Handling
Serialization
Collections (ArrayList)
Scanner for user input


🧩 Project Structure
1️⃣ Room Class
Stores room details (type, number, booking status)
Implements Serializable for file storage
2️⃣ Booking Class
Stores guest name and booked room
Links guest details with room object
3️⃣ HotelReservationSystem (Main Class)
Menu-driven user interface
Handles:
   1.Booking
   2.Cancellation
   3.Viewing bookings
   4.Saving & loading data

▶️ How to Run the Project
Make sure Java JDK is installed
1.Save the file as:
   HotelReservationSystem.java
2.Compile the program:
   javac HotelReservationSystem.java
3.Run the program:
   java HotelReservationSystem


📂 Data Storage
Booking data is saved in:
Copy code
    hotel_data.dat
Uses ObjectOutputStream & ObjectInputStream
Data remains safe even after program exit

📌 Menu Options
1. Show Available Rooms
2. Book a Room
3. Cancel Booking
4. View Guest Bookings
5.Save & Exit

🎯 Learning Outcomes
Understanding Java Serialization
Implementing real-world booking logic
Practicing OOP design
Handling files and persistent storage
Creating menu-based Java applications


📜 Use Case
Internship projects
College mini projects
Java practice
Console-based system design


🚀 Future Enhancements
Add pricing for room types
Admin login system
Date-based bookings
GUI version using Java Swing or JavaFX
Database integration (MySQL)

👨‍💻 Author
NALLABOTHULA VISHNUPRIYA
Developed as part of Java Internship Tasks for practice and learning.


📌 License
This project is created for educational purposes only.