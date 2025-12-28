import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student implements Serializable {
    String name;
    ArrayList<Integer> marks;

    Student(String name) {
        this.name = name;
        this.marks = new ArrayList<>();
    }

    void addMark(int mark) {
        if (mark >= 0 && mark <= 100) {
            marks.add(mark);
        } else {
            System.out.println("Invalid mark! Must be 0–100.");
        }
    }

    double getAverage() {
        if (marks.isEmpty())
            return 0;
        int sum = 0;
        for (int m : marks)
            sum += m;
        return (double) sum / marks.size();
    }

    int getHighest() {
        if (marks.isEmpty())
            return 0;
        int max = marks.get(0);
        for (int m : marks)
            if (m > max)
                max = m;
        return max;
    }

    int getLowest() {
        if (marks.isEmpty())
            return 0;
        int min = marks.get(0);
        for (int m : marks)
            if (m < min)
                min = m;
        return min;
    }

    void display() {
        System.out.println("\n--- Student Report ---");
        System.out.println("Name: " + name);
        System.out.println("Marks: " + marks);
        System.out.printf("Average Score: %.2f\n", getAverage());
        System.out.println("Highest Score: " + getHighest());
        System.out.println("Lowest Score: " + getLowest());
    }
}

public class StudentGradeTracker {
    private static final String FILE_NAME = "students.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = loadStudents();

        while (true) {
            System.out.println("\n===== Student Grade Tracker =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Marks to Student");
            System.out.println("3. View Student Report");
            System.out.println("4. View All Students Summary");
            System.out.println("5. Save & Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();
                    students.add(new Student(name));
                    System.out.println(" Student added successfully!");
                    break;

                case 2:
                    if (students.isEmpty()) {
                        System.out.println("⚠ No students available! Add a student first.");
                        break;
                    }
                    System.out.print("Enter student name: ");
                    String studentName = sc.nextLine();
                    Student found = null;
                    for (Student s : students) {
                        if (s.name.equalsIgnoreCase(studentName)) {
                            found = s;
                            break;
                        }
                    }
                    if (found == null) {
                        System.out.println(" Student not found!");
                    } else {
                        System.out.println("Enter marks separated by space (0 to 100): ");
                        String[] marksInput = sc.nextLine().split(" ");
                        for (String m : marksInput) {
                            try {
                                int mark = Integer.parseInt(m);
                                found.addMark(mark);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input ignored: " + m);
                            }
                        }
                        System.out.println("[OK] Marks added successfully!");
                    }
                    break;

                case 3:
                    if (students.isEmpty()) {
                        System.out.println(" No students to show.");
                        break;
                    }
                    System.out.print("Enter student name: ");
                    String nameReport = sc.nextLine();
                    boolean exists = false;
                    for (Student s : students) {
                        if (s.name.equalsIgnoreCase(nameReport)) {
                            s.display();
                            exists = true;
                            break;
                        }
                    }
                    if (!exists)
                        System.out.println(" Student not found!");
                    break;

                case 4:
                    if (students.isEmpty()) {
                        System.out.println(" No students to display.");
                        break;
                    }
                    System.out.println("\n===== All Students Summary =====");
                    System.out.printf("%-20s %-10s %-10s %-10s\n", "Name", "Average", "Highest", "Lowest");
                    System.out.println("-------------------------------------------------");
                    for (Student s : students) {
                        System.out.printf("%-20s %-10.2f %-10d %-10d\n", s.name, s.getAverage(), s.getHighest(),
                                s.getLowest());
                    }
                    break;

                case 5:
                    saveStudents(students);
                    System.out.println(" Data saved. Exiting... Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println(" Invalid choice! Try again.");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Student> loadStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data. Starting fresh.");
            return new ArrayList<>();
        }
    }

    private static void saveStudents(ArrayList<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
