package malacasteapp;

import java.util.Scanner;

public class AppointmentMenu {
    config conf = new config();
    Scanner sc = new Scanner(System.in);
    
    public void mainmenu() {
        int choice;

        do {
            System.out.println("----------- Main Menu -----------");
            System.out.println("1. ADD APPOINTMENT");
            System.out.println("2. VIEW APPOINTMENTS");
            System.out.println("3. UPDATE APPOINTMENT");
            System.out.println("4. DELETE APPOINTMENT");
            System.out.println("5. EXIT");
            System.out.println("---------------------------------");
            System.out.print("Enter your choice ( ");
            
            choice = getValidIntegerInput("Please select a valid option (1-5): ");

            switch (choice) {
                case 1:
                    add();
                    break;

                case 2:
                    view();
                    break;

                case 3:
                    view();
                    update();
                    break;

                case 4:
                    view();
                    delete();
                    view();
                    break;

                case 5:
                    System.out.println("Thank you for using the system mwa mwa.");
                    break;

                default:
                    System.out.println("Invalid choice. Please select again.");
            }

        } while (choice != 5); 
    }
    
    public void add() {
        System.out.println("Enter Appointment Date (MM/DD/YY): ");
        String appointmentDate = sc.next();
        String appointmentTime = getValidTimeInput("Enter Appointment Time (Ex. 10:00AM): ");
        String status = getValidStatusInput("Enter Status (Scheduled/Completed): ");

        String sql = "INSERT INTO tbl_appointments (appointment_date, appointment_time, status) VALUES (?, ?, ?)";
        conf.addRecord(sql, appointmentDate, appointmentTime, status);
        System.out.println("Appointment added successfully.");
    }

    private void view() {
        String query = "SELECT * FROM tbl_appointments";
        String[] headers = {"ID", "Date", "Time", "Status"};
        String[] columns = {"appointment_id", "appointment_date", "appointment_time", "status"};

        conf.viewRecords(query, headers, columns);
    }

    private void update() {
        int id = getValidIntegerInput("Enter Appointment ID to edit: ");
        System.out.println("Enter New Appointment Date (MM/DD/YY): ");
        String appointmentDate = sc.next();
        String appointmentTime = getValidTimeInput("New Appointment Time: ");
        String status = getValidStatusInput("New Status (Scheduled/Completed): ");
        
        String sqlUpdate = "UPDATE tbl_appointments SET appointment_date = ?, appointment_time = ?, status = ? WHERE appointment_id = ?";
        conf.updateRecord(sqlUpdate, appointmentDate, appointmentTime, status, id);
        System.out.println("Appointment updated successfully.");
    }

   private void delete() {
    int id = getValidIntegerInput("Enter Appointment ID to delete: ");
    
    
    System.out.print("Are you sure you want to delete the appointment with ID " + id + "? (yes/no): ");
    String confirmation = sc.next();

    if (confirmation.equalsIgnoreCase("yes")) {
        String sqlDelete = "DELETE FROM tbl_appointments WHERE appointment_id = ?";
        conf.deleteRecord(sqlDelete, id);
        System.out.println("Appointment deleted successfully.");
    } else {
        System.out.println("Deletion cancelled.");
    }
}

    private int getValidIntegerInput(String prompt) {
        int value = -1;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                sc.nextLine(); 
                return value;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine(); 
            }
        }
    }

  

    private String getValidTimeInput(String prompt) {
        String time;
        while (true) {
            System.out.print(prompt);
            time = sc.next();
            if (time.matches("^(1[0-2]|0?[1-9]):[0-5][0-9](AM|PM|am|pm)$")) { 
                return time;
            } else {
                System.out.println("Invalid time format. Please enter in HH:MM AM/PM format.");
            }
        }
    }

    private String getValidStatusInput(String prompt) {
        String status;
        while (true) {
            System.out.print(prompt);
            status = sc.next();
            if (status.equalsIgnoreCase("Scheduled") || status.equalsIgnoreCase("Completed")) {
                return status;
            } else {
                System.out.println("Invalid status. Please enter 'Scheduled' or 'Completed'.");
            }
        }
    }
    // na dugay kos validation sir huhu
}