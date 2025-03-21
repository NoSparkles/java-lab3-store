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

    @FXML
    public void initialize() {
        this.productType.setItems(FXCollections.observableArrayList("Keyboard", "Mouse", "MousePad"));
        this.productType.setValue("Keyboard");
        this.productType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.productTypeOnChange(newValue);
        });
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

    private void productTypeOnChange(String value) {
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
        
    }
}
