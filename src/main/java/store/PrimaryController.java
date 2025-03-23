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
import store.product.ProductInventory;

public class PrimaryController {
    @FXML
    private Button switchButton;
    @FXML
    private Button addProductButton;
    @FXML
    private GridPane productGrid;

    private final ProductInventory inventory = ProductInventory.getInstance();
    private List<Product> products;

    private final PrimaryControllerState state = PrimaryControllerState.getInstance();
    private boolean isAdmin;

    @FXML
    public void initialize() {
        this.restoreState();

        try {
            // Load the products from JSON
            this.products = inventory.getProducts();
        } catch (Exception e) {
            System.out.println("Couldn't load the products.");
            e.printStackTrace();
            return;
        }

        // Populate the GridPane with product data
        int row = 0; // Start at the first row
        int column = 0; // Start at the first column
        for (Product product : this.products) {
            // Create a VBox for each product
            VBox productBox = new VBox();
            //productBox.setSpacing(5); // Add some spacing between elements

            // Add labels for product details
            Label nameLabel = new Label("Name: " + product.getName());
            Label priceLabel = new Label("Price: $" + product.getPrice());
            Label typeLabel = new Label("ProductType: " + product.getProductType());
            Label detailsLabel = new Label("Details: " + product.getDetails());

            // Add the labels to the VBox
            productBox.getChildren().addAll(nameLabel, priceLabel, typeLabel, detailsLabel);

            productBox.setOnMousePressed(event -> {
                this.changeToCheckoutView(product);
            });        

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

    private void changeToCheckoutView(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("checkoutView.fxml"));

            Parent root = loader.load();

            CheckoutController controller = loader.getController();
            controller.setProduct(product);

            Stage stage = (Stage) this.productGrid.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
