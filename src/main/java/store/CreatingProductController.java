package store;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import store.product.Product;
import store.product.ProductFactory;
import store.product.ProductInventory;
import store.product.ProductObserver;

public class CreatingProductController {
    @FXML
    private Button goBackButton;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField description;
    @FXML
    private ChoiceBox<String> productType;
    @FXML
    private Label labelDetails1;
    @FXML
    private TextField textFieldDetails1;
    @FXML
    private Label labelDetails2;
    @FXML
    private TextField textFieldDetails2;
    @FXML
    private Button createButton;

    private final ProductObserver observer = ProductObserver.getInstance();
    private final ProductInventory inventory = ProductInventory.getInstance();

    @FXML
    public void initialize() {
        this.productType.setItems(FXCollections.observableArrayList("Keyboard", "Mouse", "MousePad"));
        this.productType.setValue("Keyboard");
        this.productType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.productTypeOnChange(newValue);
        });

        this.price.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.priceOnExit();
            }
        });
    }

    @FXML
    public void nameOnTyped() {
        String validatedText = validateStringField(this.name.getText(), 30);
        this.name.setText(validatedText);
        this.name.positionCaret(validatedText.length());
    }

    @FXML
    public void priceOnTyped() {
        String validatedText = validateDoubleField(this.price.getText());
        this.price.setText(validatedText);
        this.price.positionCaret(validatedText.length());
    }

    public void priceOnExit() {
        try {
            double num = Double.parseDouble(this.price.getText());
            this.price.setText(String.valueOf(Math.round(num * 100.0) / 100.0));
        } catch (NumberFormatException e) { }
    }

    @FXML
    public void descriptionOnTyped() {
        String validatedText = validateStringField(this.description.getText(), 30);
        this.description.setText(validatedText);
        this.description.positionCaret(validatedText.length());
    }

    @FXML
    public void details1OnTyped() {
        String validatedText = validateStringField(this.textFieldDetails1.getText(), 30);
        this.textFieldDetails1.setText(validatedText);
        this.textFieldDetails1.positionCaret(validatedText.length());
    }

    @FXML
    public void details2OnTyped() {
        String validatedText;
        if (this.productType.getValue().equals("Mouse")) {
            validatedText = validateIntField(this.textFieldDetails2.getText());
        }
        else {
            validatedText = validateStringField(this.textFieldDetails2.getText(), 30);
        }
        this.textFieldDetails2.setText(validatedText);
        this.textFieldDetails2.positionCaret(validatedText.length());
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

    private String validateStringField(String value, int max) {
        if (value.length() == 0) {
            return "";
        }
        if (value.length() > max) {
            return value.substring(0, max);
        }
        return value;
    }

    private String validateIntField(String value) {
        if (value.length() == 0) {
            return "";
        }
        try {
            int num = Integer.parseInt(value);
            if (num < 0) {
                return "0";
            }
            else if (num > 1000000) {
                return "1000000";
            }
        }
        catch (NumberFormatException exception) {
            return "";
        }
        return value;
    }

    private String validateDoubleField(String value) {
        if (value.length() == 0) {
            return "";
        }
        try {
            double num = Double.parseDouble(value);
            if (num < 0) {
                return "0";
            } else if (num > 1000000) {
                return "1000000";
            }
        }
        catch (NumberFormatException exception) {
            return "";
        }
        return value;
    }
    
    

    private void productTypeOnChange(String value) {
        this.textFieldDetails1.setText("");
        this.textFieldDetails2.setText("");
        switch (value) {
            case "Keyboard":
                this.labelDetails1.setText("Brand:");
                this.labelDetails2.setText("Color:");
                break;
            case "Mouse":
                this.labelDetails1.setText("Type:");
                this.labelDetails2.setText("DPI:");
                break;
            case "MousePad":
                this.labelDetails1.setText("Brand:");
                this.labelDetails2.setText("Material:");
                break;
            default:
        }
    }

    @FXML
    public void createButtonOnAction() {

        setTextFieldDefaultValue(this.name, "Unknown");
        setTextFieldDefaultValue(this.price, "0.01");
        setTextFieldDefaultValue(this.description, "-");
        setTextFieldDefaultValue(this.textFieldDetails1, "-");
        if (this.productType.getValue().equals("Mouse")) {
            setTextFieldDefaultValue(this.textFieldDetails2, "0");
        }
        else {
            setTextFieldDefaultValue(this.textFieldDetails2, "-");
        }
        

        Product product = ProductFactory.createProduct(
            this.name.getText(),
            this.productType.getValue(), 
            Double.parseDouble(this.price.getText()),
            this.description.getText(),
            this.textFieldDetails1.getText(), 
            this.textFieldDetails2.getText()
        );

        this.inventory.addProductToJson(product);
        this.observer.productAdded();
    }

    void setTextFieldDefaultValue(TextField textField, String value) {
        if (textField.getText().strip().length() == 0) {
            textField.setText(value);
        }
    }
}
