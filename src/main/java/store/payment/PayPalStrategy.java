package store.payment;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PayPalStrategy implements PaymentStrategy {
    private TextField emailField;

    @Override
    public void addPaymentFields(VBox paymentBox) {
        this.emailField = new TextField();
        this.emailField.setPromptText("Enter PayPal Email");

        paymentBox.getChildren().clear(); // Clear previous fields
        paymentBox.getChildren().add(this.emailField); // Add email field to the VBox
    }

    @Override
    public void buy(Label feedbackLabel) {
        String email = emailField.getText();
        if (email.isEmpty()) {
            feedbackLabel.setText("PayPal email is required.");
            feedbackLabel.setStyle("-fx-text-fill: red;"); // Red text for error
        } else {
            feedbackLabel.setText("Processing PayPal payment for email: " + email);
            feedbackLabel.setStyle("-fx-text-fill: green;"); // Green text for success
        }
    }
}