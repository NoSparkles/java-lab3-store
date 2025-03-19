package store.product;

public class MousePad extends Product {
    private String material;
    private boolean isGaming;

    public MousePad(String name, double price, String description, String material, boolean isGaming) {
        super(name, price, Type.Mousepad, description);
        this.material = material;
        this.isGaming = isGaming;
    }

    @Override
    public String getDetails() {
        return "Material: " + this.material + ", Gaming: " + (this.isGaming ? "Yes" : "No");
    }
}
