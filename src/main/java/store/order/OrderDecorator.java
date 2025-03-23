package store.order;

import store.product.Product;

public abstract class OrderDecorator implements Order {
    protected Order decoratedOrder; // The order being decorated

    public OrderDecorator(Order order) {
        this.decoratedOrder = order;
    }

    @Override
    public double getCost() {
        return decoratedOrder.getCost(); // Delegates cost calculation to the wrapped order
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription(); // Delegates description to the wrapped order
    }

    @Override
    public Product getProduct() {
        return decoratedOrder.getProduct(); // Delegates product access to the wrapped order
    }

    @Override
    public int getQuantity() {
        return decoratedOrder.getQuantity(); // Delegates quantity access to the wrapped order
    }
}