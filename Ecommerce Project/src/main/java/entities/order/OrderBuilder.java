package entities.order;

import entities.inventory.Inventory;
import entities.inventory.InventoryHandler;
import entities.product.Product;
import file_handler.FileHandler;

import java.util.ArrayList;
import java.util.Objects;

public class OrderBuilder {
    // private long orderID;
    private String customerEmail;
    private String shipmentAddress;
    private final ArrayList<Product> products;
    private final ArrayList<Integer> quantities;
    private Order.PaymentMethod paymentMethod;

    private static ArrayList<Order> orders;

    public static ArrayList<Order> getAllOrders(){
        if(Objects.isNull(orders)){
            orders = new ArrayList<>();
            FileHandler fileHandler = FileHandler.getInstance();
            Order order = (Order) fileHandler.readNext(FileHandler.ObjectType.ORDER);
            while(order != null) {
                orders.add(order);
                order = (Order) fileHandler.readNext(FileHandler.ObjectType.ORDER);
            }
        } return orders;
    }

    public OrderBuilder() {
        this.products = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    public OrderBuilder setCustomerEmail(String customerEmail_) {
        this.customerEmail = customerEmail_;
        return this;
    }

    public Order.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /*public OrderBuilder setOrderID(long orderID) {
        this.orderID = orderID;
        return this;
    }*/

    public OrderBuilder setShipmentAddress(String shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
        return this;
    }

    public OrderBuilder addProduct(Product product, int quantity) {
        Inventory inventory = InventoryHandler.getInventory();
        if(inventory.getStock(product.id()) < quantity) {
            System.out.println("Not enough stock");
            return this;
        }
        this.products.add(product);
        this.quantities.add(quantity);
        return this;
    }

    public OrderBuilder setPaymentMethod(Order.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public Order build() {
        int orderID = (int)(Math.random() * 9000) + 1000;
        boolean isPresentId = true;
        while(isPresentId){
            isPresentId = false;
            for(Order order : getAllOrders()){
                if(order.getOrderID() == orderID){
                    isPresentId = true;
                    orderID = (int)(Math.random() * 9000) + 1000;
                    break;
                }
            }
        }
        return new Order(orderID, customerEmail, shipmentAddress, products, quantities, paymentMethod);
    }
}
