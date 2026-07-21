import java.util.*;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CodeAlpha_HotelReservationSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hotel_oro";
    private static final String username = "root";
    // This line pulls the password securely from Windows
    private static String password; // = System.getenv("DB_PASSWORD");

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        if (System.console() != null) {
            char[] passwordChars = System.console().readPassword("Enter MySQL Password for root:");
            password = new String(passwordChars);
        } else {
            System.out.println("Enter MySQL Password for root:");
            Scanner pwdScanner = new Scanner(System.in);
            password = pwdScanner.nextLine();
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                System.out.println();
                System.out.println("HOTEL RESERVATION SYSTEM");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Cancel Reservations"); // <-- Just change this text to "Cancel"
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        cancelReservation(connection, scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter guest name: ");
            String guestName = scanner.next();
            scanner.nextLine();

            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();

            // --- ROOM CATEGORY SELECTOR ---
            System.out.println("Select room category:");
            System.out.println("1. Standard | 2. Deluxe | 3. Suite");
            System.out.print("Choice: ");
            int catChoice = scanner.nextInt();

            String roomCategory = switch (catChoice) {
                case 2 -> "Deluxe";
                case 3 -> "Suite";
                default -> "Standard";
            };

            System.out.print("Enter contact number: ");
            String contactNumber = scanner.next();

            // --- FIXED SQL QUERY WITH ALL VALUES INCLUDED ---
            String sql = "INSERT INTO reservations (guest_name, room_number, room_category, contact_number) " +
                    "VALUES ('" + guestName + "', " + roomNumber + ", '" + roomCategory + "', '" + contactNumber + "')";

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation successful!");

                    // --- PAYMENT SIMULATION ---
                    System.out.println("=========================================");
                    System.out.println("       PROCESSING PAYMENT SIMULATION     ");
                    System.out.println("=========================================");
                    System.out.println("Connecting to Gateway... SUCCESS");
                    System.out.println("Transaction Status: APPROVED");
                    System.out.println("\n--- BOOKING DETAILS RECEIPT ---");
                    System.out.println("Guest Name: " + guestName);
                    System.out.println("Room Assignment: " + roomNumber + " (" + roomCategory + ")");
                    System.out.println("Contact Ref: " + contactNumber);
                    System.out.println("=========================================\n");
                } else {
                    System.out.println("Reservation failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection connection) throws SQLException {
        String sql = "SELECT reservation_id, guest_name, room_number, room_category, contact_number, reservation_date FROM reservations";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Current Reservations:");
            System.out.println(
                    "+----------------+-----------------+-------------+---------------+----------------+---------------------+");
            System.out.println(
                    "| Reservation ID | Guest           | Room Number | Room Category | Contact Number | Reservation Date    |");
            System.out.println(
                    "+----------------+-----------------+-------------+---------------+----------------+---------------------+");

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String roomCategory = resultSet.getString("room_category"); // <-- Added for your column
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                // Format and display the reservation data in a table-like format
                System.out.printf("| %-14d | %-15s | %-11d | %-13s | %-14s | %-19s |\n",
                        reservationId, guestName, roomNumber, roomCategory, contactNumber, reservationDate);
            }

            System.out.println(
                    "+----------------+-----------------+-------------+---------------+----------------+---------------------+");
        }
    }

    private static void getRoomNumber(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID: ");
            int reservationId = scanner.nextInt();
            System.out.print("Enter guest name: ");
            String guestName = scanner.next();

            // --- UPGRADED TO SELECT BOTH ROOM NUMBER AND ROOM CATEGORY ---
            String sql = "SELECT room_number, room_category FROM reservations " +
                    "WHERE reservation_id = " + reservationId +
                    " AND guest_name = '" + guestName + "'";

            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    String roomCategory = resultSet.getString("room_category"); // <-- Fetch category

                    // Print a cleaner, more detailed success message
                    System.out.println("Room number for Reservation ID " + reservationId +
                            " and Guest " + guestName + " is: " + roomNumber +
                            " (" + roomCategory + ")");
                } else {
                    System.out.println("Reservation not found for the given ID and guest name.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID to update: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            System.out.print("Enter new guest name: ");
            String newGuestName = scanner.nextLine();

            System.out.print("Enter new room number: ");
            int newRoomNumber = scanner.nextInt();

            // --- ADDITION: NEW ROOM CATEGORY SELECTOR ---
            System.out.println("Select new room category:");
            System.out.println("1. Standard | 2. Deluxe | 3. Suite");
            System.out.print("Choice: ");
            int catChoice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            String newRoomCategory = switch (catChoice) {
                case 2 -> "Deluxe";
                case 3 -> "Suite";
                default -> "Standard";
            };

            System.out.print("Enter new contact number: ");
            String newContactNumber = scanner.next();

            // --- FIXED SQL QUERY INCLUDING THE ROOM CATEGORY ---
            String sql = "UPDATE reservations SET guest_name = '" + newGuestName + "', " +
                    "room_number = " + newRoomNumber + ", " +
                    "room_category = '" + newRoomCategory + "', " + // <-- Added column update
                    "contact_number = '" + newContactNumber + "' " +
                    "WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation updated successfully!");
                } else {
                    System.out.println("Reservation update failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cancelReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID to cancel: "); // <-- Updated text
            int reservationId = scanner.nextInt();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation canceled successfully!"); // <-- Updated text
                } else {
                    System.out.println("Reservation cancellation failed."); // <-- Updated text
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection connection, int reservationId) {
        try {
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id= " + reservationId;

            try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)) {
                return resultSet.next();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void exit() throws InterruptedException {
        System.out.println("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.println(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thankyou For Using Hotel Reservation System!!!");
    }
}