package entities.inventory;

import java.util.Map;
import java.util.Objects;
import entities.product.Product;
import file_handler.FileHandler;

public class InventoryHandler {
    private static final Inventory defaultInventory = new Inventory(){{
        try{
            addProduct(new Product(1, "Laptop", 1000, Product.Category.ELECTRONICS), 10);
            addProduct(new Product(2, "T-shirt", 20, Product.Category.CLOTHING), 100);
            addProduct(new Product(3, "Apple", 1, Product.Category.FOOD), 110);
            addProduct(new Product(4, "Smartphone", 800, Product.Category.ELECTRONICS), 50);
            addProduct(new Product(5, "Jeans", 40, Product.Category.CLOTHING), 75);
            addProduct(new Product(6, "Banana", 0.5, Product.Category.FOOD), 200);
            addProduct(new Product(7, "Headphones", 150, Product.Category.ELECTRONICS), 80);
            addProduct(new Product(8, "Jacket", 60, Product.Category.CLOTHING), 50);
            addProduct(new Product(9, "Orange", 0.8, Product.Category.FOOD), 150);
            addProduct(new Product(10, "Speaker", 300, Product.Category.ELECTRONICS), 40);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }};

    private static Inventory currentInventory;

    public static Inventory getInventory(){
        if(Objects.isNull(currentInventory)){
            currentInventory = (Inventory) FileHandler.getInstance().readNext(FileHandler.ObjectType.INVENTORY);
            if(Objects.isNull(currentInventory))
                currentInventory = defaultInventory;
        }
        return currentInventory;
    }

    private static void saveInventory(Inventory inv){
        FileHandler fileHandler = FileHandler.getInstance();
        fileHandler.deleteAllObjects(FileHandler.ObjectType.INVENTORY);
        fileHandler.writeObjectToFile(inv, FileHandler.ObjectType.INVENTORY);
    }
    public static void saveInventory(){
        saveInventory(currentInventory);
    }

    public static void updateProductStock(long productId, int newStock, Inventory.Operations operation){
        Inventory inv = getInventory();
        try{
            inv.updateProductStock(productId, newStock, operation);
            saveInventory(inv);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void addNewProduct(Product product, int stock){
        Inventory inv = getInventory();
        try{
            inv.addProduct(product, stock);
            saveInventory(inv);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
