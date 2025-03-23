package store.order;

public class Customization extends OrderDecorator {
    public Customization(Order order) {
        super(order);
    }

    @Override
    public double getCost() {
        return super.getCost() + 1.00; // Adds $1.00 for customization
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " with Customization";
    }
}