package store.product;

public class Keyboard extends Product {
    private boolean isMechanical;

    public Keyboard(String name, double price, String description, boolean isMechanical) {
        super(name, price, Type.Keyboard, description);
        this.isMechanical = isMechanical;
    }

    @Override
    public String getDetails() {
        return "Mechanical: " + (this.isMechanical ? "Yes" : "No");
    }
}

