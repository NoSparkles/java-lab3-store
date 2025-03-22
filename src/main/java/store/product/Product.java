package store.product;

public abstract class Product {
    public enum Type {
        Keyboard,
        Mouse,
        MousePad;
    }
    private static int nextId;
    private int id;
    private final String name;
    private final double price;
    private final Type productType;
    private final String description;

    public Product(String name, double price, Type productType, String description) {
        Product.incrementNextId();
        this.setId();
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.description = description;
    }

    private static void incrementNextId() {
        ++Product.nextId;
    }

    static void setNextId(int length) {
        Product.nextId = length;
    }

    private void setId() {
        this.id = Product.nextId();
    }

    public static int nextId() {
        return Product.nextId;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public Type getProductType() {
        return this.productType;
    }

    public String getDescription() {
        return this.description;
    }

    // Abstraktus metodas, kurį reikia įgyvendinti konkrečiose klasėse
    public abstract String getDetails();
}
