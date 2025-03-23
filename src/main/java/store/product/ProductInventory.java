package store.product;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProductInventory {
    private static ProductInventory instance;
    private final String filePath = "products.json"; // Writable file path

    private ProductInventory() {
    }

    public static synchronized ProductInventory getInstance() {
        if (ProductInventory.instance == null) {
            ProductInventory.instance = new ProductInventory();
        }
        return ProductInventory.instance;
    }

    // Load products from JSON file
    public synchronized List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            // Parse JSON and populate product list
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
                        String keyboardBrand = details.getString("Brand");
                        String color = details.getString("Color");
                        products.add(new Keyboard(name, price, description, keyboardBrand, color));
                        break;
                    case "Mouse":
                        String mouseType = details.getString("Type");
                        int dpi = details.getInt("DPI");
                        products.add(new Mouse(name, price, description, mouseType, dpi));
                        break;
                    case "MousePad":
                        String mousePadBrand = details.getString("Brand");
                        String material = details.getString("Material");
                        products.add(new MousePad(name, price, description, mousePadBrand, material));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown product type: " + productType);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load products: " + e.getMessage());
        }
        return products;
    }

    // Add a product to the JSON file
    public synchronized void addProductToJson(Product product) {
        try {
            // Read existing JSON content
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
            }

            // Parse JSON and add new product
            JSONArray jsonArray = new JSONArray(content.toString());
            JSONObject productJson = new JSONObject();
            productJson.put("id", product.getId());
            productJson.put("productType", product.getProductType().toString());
            productJson.put("name", product.getName());
            productJson.put("price", product.getPrice());
            productJson.put("description", product.getDescription());

            // Parse product details
            JSONObject detailsJson = new JSONObject();
            String[] detailsPairs = product.getDetails().split(", ");
            for (String pair : detailsPairs) {
                String[] keyValue = pair.split(": ");
                detailsJson.put(keyValue[0].trim(), keyValue[1].trim());
            }
            productJson.put("details", detailsJson);

            // Append to the array and save back
            jsonArray.put(productJson);
            try (FileWriter writer = new FileWriter(filePath, StandardCharsets.UTF_8)) {
                writer.write(jsonArray.toString(4)); // Pretty print with 4-space indentation
            }
        } catch (Exception e) {
            System.err.println("Failed to add product: " + e.getMessage());
        }
    }
}