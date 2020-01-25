package bd2app;

import bd2app.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bd2app.App.loadFXML;

public class RezerwacjeController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button myAccountButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button newReservationButton;
    @FXML
    private Button deleteReservationButton;
    @FXML
    private Button editReservationButton;
    @FXML
    private Button filterClient;
    @FXML
    private Button clearFiltersButton;
    @FXML
    private Button saveToFileButton;
    @FXML
    private TableView<RezerwacjeEntity> reservationsList;
    @FXML
    private TableColumn<RezerwacjeEntity, Timestamp> date;
    @FXML
    private TableColumn<RezerwacjeEntity, String> service;
    @FXML
    private TableColumn<RezerwacjeEntity, String> instructor;
    @FXML
    private TableColumn<RezerwacjeEntity, String> user;
    @FXML
    private TableColumn<RezerwacjeEntity, String> status;

    @FXML
    void goBack(ActionEvent event) throws IOException {
        App.goBack();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        App.logout();
    }

    @FXML
    void newReservation(ActionEvent event) throws IOException {
        if(App.getUserType().equals("Kursant")) {
            List<RezerwacjeEntity> res = new ArrayList<RezerwacjeEntity>(App.udao.findById(App.getUserID()).getRezerwacjesById());
            res = res.stream().filter(item -> item.getObecnosc().equals("Oczekujący")).collect(Collectors.toList());
            if(res.size() >= App.ustdao.findByNazwa("limit_rezerwacji_oczekujacych").getWartosc()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Przekroczyłeś limit rezerwacji oczekeujących");
                alert.showAndWait();
                return;
            }
        }

        Stage stage = new Stage();
        stage.setTitle("Nowa rezerwacja");
        stage.setScene(new Scene(loadFXML("nowaRezerwacja")));
        stage.show();
        stage.setOnCloseRequest((ev)->{
            loadReservations();
        });
    }

    @FXML
    void showMyAccount(ActionEvent event) throws IOException {
        App.setRoot("konto");
    }

    @FXML
    void initialize() {
        loadReservations();
        if(!App.getUserType().equals("Administrator")) {
            deleteReservationButton.setVisible(false);
            editReservationButton.setVisible(false);
            filterClient.setVisible(false);
            clearFiltersButton.setVisible(false);
            saveToFileButton.setVisible(false);
            newReservationButton.setVisible(false);
        }
        else {
            deleteReservationButton.setVisible(true);
            editReservationButton.setVisible(true);
            filterClient.setVisible(true);
            clearFiltersButton.setVisible(true);
            newReservationButton.setVisible(true);
            saveToFileButton.setVisible(true);
        }
        if(!App.getUserType().equals("Kursant")){
            filterClient.setVisible(true);
            clearFiltersButton.setVisible(true);
        }
        if(App.getUserType().equals("Kursant")){
            deleteReservationButton.setVisible(true);
        }
        if(App.getUserType().equals("Ksiegowy")){
            saveToFileButton.setVisible(true);
        }
    }

    @FXML
    void deleteReservation() {
        if(!App.getUserType().equals("Kursant")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie rezerwacji");
            alert.setHeaderText("Wybierz sposób usuwania");
            alert.setContentText("Czy po usunięciu chcesz oddać kursantowi należność za rezerwacje?");
            ButtonType buttonTypeOne = new ButtonType("Usuń ze zwrotem");
            ButtonType buttonTypeTwo = new ButtonType("Usuń bez zwrotu");
            ButtonType buttonTypeCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                RezerwacjeEntity re = reservationsList.getSelectionModel().getSelectedItem();
                TransakcjeEntity te = new TransakcjeEntity();
                te.setUzytkownik(re.getKlient());
                UslugiEntity ue = App.usdao.findById(re.getUsługa());
                te.setKwota(ue.getCena());
                te.setData(new Date(System.currentTimeMillis()));
                App.tdao.persist(te);
                App.rdao.delete(re);
            } else if (result.get() == buttonTypeTwo) {
                RezerwacjeEntity re = reservationsList.getSelectionModel().getSelectedItem();
                App.rdao.delete(re);
            } else {
                alert.close();
            }
        } else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie rezerwacji");
            alert.setHeaderText("Potwierdź usunięcie rezerwacji");
            RezerwacjeEntity re = reservationsList.getSelectionModel().getSelectedItem();
            java.util.Date dateBefore = DateUtils.addDays(re.getData(),-1);
            boolean returnMoney = true;
            if(!dateBefore.after(new Date(System.currentTimeMillis()))){
                returnMoney = false;
                alert.setContentText("Uwaga! Nie otrzymasz zwrotu pieniędzy za anulowanie tej rezerwacji");
            } else {
                alert.setContentText("Otrzymasz pełny zwrot pieniędzy po anulowaniu rezerwacji");
            }

            ButtonType buttonTypeOne = new ButtonType("Potwierdź");
            ButtonType buttonTypeCancel = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonTypeCancel){
                alert.close();
            }
            else if(result.get() == buttonTypeOne){
                if (returnMoney) {
                    TransakcjeEntity te = new TransakcjeEntity();
                    te.setUzytkownik(re.getKlient());
                    UslugiEntity ue = App.usdao.findById(re.getUsługa());
                    te.setKwota(ue.getCena());
                    te.setData(new Date(System.currentTimeMillis()));
                    App.tdao.persist(te);
                    App.rdao.delete(re);
                } else if (!returnMoney) {
                    App.rdao.delete(re);
                }
            }

        }
        loadReservations();
    }

    @FXML
    void editReservation() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Edytuj rezerwacje");
        FXMLLoader loader = App.getLoader("nowaRezerwacja");
        stage.setScene(new Scene(loader.load()));
        loader.<NowaRezerwacjaController>getController().toUpdate(reservationsList.getSelectionModel().getSelectedItem());
        stage.show();
        stage.setOnCloseRequest((event)->{
            loadReservations();
        });
    }

    void loadReservations() {
        reservationsList.getItems().clear();
        List<RezerwacjeEntity> reservations;
        if(App.getUserType().equals("Kursant")){
            reservations = new ArrayList<RezerwacjeEntity>(App.rdao.findAll().stream().filter(item->item.getKlient()==App.getUserID()).collect(Collectors.toList()));
        }
        else {
            reservations = App.rdao.findAll();
        }
        ObservableList<RezerwacjeEntity> data = FXCollections.observableArrayList(reservations);
        reservationsList.setItems(data);
        date.setCellValueFactory(new PropertyValueFactory("data"));
        service.setCellValueFactory(new PropertyValueFactory("uslugiByUsługa"));
        instructor.setCellValueFactory(new PropertyValueFactory("instruktorzyByInstruktor"));
        user.setCellValueFactory(new PropertyValueFactory("uzytkownicyByKlient"));
        status.setCellValueFactory(new PropertyValueFactory("obecnosc"));
    }

    @FXML
    void filterByClient(ActionEvent event){
        List<UzytkownicyEntity> users = new ArrayList<>(App.udao.findAll());
        users = users.stream().filter(user -> user.getTypUzytkownika() == 0).collect(Collectors.toList());


        ChoiceDialog<UzytkownicyEntity> dialog = new ChoiceDialog<>(users.iterator().next(),users);
        dialog.setTitle("Filtruj");
        dialog.setHeaderText("Wybierz kursanta");
        dialog.setContentText("Kursant:");

        Optional<UzytkownicyEntity> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            reservationsList.getItems().clear();
            List<RezerwacjeEntity> reservations;

            reservations = App.rdao.findAll().stream().filter(item -> item.getUzytkownicyByKlient().equals(letter)).collect(Collectors.toList());
            ObservableList<RezerwacjeEntity> data = FXCollections.observableArrayList(reservations);
            reservationsList.setItems(data);
            date.setCellValueFactory(new PropertyValueFactory("data"));
            service.setCellValueFactory(new PropertyValueFactory("uslugiByUsługa"));
            instructor.setCellValueFactory(new PropertyValueFactory("instruktorzyByInstruktor"));
            user.setCellValueFactory(new PropertyValueFactory("uzytkownicyByKlient"));
        });
    }

    @FXML
    void clearFilters(ActionEvent event) {
        loadReservations();
    }

    @FXML
    void saveToFile() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Wybierz przedział czasowy");
        FXMLLoader loader = App.getLoader("plik");
        stage.setScene(new Scene(loader.load()));
        stage.show();
        PlikController controller = (PlikController) loader.getController();
        controller.setReservations(reservationsList.getItems());
    }
}