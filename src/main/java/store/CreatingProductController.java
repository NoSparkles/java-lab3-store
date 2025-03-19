package store;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreatingProductController {
    @FXML
    private Button goBackButton;
    
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
}
