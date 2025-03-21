package store.product;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import store.App;

public class ProductInventory {
    // Singleton instance
    private static ProductInventory instance;

    // Path to the JSON resource
    private String resourcePath;

    // Private constructor to prevent instantiation
    private ProductInventory() {
    }

    // Public method to access the singleton instance
    public static synchronized ProductInventory getInstance() {
        if (instance == null) {
            instance = new ProductInventory();
        }
        return instance;
    }

    // Set the resource path for the JSON file
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    // Load products from JSON
    private List<Product> loadProductsFromJson() throws Exception {
        List<Product> products = new ArrayList<>();

        if (resourcePath == null || resourcePath.isEmpty()) {
            throw new IllegalArgumentException("Resource path not set.");
        }

        // Load the file as a resource stream
        InputStream resourceStream = App.class.getResourceAsStream(resourcePath);
        if (resourceStream == null) {
            throw new IllegalArgumentException("File not found: " + resourcePath);
        }

        // Read the file content
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        // Parse the JSON data
        JSONArray jsonArray = new JSONArray(content.toString());
        Product.setNextId(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject productJson = jsonArray.getJSONObject(i);
            String productType = productJson.getString("productType");
            String name = productJson.getString("name");
            double price = productJson.getDouble("price");
            String description = productJson.getString("description");

            JSONObject details = productJson.getJSONObject("details");

            switch (productType) {
                case "Keyboard":
                    String keyboardBrand = details.getString("brand");
                    String color = details.getString("color");
                    products.add(new Keyboard(name, price, description, keyboardBrand, color));
                    break;
                case "Mouse":
                    String mouseType = details.getString("type");
                    int dpi = details.getInt("dpi");
                    products.add(new Mouse(name, price, description, mouseType, dpi));
                    break;
                case "MousePad":
                    String mousePadBrand = details.getString("brand");
                    String material = details.getString("material");
                    products.add(new MousePad(name, price, description, mousePadBrand, material));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown product type: " + productType);
            }
        }

        return products;
    }

    // Get products while synchronizing with JSON
    public synchronized List<Product> getProducts() {
        try {
            return loadProductsFromJson();
        } catch (Exception e) {
            System.err.println("Failed to load products: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
