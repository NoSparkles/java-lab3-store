package store.order;

public class Packaging extends OrderDecorator {
    public Packaging(Order order) {
        super(order);
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.50; // Adds $0.50 for packaging
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " with Packaging";
    }
}