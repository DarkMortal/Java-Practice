package entities.product;

public class ProductBuilder {
    private long id;
    private String name;
    private double price;
    private Product.Category category;

    public ProductBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setCategory(Product.Category category) {
        this.category = category;
        return this;
    }

    public Product.Category getCategory(){
        return this.category;
    }

    public Product build() {
        return new Product(id, name, price, category);
    }
}
