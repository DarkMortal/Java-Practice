package entities.inventory;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import entities.product.Product;

public class Inventory implements Serializable {
    // these will be the final maps for implementing the stock level and which product is associated to which product id
    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final Map<Long, Integer> stockCollection = new ConcurrentHashMap<>();

    public void addProduct(Product product, int stock) throws Exception{
        if(!Objects.isNull(productMap.get(product.id())))
            throw new Exception("Product already exists");
        productMap.put(product.id(),product);
        stockCollection.put(product.id(),stock);
    }

    public static enum Operations{
        ADD, REMOVE
    }

    public void updateProductStock(long productId, int newStock, Operations operation) throws Exception {
        if(productMap.containsKey(productId)){
            switch (operation){
                case ADD:
                    stockCollection.put(productId, stockCollection.get(productId) + newStock);
                    break;
                case REMOVE:
                    stockCollection.put(productId, stockCollection.get(productId) - newStock);
                    break;
                default: throw new IllegalArgumentException("Invalid operation");
            }
        }else{
            throw new Exception("Product not found");
        }
    }

    public Optional<Product> getProduct(long productId) {
        return Optional.ofNullable(productMap.get(productId));
    }

    public int getStockCollectionLevel(long productId){
        return stockCollection.get(productId);
    }

    public Map<Long, Product> getLowStockProducts(int threshold){
        Map<Long,Product> lowStockProducts = new ConcurrentHashMap<>();
        for(Map.Entry<Long, Integer> entry : stockCollection.entrySet()){
            if(entry.getValue() < threshold){
                lowStockProducts.put(entry.getKey(),productMap.get(entry.getKey()));
            }
        }

        return lowStockProducts;
    }

    public int getStock(long productId){
        return stockCollection.get(productId);
    }

    public void display(){
        for(Map.Entry<Long,Product> iterator : productMap.entrySet()){
            iterator.getValue().display();
            System.out.println("Stock: " + stockCollection.get(iterator.getKey()) + "\n");
        }
    }
}
