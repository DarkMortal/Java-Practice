import entities.inventory.*;
import entities.product.*;
import entities.customer.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainTest {
    Product product = new Product(1, "Laptop", 1000, Product.Category.ELECTRONICS);
    Inventory inventory = new Inventory(){{
        try{
            addProduct(new Product(1, "Laptop", 1000, Product.Category.ELECTRONICS), 10);
            addProduct(new Product(2, "T-shirt", 20, Product.Category.CLOTHING), 100);
            addProduct(new Product(3, "Apple", 1, Product.Category.FOOD), 110);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }};

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Order(1)
    @Test
    void testInventory() {
        try{
            inventory.updateProductStock(3, 100, Inventory.Operations.REMOVE);
            assertEquals(10, inventory.getStock(3));

            inventory.updateProductStock(1, 100, Inventory.Operations.ADD);
            assertEquals(110, inventory.getStock(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Order(2)
    @Test
    void testProduct(){
        assertEquals("Laptop", inventory.getProduct(1).orElse(product).name());
        assertEquals(Product.Category.ELECTRONICS, inventory.getProduct(1).orElse(product).category());
    }

    @Order(3)
    @Test
    void testCustomer() {
        try{
            new Customer( "John Doe", "jdoo.com", "12321");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid email format", e.getMessage());
        }
    }

    @Order(4)
    @Test
    void testOrder() {
        entities.order.Order order = new entities.order.Order(
                1, "madara@god.com", "Hidden Leaf Village",
                new ArrayList<>(), new ArrayList<>(), entities.order.Order.PaymentMethod.CREDIT_CARD
        );
        assertEquals("PLACED", order.getStatus().toString());

        order.updateStatus(entities.order.Order.Status.SHIPPED);
        order.updateStatus(entities.order.Order.Status.PLACED);
        assertEquals("SHIPPED", order.getStatus().toString());

        assertArrayEquals(new String[]{
                "Order ID: 1 status updated to SHIPPED",
                "Invalid status update", ""
        }, Arrays.stream(outContent.toString(StandardCharsets.UTF_8).split("\r")).map(String::trim).toArray());

        order.addProduct(inventory.getProduct(1).orElse(product), 10);
        order.addProduct(inventory.getProduct(2).orElse(product), 30);
        assertEquals(10600, order.getTotalPrice());
        try{
            order.removeProduct(inventory.getProduct(3).orElse(product));
        }catch (Exception e){
            assertEquals("Product not found in order", e.getMessage());
        }
    }
}