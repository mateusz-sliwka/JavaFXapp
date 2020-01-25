package bd2app;

import bd2app.App;
import bd2app.model.TransakcjeEntity;
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

import static bd2app.App.loadFXML;

public class UslugiController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button myAccountButton;
    @FXML
    private Button returnButton;
    @FXML
    private TableView<UslugiEntity> servicesList;
    @FXML
    private TableColumn<UslugiEntity, String> name;
    @FXML
    private TableColumn<UslugiEntity, Integer> price;
    @FXML
    private TableColumn<UslugiEntity, String> category;
    @FXML
    private Button addServiceButton;
    @FXML
    private Button deleteServiceButton;
    @FXML
    private Button editServiceButton;

    @FXML
    void addService(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Nowa usluga");
        stage.setScene(new Scene(loadFXML("nowaUsluga")));
        stage.show();
        stage.setOnCloseRequest(ev -> {
            loadServices();
        });
    }

    @FXML
    void editService() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Edytuj usluge");
        FXMLLoader loader = App.getLoader("nowaUsluga");
        stage.setScene(new Scene(loader.load()));
        loader.<NowaUslugaController>getController().toUpdate(servicesList.getSelectionModel().getSelectedItem());
        stage.show();
        stage.setOnCloseRequest(ev -> {
            loadServices();
        });
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        App.goBack();
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
    void deleteService() {
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Usuwanie usługi");
        alert2.setHeaderText("Próbujesz usunąc usługę");
        alert2.setContentText("Razem z usługą usuną się wszystkie rezerwacje do niej przypisane!");
        Optional<ButtonType> result = alert2.showAndWait();
        if (result.get() == ButtonType.OK) {
            UslugiEntity ue = servicesList.getSelectionModel().getSelectedItem();
            App.usdao.delete(ue);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuwanie uslugi");
            alert.setHeaderText(null);
            alert.setContentText("Usluga zostala usunieta!");
            alert.showAndWait();
            loadServices();
        } else {
            alert2.close();
        }
    }

    @FXML
    void initialize() {
        loadServices();
        if (!App.getUserType().equals("Administrator")) {
            addServiceButton.setVisible(false);
            deleteServiceButton.setVisible(false);
            editServiceButton.setVisible(false);
        } else {
            addServiceButton.setVisible(true);
            deleteServiceButton.setVisible(true);
            editServiceButton.setVisible(true);
        }
    }

    void loadServices() {
        servicesList.getItems().clear();
        List<UslugiEntity> services = App.usdao.findAll();
        ObservableList<UslugiEntity> data = FXCollections.observableArrayList(services);
        servicesList.setItems(data);
        name.setCellValueFactory(new PropertyValueFactory("nazwa"));
        price.setCellValueFactory(new PropertyValueFactory("cena"));
        category.setCellValueFactory(new PropertyValueFactory("kategoriaByKategoriaid"));
    }
}
