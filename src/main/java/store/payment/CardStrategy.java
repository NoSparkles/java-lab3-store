package store.payment;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CardStrategy implements PaymentStrategy {
    private TextField cardNumberField;
    private TextField expiryField;
    private TextField cvvField;

    @Override
    public void addPaymentFields(VBox paymentBox) {
        this.cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number");

        this.expiryField = new TextField();
        expiryField.setPromptText("MM/YY");

        this.cvvField = new TextField();
        cvvField.setPromptText("CVV");

        paymentBox.getChildren().clear(); // Clear previous fields
        paymentBox.getChildren().addAll(cardNumberField, expiryField, cvvField); // Add fields to the VBox
    }

    @Override
    public void buy(Label feedbackLabel) {
        String cardNumber = cardNumberField.getText();
        String expiry = expiryField.getText();
        String cvv = cvvField.getText();

        if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
            feedbackLabel.setText("All card details are required.");
            feedbackLabel.setStyle("-fx-text-fill: red;"); // Red text for error
        } else {
            feedbackLabel.setText("Processing Card payment with card number: " + cardNumber);
            feedbackLabel.setStyle("-fx-text-fill: green;"); // Green text for success
        }
    }
}