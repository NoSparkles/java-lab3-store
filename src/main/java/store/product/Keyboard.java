package store.product;

public class Keyboard extends Product {
    private String brand;
    private String color;

    public Keyboard(String name, double price, String description, String brand, String color) {
        super(name, price, Type.Keyboard, description);
        this.brand = brand;
        this.color = color;
    }

    @Override
    public String getDetails() {
        return "Brand: " + this.brand + ", Color: " + this.color;
    }
}

