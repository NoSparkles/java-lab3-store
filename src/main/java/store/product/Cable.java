package store.product;

public class Cable extends Product {
    private String type;
    private double length;

    public Cable(String name, double price, String description, String type, double length) {
        super(name, price, Type.Cable, description);
        this.type = type;
        this.length = length;
    }

    @Override
    public String getDetails() {
        return "Type: " + this.type + ", Length: " + this.length + " meters";
    }
}

