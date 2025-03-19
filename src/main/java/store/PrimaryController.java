package store;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import store.product.Product;

public class PrimaryController {
    @FXML
    private Button switchButton;
    @FXML
    private Button addProductButton;

    private final PrimaryControllerState state = PrimaryControllerState.getInstance();

    @FXML
    private GridPane productGrid;

    private boolean isAdmin;

    public void initialize() {
        this.restoreState();
        List<Product> products;

        try {
            // Load the products from JSON
            products = Product.getProductsFromJson("products.json");
        } catch (Exception e) {
            System.out.println("Couldn't load the products.");
            e.printStackTrace();
            return;
        }

        // Populate the GridPane with product data
        int row = 0; // Start at the first row
        int column = 0; // Start at the first column
        for (Product product : products) {
            // Create a VBox for each product
            VBox productBox = new VBox();
            //productBox.setSpacing(5); // Add some spacing between elements

            // Add labels for product details
            Label nameLabel = new Label("Name: " + product.getName());
            Label priceLabel = new Label("Price: $" + product.getPrice());
            Label typeLabel = new Label("Type: " + product.getType());
            Label detailsLabel = new Label("Details: " + product.getDetails());

            // Add the labels to the VBox
            productBox.getChildren().addAll(nameLabel, priceLabel, typeLabel, detailsLabel);

            // Add the VBox to the GridPane
            productGrid.add(productBox, column, row);

            // Update row and column positions for the grid layout
            column++;
            if (column == 4) { // Limit to 4 columns, then go to the next row
                column = 0;
                row++;
            }
        }
    }

    private void restoreState() {
        this.isAdmin = !state.isAdmin();
        this.switchRole();
    }

    private void saveState() {
        state.setAdmin(this.isAdmin);
    }

    @FXML 
    public void switchRole() {
        if (this.isAdmin) {
            switchToUser();
            return;
        }
        switchToAdmin();
    }

    @FXML
    public void addProductButtonOnAction() {
        this.saveState();
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("creatingProduct.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) this.addProductButton.getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions (e.g., FXML file not found)
        }
    }

    private void switchToAdmin() {
        this.isAdmin = true;
        this.addProductButton.setVisible(true);
        this.switchButton.setText("Switch to user");
    }

    private void switchToUser() {
        this.isAdmin = false;
        this.addProductButton.setVisible(false);
        this.switchButton.setText("Switch to admin");
    }
}
