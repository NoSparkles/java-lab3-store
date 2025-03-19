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

public abstract class Product {
    public enum Type {
        Cable,
        Keyboard,
        Monitor,
        Mouse,
        Mousepad;
    }
    private static int count;
    private int id;
    private String name;
    private double price;
    private Type type;
    private String description;

    public Product(String name, double price, Type type, String description) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
    }

    // Bendras metodas, kurį gali naudoti visos produktų klasės

    public static void setCount(int c) {
        Product.count = c;
    }

    public static int getCount() {
        return Product.count;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public Type getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    // Abstraktus metodas, kurį reikia įgyvendinti konkrečiose klasėse
    public abstract String getDetails();

    public static List<Product> getProductsFromJson(String resourcePath) throws Exception {
        List<Product> products = new ArrayList<>();

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

        Product.setCount(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject productJson = jsonArray.getJSONObject(i);
            String type = productJson.getString("type");
            String name = productJson.getString("name");
            double price = productJson.getDouble("price");
            String description = productJson.getString("description");

            JSONObject details = productJson.getJSONObject("details");

            switch (type) {
                case "Mouse":
                    String mouseType = details.getString("type");
                    int dpi = details.getInt("dpi");
                    products.add(new Mouse(name, price, description, mouseType, dpi));
                    break;

                case "Keyboard":
                    boolean isMechanical = details.getBoolean("isMechanical");
                    products.add(new Keyboard(name, price, description, isMechanical));
                    break;

                case "Monitor":
                    int screenSize = details.getInt("screenSize");
                    String resolution = details.getString("resolution");
                    products.add(new Monitor(name, price, description, screenSize, resolution));
                    break;

                case "MousePad":
                    String material = details.getString("material");
                    boolean isGaming = details.getBoolean("isGaming");
                    products.add(new MousePad(name, price, description, material, isGaming));
                    break;

                case "Cable":
                    String cableType = details.getString("type");
                    double length = details.getDouble("length");
                    products.add(new Cable(name, price, description, cableType, length));
                    break;

                default:
                    throw new IllegalArgumentException("Unknown product type: " + type);
            }
        }

        return products;
    }


}
