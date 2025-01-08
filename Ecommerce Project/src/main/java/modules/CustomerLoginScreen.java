package modules;

import entities.customer.Customer;
import entities.customer.CustomerHandler;

import java.util.Optional;

import static modules.ScreenModule.scanner;
import static modules.ScreenModule.clearScreen;

public class CustomerLoginScreen {
    public static void startModule(){
        clearScreen();
        System.out.println("Welcome to Customer Screen\n1.Login (Existing user)\n2.Register (New user)\n3.Back");
        int choice = scanner.nextInt();

        switch (choice){
            case 1:{
                System.out.println("Enter email:");
                String email = scanner.next();
                System.out.println("Enter password:");
                String password = scanner.next();

                Optional<Customer> customer = CustomerHandler.login(email, password);
                if(customer.isPresent())
                    new CustomerAcess(customer.get()).startModule();
                else System.out.println("Invalid credentials");
            } break;
            case 2: {
                System.out.println("Enter name:");
                scanner.nextLine();
                String name = scanner.nextLine();
                System.out.println("Enter email:");
                String email = scanner.next();
                System.out.println("Enter password:");
                String password = scanner.next();
                CustomerHandler.addNewCustomer(new Customer(name, email, password));
            } break;
            case 3:
                break;
            default: System.out.println("Invalid choice");
        }
    }
}
