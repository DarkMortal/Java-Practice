package entities.order;
import entities.inventory.Inventory;
import entities.product.Product;

import java.util.ArrayList;
import java.io.Serializable;

import entities.inventory.InventoryHandler;

public class Order implements Serializable {
    private final long orderID;
    private final String customerEmail;
    private String shipmentAddress;
    private final ArrayList<Product> products;
    private final ArrayList<Integer> quantities;

    public static enum Status {
        PLACED, PACKED, SHIPPED, DELIVERED, CANCELLED, RETURNED
    };
    private int status;

    public static enum PaymentMethod {
        CASH, UPI, CREDIT_CARD, DEBIT_CARD
    };
    private int paymentMethod;

    public Order(long orderID_, String customerEmail_, String shipmentAddress_, ArrayList<Product> products_, ArrayList<Integer> quantities_, PaymentMethod paymentMethod_) {
        this.orderID = orderID_;
        this.customerEmail = customerEmail_;
        this.shipmentAddress = shipmentAddress_;
        this.products = products_;
        this.quantities = quantities_;
        this.status = Status.PLACED.ordinal();
        this.paymentMethod = paymentMethod_.ordinal();

        Inventory inventory = InventoryHandler.getInventory();
        for(int i = 0; i < products.size(); i++){
            try{
                inventory.updateProductStock(products.get(i).id(), quantities.get(i), Inventory.Operations.REMOVE);
                InventoryHandler.saveInventory();  // save the updated inventory
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }  // all products in the order are removed from the inventory
    }

    public long getOrderID() {
        return this.orderID;
    }
    public String getCustomerEmail(){
        return this.customerEmail;
    }
    public String getShipmentAddress() {
        return shipmentAddress;
    }
    public Status getStatus() {
        return Status.values()[status];
    }
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.values()[paymentMethod];
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for(int index = 0; index < products.size(); index++) {
            totalPrice += products.get(index).price() * quantities.get(index);
        }
        return totalPrice;
    }

    public void addProduct(Product product_, int quantity_) {
        for(int index = 0; index < products.size(); index++) {
            if(products.get(index).equals(product_)) {
                quantities.set(index, quantities.get(index) + quantity_);
                return;
            }
        }
        products.add(product_);
        quantities.add(quantity_);
    }

    public void removeProduct(Product product) throws NullPointerException{
        for(int index = 0; index < products.size(); index++) {
            if(products.get(index).equals(product)) {
                if(quantities.get(index) > 1) {
                    quantities.set(index, quantities.get(index) - 1);
                } else {
                    products.remove(index);
                    quantities.remove(index);
                }
                return;
            }
        }
        throw new NullPointerException("Product not found in order");
    }

    public void updatePaymentMethod(PaymentMethod paymentMethod_) {
        this.paymentMethod = paymentMethod_.ordinal();
    }
    public void updateShipmentAddress(String shipmentAddress_) {
        this.shipmentAddress = shipmentAddress_;
    }

    public void updateStatus(Status status_) {
        if(status_.ordinal() < this.status){
            System.out.println("Invalid status update"); return;
        }
        if(status_ == Status.RETURNED){
            for(int i = 0; i < products.size(); i++){
                InventoryHandler.updateProductStock(products.get(i).id(), quantities.get(i), Inventory.Operations.ADD);
            }
            InventoryHandler.saveInventory();  // save inventory after returning products
        }
        this.status = status_.ordinal();
        System.out.println("Order ID: " + this.orderID + " status updated to " + Status.values()[this.status].toString() + "\n");
    }

    public void display() {
        System.out.println("Order ID: " + orderID);
        System.out.println("Payment method: " + PaymentMethod.values()[paymentMethod].name());
        System.out.println("Status: " + Status.values()[status].name());
        System.out.println("Shipment Address: " + shipmentAddress + "\n\nItems:");
        for(int index = 0; index < products.size(); index++) {
            if(index == products.size() - 1)
                System.out.println(products.get(index).name() + " " + quantities.get(index) + " units\n");
            else System.out.println(products.get(index).name() + " " + quantities.get(index) + " units");
        }
        System.out.println("Total order value: " + getTotalPrice());
    }
}
