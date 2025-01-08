package entities.customer;

import java.util.Objects;
import java.util.ArrayList;
import java.util.Optional;

import file_handler.FileHandler;

public class CustomerHandler {
    private static ArrayList<Customer> customers;

    public static ArrayList<Customer> getAllCustomers(){
        if(Objects.isNull(customers)){
            customers = new ArrayList<>();
            FileHandler fileHandler = FileHandler.getInstance();
            Customer customer = (Customer) fileHandler.readNext(FileHandler.ObjectType.CUSTOMER);
            while(customer != null) {
                 customers.add(customer);
                 customer = (Customer) fileHandler.readNext(FileHandler.ObjectType.CUSTOMER);
            }
        }
        return customers;
    }

    public static void saveCustomer(Customer customer){
        FileHandler fileHandler = FileHandler.getInstance();
        fileHandler.writeObjectToFile(customer, FileHandler.ObjectType.CUSTOMER);
    }

    public static boolean addNewCustomer(Customer customer){
        if(customers == null) getAllCustomers();
        if(customers.stream().anyMatch(c -> c.email().equals(customer.email()))){
            System.out.println("Customer with this email already exists");
            return false;
        }
        customers.add(customer);
        saveCustomer(customer);
        return true;
    }

    public static Optional<Customer> login(String email, String password){
        if(customers == null) getAllCustomers();
        return customers.stream().filter(c -> c.email().equals(email) && c.password().equals(password)).findFirst();
    }
}
