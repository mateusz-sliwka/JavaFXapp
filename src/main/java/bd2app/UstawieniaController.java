package bd2app;

import bd2app.model.TransakcjeEntity;
import bd2app.model.UstawieniaEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static bd2app.App.loadFXML;

public class UstawieniaController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button myAccountButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button newSettingButton;
    @FXML
    private Button editSettingButton;
    @FXML
    private TableView<UstawieniaEntity> settingsList;
    @FXML
    private TableColumn<UstawieniaEntity, String> name;
    @FXML
    private TableColumn<UstawieniaEntity, Integer> value;

    @FXML
    void goBack(ActionEvent event) throws IOException {
        App.goBack();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        App.logout();
    }

    @FXML
    void newTransaction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Nowe ustawienie");
        stage.setScene(new Scene(loadFXML("noweUstawienia")));
        stage.show();
        stage.setOnCloseRequest(ev -> {
            loadSettings();
        });
    }

    @FXML
    void showMyAccount(ActionEvent event) throws IOException {
        App.setRoot("konto");
    }

    @FXML
    void initialize() {
        loadSettings();
    }

    @FXML
    private void deleteSetting() {
        App.ustdao.delete(settingsList.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuwanie ustawienia");
        alert.setHeaderText(null);
        alert.setContentText("Ustawienie zostalo usuniete!");
        alert.showAndWait();
        loadSettings();
    }

    @FXML
    void editTransaction() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Edytuj ustawienie");
        FXMLLoader loader = App.getLoader("noweUstawienia");
        stage.setScene(new Scene(loader.load()));
        loader.<NoweUstawieniaController>getController().toUpdate(settingsList.getSelectionModel().getSelectedItem());
        stage.show();
        stage.setOnCloseRequest(ev -> {
            loadSettings();
        });
    }

    void loadSettings() {
        settingsList.getItems().clear();
        List<UstawieniaEntity> settings;
        settings = App.ustdao.findAll();
        ObservableList<UstawieniaEntity> data = FXCollections.observableArrayList(settings);
        settingsList.setItems(data);
        name.setCellValueFactory(new PropertyValueFactory("nazwa"));
        value.setCellValueFactory(new PropertyValueFactory("wartosc"));
    }
}
