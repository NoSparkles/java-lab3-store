package store.product;

public class Monitor extends Product {
    private int screenSize;
    private String resolution;

    public Monitor(String name, double price, String description, int screenSize, String resolution) {
        super(name, price, Type.Monitor, description);
        this.screenSize = screenSize;
        this.resolution = resolution;
    }

    @Override
    public String getDetails() {
        return "Screen Size: " + this.screenSize + " inches, Resolution: " + this.resolution;
    }
}

