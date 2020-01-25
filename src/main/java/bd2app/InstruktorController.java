package bd2app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class InstruktorController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button myAccountButton;
    @FXML
    private Button servicesButton;
    @FXML
    private Button reservationsButton;
    @FXML
    private Button instructorsButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button transactionsButton;

    @FXML
    void logout(ActionEvent event) throws IOException {
        App.setUserType("");
        App.setRoot("logowanie");
    }

    @FXML
    void showInstructors(ActionEvent event) throws IOException {
        App.setRoot("instruktorzy");
    }

    @FXML
    void showMyAccount(ActionEvent event) throws IOException {
        App.setRoot("konto");
    }

    @FXML
    void showReservations(ActionEvent event) throws IOException {
        App.setRoot("rezerwacje");
    }

    @FXML
    void showServices(ActionEvent event) throws IOException {
        App.setRoot("uslugi");
    }

    @FXML
    void showTransactions(ActionEvent event) throws IOException {
        App.setRoot("transakcje");
    }

    @FXML
    public void initialize() {
    }


}