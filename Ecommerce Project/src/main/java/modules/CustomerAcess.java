package modules;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import entities.order.Order;
import entities.order.OrderBuilder;
import entities.order.OrderHandler;
import entities.customer.Customer;
import entities.customer.CustomerHandler;
import entities.product.Product;
import entities.inventory.InventoryHandler;

import static modules.ScreenModule.scanner;
import static modules.ScreenModule.clearScreen;

public class CustomerAcess {
    private static final Scanner scanner = new Scanner(System.in);
    private Customer customer;

    public CustomerAcess(Customer customer){
        this.customer = customer;
    }

    public void startModule() {
        while(true){
            clearScreen();
            System.out.println("Welcome " + this.customer.name());
            System.out.println("1.Order a product\n2.View orders\n3.See all products\n4.Cancel a order");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:{
                    OrderBuilder orderBuilder = new OrderBuilder();
                    orderBuilder.setCustomerEmail(this.customer.email());
                    System.out.println("Enter shipment address:");
                    scanner.nextLine();
                    orderBuilder.setShipmentAddress(scanner.nextLine());
                    System.out.println("Enter number of products:");
                    int n = scanner.nextInt();
                    for(int i = 0; i < n; i++){
                        System.out.println("Enter product id:");
                        int productID = scanner.nextInt();
                        Optional<Product> product = InventoryHandler.getInventory().getProduct(productID);

                        if(product.isEmpty()){
                            System.out.println("Product not found");
                            continue;
                        }
                        System.out.println("Enter quantity:");
                        int quantity = scanner.nextInt();
                        orderBuilder.addProduct(product.get(), quantity);
                    }
                    System.out.println("Enter payment method:\n1. CASH\n2. UPI\n3. CREDIT CARD\n4. DEBIT CARD");
                    while(Objects.isNull(orderBuilder.getPaymentMethod())){
                        int paymentMethod = scanner.nextInt();
                        switch (paymentMethod){
                            case 1: orderBuilder.setPaymentMethod(Order.PaymentMethod.CASH); break;
                            case 2: orderBuilder.setPaymentMethod(Order.PaymentMethod.UPI); break;
                            case 3: orderBuilder.setPaymentMethod(Order.PaymentMethod.CREDIT_CARD); break;
                            case 4: orderBuilder.setPaymentMethod(Order.PaymentMethod.DEBIT_CARD); break;
                            default: System.out.println("Invalid choice");
                        }
                    }
                    Order order = orderBuilder.build();
                    OrderHandler.addNewOrder(order);
                    System.out.println("Order placed successfully");
                } break;
                case 2:{
                    OrderHandler.getOrdersByCustomerEmail(this.customer.email()).forEach(Order::display);
                } break;
                case 3:{
                    InventoryHandler.getInventory().display();
                } break;
                case 4: {
                    System.out.println("Enter order id:");
                    long id = scanner.nextLong();
                    try{
                        OrderHandler.cancelOrder(id, this.customer.email());
                        System.out.println("Order cancelled successfully");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } break;
                default: System.out.println("Invalid choice");
            }
            System.out.println("Do you want to continue? (y/n)");
            char c = scanner.next().charAt(0);
            if(c != 'y' && c != 'Y') break;
        }
    }
}
