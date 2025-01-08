package modules;

import entities.order.Order;
import entities.order.OrderHandler;
import entities.product.Product;
import entities.product.ProductBuilder;
import entities.inventory.Inventory;
import entities.inventory.InventoryHandler;

import java.util.Objects;
import java.util.Optional;

import static modules.ScreenModule.scanner;

public class AdminScreen {
    public static void startModule(){
        while(true){
            ScreenModule.clearScreen();
            System.out.println("Welcome to Admin Screen\n1.Add product\n2.Update product stock\n3.View low stock products\n4.See all orders\n5.Update order status\n6.See all products\n7.Exit");
            int choice = scanner.nextInt();

            switch (choice){
                case 1:{
                    ProductBuilder productBuilder = new ProductBuilder();
                    System.out.println("Enter product id:");
                    productBuilder.setId(scanner.nextLong());
                    System.out.println("Enter product name:");
                    scanner.nextLine();
                    productBuilder.setName(scanner.nextLine());
                    System.out.println("Enter product price:");
                    productBuilder.setPrice(scanner.nextDouble());

                    while(Objects.isNull(productBuilder.getCategory())){
                        System.out.println("Enter product category (1: ELECTRONICS, 2: CLOTHING, 3: FOOD):");
                        int category = scanner.nextInt();
                        switch(category){
                            case 1: productBuilder.setCategory(Product.Category.ELECTRONICS); break;
                            case 2: productBuilder.setCategory(Product.Category.CLOTHING); break;
                            case 3: productBuilder.setCategory(Product.Category.FOOD); break;
                            default: System.out.println("Invalid category");
                        }
                    }
                    System.out.println("Enter stock:");
                    int stock = scanner.nextInt();

                    Product product = productBuilder.build();
                    InventoryHandler.addNewProduct(product, stock);
                } break;
                case 2: {
                    System.out.println("Enter product id:");
                    long id = scanner.nextLong();
                    System.out.println("Enter new stock:");
                    int newStock = scanner.nextInt();
                    System.out.println("Enter operation (1: ADD, 2: REMOVE):");
                    int operation = scanner.nextInt();

                    InventoryHandler.updateProductStock(id, newStock, Inventory.Operations.values()[operation]);
                } break;
                case 3: {
                    System.out.println("Enter threshold:");
                    int threshold = scanner.nextInt();
                    InventoryHandler.getInventory().
                            getLowStockProducts(threshold).forEach((id, product) -> product.display());
                } break;
                case 4: {
                    OrderHandler.getAllOrders().forEach(Order::display);
                } break;
                case 5: {
                    System.out.println("Enter order id:");
                    long orderID = scanner.nextLong();
                    Optional<Order> order = OrderHandler.getOrderById(orderID);
                    if(order.isEmpty()){
                        System.out.println("Order not found"); break;
                    }
                    System.out.println("Enter new status (1: SHIPPED, 2: DELIVERED, 3: RETURNED):");
                    int status = scanner.nextInt();
                    switch (status) {
                        case 1: {
                            OrderHandler.changeOrderStatus(orderID, Order.Status.SHIPPED);
                        } break;
                        case 2: {
                            OrderHandler.changeOrderStatus(orderID, Order.Status.DELIVERED);
                        } break;
                        case 3: {
                            OrderHandler.changeOrderStatus(orderID, Order.Status.RETURNED);
                        } break;
                    }
                } break;
                case 6: {
                    InventoryHandler.getInventory().display();
                } break;
                case 7: return;
                default: System.out.println("Invalid choice");
            }

            System.out.println("Do you want to continue? (y/n)");
            char c = ScreenModule.scanner.next().charAt(0);
            if(c != 'y' && c != 'Y') break;
        }
    }
}
