package bd2app;

import java.io.IOException;

import bd2app.dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static bd2app.App.loadFXML;

public class LogowanieController {
    @FXML
    private TextField emailTB;
    @FXML
    private PasswordField passwordTB;
    ObservableList<String> accountTypes = FXCollections.observableArrayList("Kursant", "Instruktor", "Biurowy", "Ksiegowy", "Administrator");
    @FXML
    private ComboBox accountTypeCB;
    @FXML
    private Label errorLabel;
    @FXML
    private SplitPane splitPane;

    @FXML
    private void initialize() {
        accountTypeCB.setItems(accountTypes);
        splitPane.setStyle("-fx-box-border: transparent;");
    }

    @FXML
    private void registerForm() throws IOException {
        App.setRoot("rejestracja");
    }

    @FXML
    private void login() throws IOException {
        String email = emailTB.getText();

        String password = passwordTB.getText();
        String accountType = "Kursant";
        int typ=3;
        String selectedType = "Administrator";
        if( accountTypeCB.getSelectionModel().getSelectedItem()!=null)
      selectedType = accountTypeCB.getSelectionModel().getSelectedItem().toString();

        if(selectedType.equals("Kursant"))
            typ=0;
        else if (selectedType.equals("Biurowy"))
                typ=1;
        else if (selectedType.equals("Ksiegowy"))
            typ=2;
        else if (selectedType.equals("Administrator"))
            typ=3;
        if (accountTypeCB.getSelectionModel().getSelectedItem() != null)
            accountType = accountTypeCB.getSelectionModel().getSelectedItem().toString();
        int verification = -1;
        if (accountType.equals("Instruktor"))
            verification = App.idao.login(email, password);
        else if (accountTypeCB.getSelectionModel().getSelectedItem() != null)
            verification = App.udao.login(email, password,typ);
        if (verification == -1) {
            errorLabel.setVisible(true);
            passwordTB.setText("");
        } else {
            App.setUserType(accountType);
            App.setUserID(verification);
            App.setRoot(accountType.toLowerCase());
        }
    }
}
