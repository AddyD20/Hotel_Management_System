package hotelBooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection{
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/hotelBookingDB";
        String user = "root";
        String password = "PassworD";

        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("connected to Mysql succesfully" + conn);
        }catch(SQLException e ){
            System.out.println("Connection failed");
            e.printStackTrace();
        }

    }
}