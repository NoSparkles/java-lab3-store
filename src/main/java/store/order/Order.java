package store.order;

import store.product.Product;

public interface Order {
    double getCost(); // Calculates the total cost
    String getDescription(); // Provides a summary of the order
    Product getProduct(); // Gets the product
    int getQuantity(); // Gets the quantity of the order
}