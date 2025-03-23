package store.order;

import store.product.Product;

public class BasicOrder implements Order {
    private final Product product;
    private final int quantity;

    public BasicOrder(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public double getCost() {
        return quantity * product.getPrice(); // Total cost = quantity * product price
    }

    @Override
    public String getDescription() {
        return "Order for " + quantity + " x " + product.getName() +
               " (" + product.getProductType() + ") at $" + product.getPrice() + " each.";
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}