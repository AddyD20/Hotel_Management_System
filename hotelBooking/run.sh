
#!/bin/bash

echo "Removing old compiled files"
rm -rf hotelBooking

echo "Compiling Java files..."
javac -d . -cp "./mysql-connector-j-9.2.0.jar" DatabaseConnection.java hotelBookingOperation.java hotelBookingUI.java

echo "Running Java program..."
java -cp ".;mysql-connector-j-9.2.0.jar" hotelBooking.hotelBookingUI