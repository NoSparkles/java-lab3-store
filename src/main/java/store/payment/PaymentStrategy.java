package store.payment;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public interface PaymentStrategy {
    void addPaymentFields(VBox paymentBox);
    void buy(Label feedbackLabel);
}