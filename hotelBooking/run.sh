#!/bin/bash

echo "Compiling Java files..."
javac -d . -cp "./mysql-connector-j-9.2.0.jar" DatabaseConnection.java

echo "Running Java program..."
java -cp ".;mysql-connector-j-9.2.0.jar" hotelBooking.DatabaseConnection
