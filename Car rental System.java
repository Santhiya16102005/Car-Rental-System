package carrentalsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main extends JFrame {
    private ArrayList<Car> fleet;
    private ArrayList<Booking> bookings;

    private JTextArea textArea;
    private JTextField nameField, emailField, phoneField, carModelField, bookingDateField;

    public Main() {
        fleet = new ArrayList<>();
        bookings = new ArrayList<>();

        setTitle("Car Rental System");
        setSize(700, 500);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 5, 5));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Car Model:"));
        carModelField = new JTextField();
        inputPanel.add(carModelField);

        inputPanel.add(new JLabel("Booking Date:"));
        bookingDateField = new JTextField();
        inputPanel.add(bookingDateField);

        JButton startButton = new JButton("Start");
        JButton bookButton = new JButton("Book Car");

        startButton.addActionListener(e -> addCarsToFleet());
        bookButton.addActionListener(e -> handleBooking());

        inputPanel.add(startButton);
        inputPanel.add(bookButton);

        add(inputPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addCarsToFleet() {
        fleet.add(new Car("ABC123", "Toyota Camry"));
        fleet.add(new Car("DEF456", "Honda Accord"));
        fleet.add(new Car("GHI789", "Tesla Model 3"));
        textArea.append("Cars added to fleet:\n");
        for (Car car : fleet) {
            textArea.append(car + "\n");
        }
    }

    private void handleBooking() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String carModel = carModelField.getText();
        String bookingDate = bookingDateField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || carModel.isEmpty() || bookingDate.isEmpty()) {
            textArea.append("Error: All fields are required!\n");
            return;
        }

        Customer customer = new Customer(name, email, phone);
        boolean carFound = false;
        boolean carAvailable = false;

        for (Car car : fleet) {
            if (car.getModel().equalsIgnoreCase(carModel)) {
                carFound = true;
                if (car.isAvailable()) {
                    Booking booking = new Booking(customer, car, bookingDate);
                    bookings.add(booking);
                    textArea.append("Booking Successful:\n" + booking + "\n");
                    carAvailable = true;
                    break;
                }
            }
        }

        if (!carFound) {
            textArea.append("Error: Car model " + carModel + " not found in the fleet.\n");
        } else if (!carAvailable) {
            textArea.append("Error: Car model " + carModel + " is not available for booking.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main gui = new Main();
            gui.setVisible(true);
        });
    }

    private class Car {
        private String licensePlate;
        private String model;
        private boolean isAvailable;

        public Car(String licensePlate, String model) {
            this.licensePlate = licensePlate;
            this.model = model;
            this.isAvailable = true;
        }

        public String getLicensePlate() {
            return licensePlate;
        }

        public String getModel() {
            return model;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }

        @Override
        public String toString() {
            return "Car [Model=" + model + ", License Plate=" + licensePlate + ", Available=" + isAvailable + "]";
        }
    }

    private class Customer {
        private String name;
        private String email;
        private String phone;

        public Customer(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        @Override
        public String toString() {
            return "Customer [Name=" + name + ", Email=" + email + ", Phone=" + phone + "]";
        }
    }

    private class Booking {
        private Customer customer;
        private Car car;
        private String bookingDate;

        public Booking(Customer customer, Car car, String bookingDate) {
            this.customer = customer;
            this.car = car;
            this.bookingDate = bookingDate;
            car.setAvailable(false);
        }

        @Override
        public String toString() {
            return "Booking [Customer=" + customer.getName() + ", Car=" + car.getModel() + ", Date=" + bookingDate + "]";
        }
    }
}