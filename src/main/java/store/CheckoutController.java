package store;


import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import store.order.BasicOrder;
import store.order.Customization;
import store.order.Order;
import store.order.Packaging;
import store.product.Product;

public class CheckoutController {
    @FXML
    private Button goBackButton;
    @FXML
    private Label name;
    @FXML
    private Label productType;
    @FXML
    private Label price;
    @FXML
    private Label description;
    @FXML
    private CheckBox packaging;
    @FXML
    private CheckBox customization;
    @FXML
    private ChoiceBox<String> paymentMethod;
    @FXML
    private TextField quantity;

    private Product product;

    public void setProduct(Product product) {
        this.product = product;

        this.setFields();
    }

    @FXML
    public void initialize() {
        this.paymentMethod.setItems(FXCollections.observableArrayList("Card", "PayPal"));
        this.paymentMethod.setValue("Card");
        this.quantity.setText("1");
    }

    @FXML
    public void goBackButtonOnAction() {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) this.goBackButton.getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions (e.g., FXML file not found)
        }
    }

    @FXML
    public void packagingOnAction() {
        this.adjustOrder();
    }

    @FXML
    public void customizationOnAction() {
        this.adjustOrder();
    }

    @FXML
    public void quntityOnTyped() {
        String validatedText = this.validateQuantity(this.quantity.getText());
        this.quantity.setText(validatedText);
        this.quantity.positionCaret(validatedText.length());
        this.adjustOrder();
    }

    private String validateQuantity(String value) {
        if (value.length() == 0) {
            return "";
        }
        try {
            int num = Integer.parseInt(value);
            if (num < 1) {
                return "1";
            }
            else if (num > 100) {
                return "100";
            }
        }
        catch (NumberFormatException exception) {
            return "1";
        }
        return value;
    }

    private void setFields() {
        this.name.setText(this.product.getName());
        this.price.setText(String.valueOf(this.product.getPrice()));
        this.description.setText(this.product.getDescription());
    }

    private void adjustOrder() {
        int quantityInt;
        if (this.quantity.getText().length() == 0) {
            quantityInt = 1;
        }
        else {
            quantityInt = Integer.parseInt(this.quantity.getText());
        }
        Order order = new BasicOrder(this.product, quantityInt);

        if (this.packaging.isSelected()) {
            order = new Packaging(order);
        }
        if (this.customization.isSelected()) {
            order = new Customization(order);
        }

        System.out.println("Order Description:");
        System.out.println(order.getDescription());
        this.price.setText(String.valueOf(Math.round(order.getCost() * 100.0) / 100.0));
    }
}
