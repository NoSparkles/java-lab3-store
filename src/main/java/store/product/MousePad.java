package store.product;

public class MousePad extends Product {
    private final String brand;
    private final String material;

    public MousePad(String name, double price, String description, String brand, String material) {
        super(name, price, Type.Mousepad, description);
        this.brand = brand;
        this.material = material;
    }

    @Override
    public String getDetails() {
        return "Brand: " + this.brand + ", Material: " + this.material;
    }
}
