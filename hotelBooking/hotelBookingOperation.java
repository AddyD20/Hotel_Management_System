package hotelBooking;

import java.sql.*;
import java.util.Scanner;

public class hotelBookingOperation {
    private static final String URL = "jdbc:mysql://localhost:3306/HotelBookingDB";
    private static final String USER = "root";
    private static final String PASSWORD = "PassworD";


    //new booking
    public static void  addBooking(String customerName, String phoneNo , String roomType , int roomNo , String checkIn, String checkOut){
        String checkSQL = "select count(*) from bookings where room_no = ? and (check_in < ? and check_out > ?)";
        String sql = "INSERT INTO bookings(guest_name , phone , room_type , room_no, check_in , check_out) VALUES (? , ? , ? , ? , ? , ?)";
        
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement checkstmt = conn.prepareStatement(checkSQL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            Date checkInDate = Date.valueOf(checkIn);
            Date checkOuDate = Date.valueOf(checkOut);

            checkstmt.setInt(1, roomNo);
            checkstmt.setDate(2, checkOuDate);
            checkstmt.setDate(3, checkInDate);
            ResultSet rs = checkstmt.executeQuery();
            rs.next();

            if(rs.getInt(1)>0){
                System.out.println("Room " + roomNo + " is already booked for the selected dates. Please choose another room.");
                return;
            }

            stmt.setString(1, customerName);
            stmt.setString(2, phoneNo);
            stmt.setString(3, roomType);
            stmt.setInt(4,roomNo);
            stmt.setDate(5, Date.valueOf(checkIn));
            stmt.setDate(6, Date.valueOf(checkOut));
            stmt.executeUpdate();

            System.out.println("Booking added successfully!" + roomNo);
        
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    //read : Display all bookings
    public static String showBooking() {
        String sql = "SELECT * FROM bookings"; 
        StringBuilder bookings = new StringBuilder();
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                bookings.append("ID: ").append(rs.getInt("booking_id"))
                        .append(", Name: ").append(rs.getString("guest_name"))
                        .append(", Room: ").append(rs.getString("room_type"))
                        .append(", Room Number: ").append(rs.getInt("room_no"))
                        .append(", Check-in: ").append(rs.getDate("check_in"))
                        .append(", Check-Out: ").append(rs.getDate("check_out"))
                        .append("\n");                     
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error fetching bookings!";
        }
        
        return bookings.toString().isEmpty() ? "No bookings available!" : bookings.toString();
    }
    
    

    //Modify exsisting bookings
    public static void updateBookings(int id , String newRoomType ){
        String sql1 = "select room_number from rooms where room_type = ? and status = 'available' limit 1";
        String sql2 = "update bookings set room_type = ?, room_no = ? where booking_id = ?";
        String sql3 = "update rooms set status = 'Booked' where room_number = ?";


        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement stmt1 = conn.prepareStatement(sql1);
        PreparedStatement stmt2 = conn.prepareStatement(sql2);
        PreparedStatement stmt3 = conn.prepareStatement(sql3)) {

            stmt1.setString(1, newRoomType);
            try(ResultSet rs = stmt1.executeQuery()) {
                if(rs.next()) {
                    int newRoomNo = rs.getInt("room_number");

                    stmt2.setString(1,newRoomType);
                    stmt2.setInt(2, newRoomNo);
                    stmt2.setInt(3, id);

                    int rowsUpdated = stmt2.executeUpdate();

                    if(rowsUpdated>0){
                        stmt3.setInt(1, newRoomNo);
                        stmt3.executeUpdate();
                        System.out.println("✅ Booking updated succesfully");
                    }else{
                        System.out.println("Booking id not found.");
                    }
                } else {
                    System.out.println("No available rooms for this type.");
                }
            }
                
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    
    //Delete : Remove booking 
    public static void deleteBooking(int id) {
        String sql1 = "select room_no from bookings where booking_id = ?";
        String sql2 = "delete from bookings where booking_id = ?";
        String sql3 = "update rooms set status = 'Available' where room_number = ?";

        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement stmt1 = conn.prepareStatement(sql1);
        PreparedStatement stmt2 = conn.prepareStatement(sql2);
        PreparedStatement stmt3 = conn.prepareStatement(sql3)) {

        stmt1.setInt(1, id);
        try(ResultSet rs = stmt1.executeQuery()) {
            if(rs.next()){
                int roomNo = rs.getInt("room_no");
    
                stmt2.setInt(1, id);
                int rowsDeleted = stmt2.executeUpdate();
    
                if(rowsDeleted > 0) {        
                    if(roomNo>0){
                        stmt3.setInt(1, roomNo);
                        stmt3.executeUpdate();
                    }    
                        System.out.println("✅ Booking deleted succesfully");
                    }else{
                        System.out.println("Booking Id not found.");
                    }
    
                }else{
                    System.out.println("Booking ID not found");
                }
            };
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\n 🏨 Hotel Booking System");
            System.out.println("1️⃣ Add Booking");
            System.out.println("2️⃣ Show All Bookings");
            System.out.println("3️⃣ Update Booking");
            System.out.println("4️⃣ Delete Booking");
            System.out.println("5️⃣ Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                System.out.println("Enter Name: ");
                String name = scanner.nextLine();
                System.out.println("Enter Phone Number: ");
                String phoneNo = scanner.nextLine();
                System.out.println("Enter Room Type: ");
                String room = scanner.nextLine();
                System.out.println("Enter Room Number: ");
                int roomNo = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter Check-in Date (YYYY-MM-DD): ");
                String checkIn = scanner.nextLine();
                System.out.println("Enter Check-out Date(YYYY-MM-DD): ");
                String checkOut = scanner.nextLine();
                addBooking(name, phoneNo, room, roomNo , checkIn, checkOut);
                break;

                case 2:
                showBooking();
                break;

                case 3:
                System.out.println("Enter Booking ID to update: ");
                int idToUpdate = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter new room type: ");
                String newRoom = scanner.nextLine();
                updateBookings(idToUpdate, newRoom );
                break;

                case 4:
                System.out.println("Enter booking ID to delete: ");
                int idToDelete = scanner.nextInt();
                deleteBooking(idToDelete);
                break;

                case 5:
                System.out.println("Exiting Hotel Booking System...");
                scanner.close();
                return;

                default:
                System.out.println("Invalid choice . Try again.");

            }
        }
    }    

}
