package store.product;

public class ProductFactory {
    public static Product createProduct(String name, String productType, double price, String description, String details1, String details2) {
        switch (productType) {
            case "Keyboard":
                return createKeyboard(name, price, description, details1, details2);
            case "Mouse":
                return createMouse(name, price, description, details1, Integer.parseInt(details2));
            case "MousePad":
                return createMousePad(name, price, description, details1, details2);
            default:
                System.out.println("Product was not created.");
                return null;
        }
    }

    private static Product createKeyboard(String name, double price, String description, String brand, String color) {
        return new Keyboard(name, price, description, brand, color);
    }

    private static Product createMouse(String name, double price, String description, String type, int DPI) {
        return new Mouse(name, price, description, type, DPI);
    }

    private static Product createMousePad(String name, double price, String description, String brand, String material) {
        return new MousePad(name, price, description, brand, material);
    }
}
