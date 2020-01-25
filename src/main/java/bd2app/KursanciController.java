package bd2app;

import bd2app.model.UslugiEntity;
import bd2app.model.UzytkownicyEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KursanciController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button myAccountButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button deleteClientBtn;
    @FXML
    private Button editClientBtn;
    @FXML
    private Button addClientBtn;
    @FXML
    private TableView<UzytkownicyEntity> usersList;
    @FXML
    private TableColumn<UzytkownicyEntity, String> name;
    @FXML
    private TableColumn<UzytkownicyEntity, String> surname;
    @FXML
    private TableColumn<UzytkownicyEntity, String> pesel;
    @FXML
    private TableColumn<UzytkownicyEntity, String> pkk;
    @FXML
    private TableColumn<UzytkownicyEntity, Integer> phone;
    @FXML
    private TableColumn<UzytkownicyEntity, String> mail;
    @FXML
    private TableColumn<UzytkownicyEntity, Integer> wallet;

    @FXML
    void goBack(ActionEvent event) throws IOException {
        App.goBack();
    }

    @FXML
    void addClient() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Dodaj kursanta");
        FXMLLoader loader = App.getLoader("rejestracja");
        stage.setScene(new Scene(loader.load()));
        loader.<RejestracjaController>getController().fromAdmin();
        stage.show();
        stage.setOnCloseRequest((event) -> {
            loadClients();
        });
    }

    @FXML
    void deleteClient() throws IOException {
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Usuwanie kursanta");
        alert2.setHeaderText("Próbujesz usunąc kursanta");
        alert2.setContentText("Razem z kursantem usuna sie wszystkie jego rezerwacje i platnosci!");
        Optional<ButtonType> result = alert2.showAndWait();
        if (result.get() == ButtonType.OK) {
            UzytkownicyEntity ue = usersList.getSelectionModel().getSelectedItem();
            App.udao.delete(ue);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuwanie kursanta");
            alert.setHeaderText(null);
            alert.setContentText("Kursant zostal usuniety!");
            alert.showAndWait();
            loadClients();
        } else {
            alert2.close();
        }
    }

    @FXML
    void editClient() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Edytuj kursanta");
        FXMLLoader loader = App.getLoader("rejestracja");
        stage.setScene(new Scene(loader.load()));
        loader.<RejestracjaController>getController().toUpdate(usersList.getSelectionModel().getSelectedItem());
        stage.show();
        stage.setOnCloseRequest((event) -> {
            loadClients();
        });
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        App.logout();
    }

    @FXML
    void showMyAccount(ActionEvent event) throws IOException {
        App.setRoot("konto");
    }

    @FXML
    void initialize() {
        loadClients();
        if(!App.getUserType().equals("Administrator") && !App.getUserType().equals("Biurowy")) {
            deleteClientBtn.setVisible(false);
            editClientBtn.setVisible(false);
            addClientBtn.setVisible(false);
        }
        else {
            deleteClientBtn.setVisible(true);
            editClientBtn.setVisible(true);
            addClientBtn.setVisible(true);
        }
    }

    void loadClients() {
        usersList.getItems().clear();
        List<UzytkownicyEntity> users = App.udao.findAll();
        users = users.stream().filter(user -> user.getTypUzytkownika() == 0).collect(Collectors.toList());
        ObservableList<UzytkownicyEntity> data = FXCollections.observableArrayList(users);
        usersList.setItems(data);
        name.setCellValueFactory(new PropertyValueFactory("imię"));
        surname.setCellValueFactory(new PropertyValueFactory("nazwisko"));
        pesel.setCellValueFactory(new PropertyValueFactory("pesel"));
        pkk.setCellValueFactory(new PropertyValueFactory("pkk"));
        phone.setCellValueFactory(new PropertyValueFactory("telefon"));
        mail.setCellValueFactory(new PropertyValueFactory("email"));
        wallet.setCellValueFactory(new PropertyValueFactory("eportfel"));
    }
}
