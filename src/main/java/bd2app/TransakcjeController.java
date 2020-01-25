package bd2app;

import bd2app.model.RezerwacjeEntity;
import bd2app.model.TransakcjeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static bd2app.App.loadFXML;

public class TransakcjeController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button myAccountButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button newTransactionButton;
    @FXML
    private Button deleteTransactionBtn;
    @FXML
    private Button editTransactionButton;
    @FXML
    private Button fileButton;
    @FXML
    private TableView<TransakcjeEntity> transactionsList;
    @FXML
    private TableColumn<TransakcjeEntity, Date> date;
    @FXML
    private TableColumn<TransakcjeEntity, Integer> price;
    @FXML
    private TableColumn<TransakcjeEntity, String> user;

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
        stage.setTitle("Nowa transakcja");
        stage.setScene(new Scene(loadFXML("nowaTransakcja")));
        stage.show();
        stage.setOnCloseRequest(ev -> {
            loadTransactions();
        });
    }

    @FXML
    void showMyAccount(ActionEvent event) throws IOException {
        App.setRoot("konto");
    }

    @FXML
    void initialize() {
        if (App.getUserType().equals("Kursant") || App.getUserType().equals("Biurowy") || App.getUserType().equals("Instruktor")) {
            editTransactionButton.setVisible(false);
            newTransactionButton.setVisible(false);
            deleteTransactionBtn.setVisible(false);
            fileButton.setVisible(false);
        } else {
            editTransactionButton.setVisible(true);
            newTransactionButton.setVisible(true);
            deleteTransactionBtn.setVisible(true);
            fileButton.setVisible(true);
        }
        loadTransactions();
    }

    @FXML
    void deleteTransaction() {
        App.tdao.delete(transactionsList.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuwanie transakcji");
        alert.setHeaderText(null);
        alert.setContentText("Transakcja zostala usunieta!");
        alert.showAndWait();
        loadTransactions();
    }

    @FXML
    void editTransaction() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Edytuj transakcje");
        FXMLLoader loader = App.getLoader("nowaTransakcja");
        stage.setScene(new Scene(loader.load()));
        loader.<NowaTransakcjaController>getController().toUpdate(transactionsList.getSelectionModel().getSelectedItem());
        stage.show();
        stage.setOnCloseRequest(ev -> {
            loadTransactions();
        });
    }

    void loadTransactions() {
        transactionsList.getItems().clear();
        List<TransakcjeEntity> transactions;
        if (App.getUserType().equals("Kursant")) {
            transactions = new ArrayList<>(App.udao.findById(App.getUserID()).getTransakcjesById());
        } else {
            transactions = App.tdao.findAll();
        }
        ObservableList<TransakcjeEntity> data = FXCollections.observableArrayList(transactions);
        transactionsList.setItems(data);
        date.setCellValueFactory(new PropertyValueFactory("data"));
        price.setCellValueFactory(new PropertyValueFactory("kwota"));
        user.setCellValueFactory(new PropertyValueFactory("uzytkownicyByUzytkownik"));

    }

    @FXML
    void saveToFile() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Wybierz przedział czasowy");
        FXMLLoader loader = App.getLoader("plik");
        stage.setScene(new Scene(loader.load()));
        stage.show();
        PlikController controller = (PlikController) loader.getController();
        controller.setTransactions(transactionsList.getItems());
    }
}
