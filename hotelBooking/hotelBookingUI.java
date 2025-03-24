package hotelBooking;

import java.awt.*;
import javax.swing.*;

public class hotelBookingUI {
    private JFrame frame;
    private JPanel mainPanel, addBookingPanel, showBookingPanel, updateBookingPanel, deleteBookingPanel;
    private CardLayout cardLayout;
    
    public hotelBookingUI() {
        frame = new JFrame("Hotel Booking Management");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createHomePanel();
        createAddBookingPanel();
        createShowBookingPanel();
        createUpdateBookingPanel();
        createDeleteBookingPanel();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void createHomePanel() {
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        homePanel.setBackground(new Color(230, 240, 255)); // Light blue background
    
        JLabel titleLabel = new JLabel("Hotel Booking Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(30, 60, 90)); // Dark blue text
        homePanel.add(titleLabel, BorderLayout.NORTH);
    
        ImageIcon hotelIcon = new ImageIcon("hotel.jpg"); // Ensure "hotel.jpg" is in the project directory
        Image img = hotelIcon.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        homePanel.add(imageLabel, BorderLayout.CENTER); // Add image in center space
    

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(220, 230, 250)); // Soft blue panel
    

        JButton addBookingBtn = new JButton("Add Booking");
        JButton showBookingBtn = new JButton("Show All Bookings");
        JButton updateBookingBtn = new JButton("Update Booking");
        JButton deleteBookingBtn = new JButton("Delete Booking");
    
    
        Color btnBackground = new Color(50, 100, 180); // Deep blue
        Color btnForeground = Color.WHITE; // White text
    
        JButton[] buttons = {addBookingBtn, showBookingBtn, updateBookingBtn, deleteBookingBtn};
        for (JButton btn : buttons) {
            btn.setBackground(btnBackground);
            btn.setForeground(btnForeground);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusPainted(false);
        }
    
        addBookingBtn.addActionListener(e -> cardLayout.show(mainPanel, "AddBooking"));
        showBookingBtn.addActionListener(e -> cardLayout.show(mainPanel, "ShowBooking"));
        updateBookingBtn.addActionListener(e -> cardLayout.show(mainPanel, "UpdateBooking"));
        deleteBookingBtn.addActionListener(e -> cardLayout.show(mainPanel, "DeleteBooking"));
    
        buttonPanel.add(addBookingBtn);
        buttonPanel.add(showBookingBtn);
        buttonPanel.add(updateBookingBtn);
        buttonPanel.add(deleteBookingBtn);
    

        homePanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(homePanel, "Home");
    }
    

    private void createAddBookingPanel() {
        addBookingPanel = new JPanel();
        addBookingPanel.setLayout(new GridLayout(8, 2));
        
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField roomTypeField = new JTextField();
        JTextField roomNoField = new JTextField();
        JTextField checkInField = new JTextField();
        JTextField checkOutField = new JTextField();
        JButton addBtn = new JButton("Add Booking");
        JButton backBtn = new JButton("Back to Home");

        addBookingPanel.add(new JLabel("Name:"));
        addBookingPanel.add(nameField);
        addBookingPanel.add(new JLabel("Phone:"));
        addBookingPanel.add(phoneField);
        addBookingPanel.add(new JLabel("Room Type:"));
        addBookingPanel.add(roomTypeField);
        addBookingPanel.add(new JLabel("Room Number:"));
        addBookingPanel.add(roomNoField);
        addBookingPanel.add(new JLabel("Check-in Date (YYYY-MM-DD):"));
        addBookingPanel.add(checkInField);
        addBookingPanel.add(new JLabel("Check-out Date (YYYY-MM-DD):"));
        addBookingPanel.add(checkOutField);
        addBookingPanel.add(addBtn);
        addBookingPanel.add(backBtn);

        addBtn.addActionListener(e -> {
            hotelBookingOperation.addBooking(
                nameField.getText(),
                phoneField.getText(),
                roomTypeField.getText(),
                Integer.parseInt(roomNoField.getText()),
                checkInField.getText(),
                checkOutField.getText()
            );
            JOptionPane.showMessageDialog(frame, "Booking Added!");
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        
        mainPanel.add(addBookingPanel, "AddBooking");
    }

    private void createShowBookingPanel() {
        showBookingPanel = new JPanel(new BorderLayout());
        showBookingPanel.setBackground(new Color(255, 245, 235)); // Very light peachy pink
        
        JTextArea bookingArea = new JTextArea();
        bookingArea.setEditable(false);
        bookingArea.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton showBtn = new JButton("Show All Bookings");
        JButton backBtn = new JButton("Back to Home");
    
        showBtn.addActionListener(e -> bookingArea.setText(hotelBookingOperation.showBooking()));
    
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
    
        showBookingPanel.add(new JLabel("All Bookings:", JLabel.CENTER), BorderLayout.NORTH);
        showBookingPanel.add(new JScrollPane(bookingArea), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showBtn);
        buttonPanel.add(backBtn);
        showBookingPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(showBookingPanel, "ShowBooking");
    }
    

    private void createUpdateBookingPanel() {
        updateBookingPanel = new JPanel(new GridLayout(4, 2));
        
        JTextField bookingIdField = new JTextField();
        JTextField newRoomTypeField = new JTextField();
        JButton updateBtn = new JButton("Update Booking");
        JButton backBtn = new JButton("Back to Home");

        updateBtn.addActionListener(e -> {
            hotelBookingOperation.updateBookings(
                Integer.parseInt(bookingIdField.getText()),
                newRoomTypeField.getText()
            );
            JOptionPane.showMessageDialog(frame, "Booking Updated!");
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        updateBookingPanel.add(new JLabel("Booking ID:"));
        updateBookingPanel.add(bookingIdField);
        updateBookingPanel.add(new JLabel("New Room Type:"));
        updateBookingPanel.add(newRoomTypeField);
        updateBookingPanel.add(updateBtn);
        updateBookingPanel.add(backBtn);
        
        mainPanel.add(updateBookingPanel, "UpdateBooking");
    }

    private void createDeleteBookingPanel() {
        deleteBookingPanel = new JPanel(new GridLayout(3, 2));
        JTextField deleteIdField = new JTextField();
        JButton deleteBtn = new JButton("Delete Booking");
        JButton backBtn = new JButton("Back to Home");

        deleteBtn.addActionListener(e -> {
            hotelBookingOperation.deleteBooking(Integer.parseInt(deleteIdField.getText()));
            JOptionPane.showMessageDialog(frame, "Booking Deleted!");
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        deleteBookingPanel.add(new JLabel("Booking ID:"));
        deleteBookingPanel.add(deleteIdField);
        deleteBookingPanel.add(deleteBtn);
        deleteBookingPanel.add(backBtn);
        
        mainPanel.add(deleteBookingPanel, "DeleteBooking");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(hotelBookingUI::new);
    }
}
