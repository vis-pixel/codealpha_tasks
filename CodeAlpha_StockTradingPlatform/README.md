📈 Stock Trading Platform – Java Console Application

This project is a Stock Trading Platform developed in Java that simulates basic stock market operations such as buying, selling, and managing stock portfolios.
It is a menu-driven console application and supports persistent data storage using file serialization.

This project is suitable for internship tasks, academic mini-projects, and Java OOP practice.


📌 Features
👤 Create multiple users
💰 Manage user balance
📊 View available market stocks
🛒 Buy stocks based on available balance
💸 Sell owned stocks
📁 View user portfolio
💾 Save & load user data automatically using files
🧠 Object-Oriented Design with clean separation of classes


🛠️ Technologies Used
Java
Object-Oriented Programming (OOP)
File Handling
Serialization
Collections (ArrayList)
Scanner (User Input)


🧩 Project Structure
1️⃣ Stock Class
Represents a stock in the market
Stores stock symbol and price
2️⃣ StockHolding Class
Represents a user’s holding
Stores stock and quantity owned
3️⃣ User Class
Stores user name, balance, and portfolio
Handles:
    1.Buying stocks
    2.Selling stocks
    3.Viewing portfolio
4️⃣ StockTradingPlatform (Main Class)
Menu-driven application
Controls user interaction
Manages saving and loading data


▶️ How to Run the Project
Ensure Java JDK is installed

1.Save the file as:
     StockTradingPlatform.java
2.Compile the program:
     javac StockTradingPlatform.java
3.Run the program:
     java StockTradingPlatform


📂 Data Persistence
User data is saved in:
     stock_data.dat
Uses ObjectOutputStream and ObjectInputStream
Data is automatically loaded when the program restarts


📌 Menu Options
1. Add User
2. Buy Stock
3. Sell Stock
4. View Portfolio
5. View Market Stocks
6. Save & Exit


📈 Market Stocks (Sample)
AAPL – $150.0
GOOG – $2800.0
TSLA – $750.0
(Prices are static and used for simulation purposes)


🎯 Learning Outcomes
Understanding stock trading logic
Practicing Java Serialization
Implementing portfolio management
Applying OOP principles
Handling real-world scenarios like balance checks


🚀 Future Enhancements
Dynamic stock prices
Transaction history
User authentication
GUI using Java Swing / JavaFX
Database integration (MySQL)
Profit & loss calculations


📌 Use Case
Internship projects (CodeAlpha)
Java practice
Console-based system design
Academic mini-projects


👨‍💻 Author
NALLABOTHULA VISHNUPRIYA
Developed as part of Java Internship Tasks for practice and learning.


📜 License
This project is developed for educational purposes only.
