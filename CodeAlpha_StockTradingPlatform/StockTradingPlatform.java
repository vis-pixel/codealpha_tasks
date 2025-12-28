import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Stock class
class Stock implements Serializable {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

// Stock holding class
class StockHolding implements Serializable {
    Stock stock;
    int quantity;

    StockHolding(Stock stock, int quantity) {
        this.stock = stock;
        this.quantity = quantity;
    }
}

// User class
class User implements Serializable {
    String name;
    double balance;
    ArrayList<StockHolding> portfolio;

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new ArrayList<>();
    }

    void buyStock(Stock stock, int quantity) {
        double total = stock.price * quantity;
        if (total > balance) {
            System.out.println("Insufficient balance!");
            return;
        }
        balance -= total;
        for (StockHolding sh : portfolio) {
            if (sh.stock.symbol.equals(stock.symbol)) {
                sh.quantity += quantity;
                System.out.println("Bought " + quantity + " shares of " + stock.symbol);
                return;
            }
        }
        portfolio.add(new StockHolding(stock, quantity));
        System.out.println("Bought " + quantity + " shares of " + stock.symbol);
    }

    void sellStock(Stock stock, int quantity) {
        for (int i = 0; i < portfolio.size(); i++) {
            StockHolding sh = portfolio.get(i);
            if (sh.stock.symbol.equals(stock.symbol)) {
                if (sh.quantity < quantity) {
                    System.out.println("Not enough shares to sell!");
                    return;
                }
                sh.quantity -= quantity;
                balance += stock.price * quantity;
                System.out.println("Sold " + quantity + " shares of " + stock.symbol);
                if (sh.quantity == 0)
                    portfolio.remove(i);
                return;
            }
        }
        System.out.println("You do not own this stock!");
    }

    void viewPortfolio() {
        System.out.println("\n--- Portfolio for " + name + " ---");
        System.out.println("Balance: $" + balance);
        if (portfolio.isEmpty()) {
            System.out.println("No stocks owned.");
        } else {
            for (StockHolding sh : portfolio) {
                System.out.println(sh.stock.symbol + "  " + sh.quantity + " shares");
            }
        }
    }
}

// Main class
public class StockTradingPlatform {
    private static final String FILE_NAME = "stock_data.dat";
    private static ArrayList<User> users;
    private static ArrayList<Stock> market;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        users = loadData();
        market = new ArrayList<>();
        market.add(new Stock("AAPL", 150.0));
        market.add(new Stock("GOOG", 2800.0));
        market.add(new Stock("TSLA", 750.0));

        while (true) {
            System.out.println("\n===== Stock Trading Platform =====");
            System.out.println("1. Add User");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Market Stocks");
            System.out.println("6. Save & Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter user name: ");
                    String userName = sc.nextLine();
                    System.out.print("Enter initial balance: $");
                    double balance = sc.nextDouble();
                    sc.nextLine();
                    users.add(new User(userName, balance));
                    System.out.println("User added successfully!");
                    break;

                case 2:
                    User buyer = selectUser(sc);
                    if (buyer == null)
                        break;
                    showMarket();
                    System.out.print("Select stock number to buy: ");
                    int buyIndex = sc.nextInt() - 1;
                    sc.nextLine();
                    if (buyIndex < 0 || buyIndex >= market.size()) {
                        System.out.println("Invalid stock number!");
                        break;
                    }
                    System.out.print("Enter quantity to buy: ");
                    int buyQty = sc.nextInt();
                    sc.nextLine();
                    buyer.buyStock(market.get(buyIndex), buyQty);
                    break;

                case 3:
                    User seller = selectUser(sc);
                    if (seller == null)
                        break;
                    showMarket();
                    System.out.print("Select stock number to sell: ");
                    int sellIndex = sc.nextInt() - 1;
                    sc.nextLine();
                    if (sellIndex < 0 || sellIndex >= market.size()) {
                        System.out.println("Invalid stock number!");
                        break;
                    }
                    System.out.print("Enter quantity to sell: ");
                    int sellQty = sc.nextInt();
                    sc.nextLine();
                    seller.sellStock(market.get(sellIndex), sellQty);
                    break;

                case 4:
                    User viewer = selectUser(sc);
                    if (viewer != null)
                        viewer.viewPortfolio();
                    break;

                case 5:
                    showMarket();
                    break;

                case 6:
                    saveData();
                    System.out.println("Data saved. Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static User selectUser(Scanner sc) {
        if (users.isEmpty()) {
            System.out.println("No users available! Add a user first.");
            return null;
        }
        System.out.print("Enter user name: ");
        String name = sc.nextLine();
        for (User u : users) {
            if (u.name.equalsIgnoreCase(name))
                return u;
        }
        System.out.println("User not found!");
        return null;
    }

    private static void showMarket() {
        System.out.println("\n--- Market Stocks ---");
        int i = 1;
        for (Stock s : market) {
            System.out.println(i++ + ". " + s.symbol + " ($" + s.price + ")");
        }
    }

    private static ArrayList<User> loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                ArrayList<?> temp = (ArrayList<?>) obj;
                ArrayList<User> loaded = new ArrayList<>();
                for (Object o : temp) {
                    if (o instanceof User)
                        loaded.add((User) o);
                }
                return loaded;
            } else {
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data. Starting fresh.");
            return new ArrayList<>();
        }
    }

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
