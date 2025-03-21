package store.product;

public class Mouse extends Product {
    private final String type; // E.g., Optical, Laser
    private final int dpi;     // DPI resolution

    public Mouse(String name, double price, String description, String type, int dpi) {
        super(name, price, Type.Mouse, description);
        this.type = type;
        this.dpi = dpi;
    }

    @Override
    public String getDetails() {
        return "Type: " + this.type + ", DPI: " + this.dpi;
    }
}

