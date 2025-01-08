package entities.product;

import java.io.Serializable;

public record Product(long id, String name, double price, Category category) implements Serializable {
    public static enum Category {
        ELECTRONICS,
        CLOTHING,
        FOOD
    };

    public Product {
        if(price <= 0) {
            throw new IllegalArgumentException("Price cannot be negative or equal to 0");
        }
        if(category.ordinal() >= Category.values().length) {
            throw new IllegalArgumentException("Invalid category");
        }
    }

    public int getCategory() {
        return category.ordinal();
    }

    public void display(){
        System.out.println("Product ID: " + id);
        System.out.println("Product Name: " + name);
        System.out.println("Product Price: " + price);
        System.out.println("Product Category: " + category);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
