import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Room class
class Room implements Serializable {
    String type;
    int number;
    boolean isBooked;

    Room(String type, int number) {
        this.type = type;
        this.number = number;
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return type + " Room " + number + " [" + (isBooked ? "Booked" : "Available") + "]";
    }
}

// Booking class
class Booking implements Serializable {
    String guestName;
    Room room;

    Booking(String guestName, Room room) {
        this.guestName = guestName;
        this.room = room;
    }
}

// Main Hotel Reservation System
public class HotelReservationSystem {
    private static final String FILE_NAME = "hotel_data.dat";
    private static ArrayList<Room> rooms;
    private static ArrayList<Booking> bookings;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load data
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                rooms = (ArrayList<Room>) ois.readObject();
                bookings = (ArrayList<Booking>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Error loading data. Starting fresh.");
                initializeData();
            }
        } else {
            initializeData();
        }

        while (true) {
            System.out.println("\n===== Hotel Reservation System =====");
            System.out.println("1. Show Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Guest Bookings");
            System.out.println("5. Save & Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    showAvailableRooms();
                    break;

                case 2:
                    System.out.print("Enter your name: ");
                    String guestName = sc.nextLine();
                    System.out.print("Enter room type (Standard/Deluxe/Suite): ");
                    String type = sc.nextLine();
                    bookRoom(guestName, type);
                    break;

                case 3:
                    System.out.print("Enter your name to cancel booking: ");
                    String nameCancel = sc.nextLine();
                    cancelBooking(nameCancel);
                    break;

                case 4:
                    System.out.print("Enter guest name to view bookings: ");
                    String guestView = sc.nextLine();
                    viewGuestBookings(guestView);
                    break;

                case 5:
                    saveData();
                    System.out.println(" Data saved. Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println(" Invalid choice! Try again.");
            }
        }
    }

    private static void initializeData() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        // Standard rooms
        for (int i = 1; i <= 5; i++)
            rooms.add(new Room("Standard", i));
        // Deluxe rooms
        for (int i = 6; i <= 8; i++)
            rooms.add(new Room("Deluxe", i));
        // Suite rooms
        for (int i = 9; i <= 10; i++)
            rooms.add(new Room("Suite", i));
    }

    private static void showAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room r : rooms) {
            if (!r.isBooked)
                System.out.println(r);
        }
    }

    private static void bookRoom(String guestName, String type) {
        for (Room r : rooms) {
            if (r.type.equalsIgnoreCase(type) && !r.isBooked) {
                r.isBooked = true;
                bookings.add(new Booking(guestName, r));
                System.out.println(" [OK] Room booked successfully: " + r);
                return;
            }
        }
        System.out.println(" No available rooms of type " + type + "!");
    }

    private static void cancelBooking(String guestName) {
        for (Booking b : bookings) {
            if (b.guestName.equalsIgnoreCase(guestName)) {
                b.room.isBooked = false;
                bookings.remove(b);
                System.out.println(" Booking canceled for " + guestName);
                return;
            }
        }
        System.out.println(" No booking found for " + guestName + "!");
    }

    private static void viewGuestBookings(String guestName) {
        boolean found = false;
        System.out.println("\n--- Bookings for " + guestName + " ---");
        for (Booking b : bookings) {
            if (b.guestName.equalsIgnoreCase(guestName)) {
                System.out.println(b.room);
                found = true;
            }
        }
        if (!found)
            System.out.println("No bookings found.");
    }

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(rooms);
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
