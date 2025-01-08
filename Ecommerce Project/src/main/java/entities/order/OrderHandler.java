package entities.order;

import java.util.Objects;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import file_handler.FileHandler;

public class OrderHandler {
    private static ArrayList<Order> orders;
    public static boolean updateFlag = false;

    public static ArrayList<Order> getAllOrders(){
        if(Objects.isNull(orders)){
            orders = new ArrayList<>();
            FileHandler fileHandler = FileHandler.getInstance();
            Order order = (Order) fileHandler.readNext(FileHandler.ObjectType.ORDER);
            while(order != null) {
                orders.add(order);
                order = (Order) fileHandler.readNext(FileHandler.ObjectType.ORDER);
            }
        }
        return orders;
    }

    public static void saveOrder(Order order){
        FileHandler fileHandler = FileHandler.getInstance();
        fileHandler.writeObjectToFile(order, FileHandler.ObjectType.ORDER);
    }

    public static void addNewOrder(Order order){
        if(orders == null) getAllOrders();
        orders.add(order);
        saveOrder(order);
    }

    public static void saveAllOrders(){
        FileHandler fileHandler = FileHandler.getInstance();
        fileHandler.deleteAllObjects(FileHandler.ObjectType.ORDER);
        for(Order order: orders) fileHandler.writeObjectToFile(order, FileHandler.ObjectType.ORDER);
    }

    public static void changeOrderStatus(long orderId, Order.Status status) throws NullPointerException {
        Optional<Order> order = getOrderById(orderId);
        if(order.isEmpty()) throw new NullPointerException("Order not found");
        order.get().updateStatus(status);
        updateFlag = true;
    }

    public static void cancelOrder(long orderId, String customerEmail) throws NullPointerException, IllegalAccessException{
        Optional<Order> order = getOrderById(orderId);
        if(order.isEmpty()) throw new NullPointerException("Order not found");
        if(!order.get().getCustomerEmail().equals(customerEmail))
            throw new IllegalAccessException("Order doesn't belong to this user");
        order.get().updateStatus(Order.Status.CANCELLED);
        updateFlag = true;
    }

    public static Optional<Order> getOrderById(long orderID){
        if(orders == null) getAllOrders();
        return orders.stream().filter(o -> o.getOrderID() == orderID).findFirst();
    }

    public static ArrayList<Order> getOrdersByCustomerEmail(String customerEmail_){
        if(orders == null) getAllOrders();
        return orders.stream().filter(o -> o.getCustomerEmail().equals(customerEmail_))
               .collect(Collectors.toCollection(ArrayList::new));
    }
}
