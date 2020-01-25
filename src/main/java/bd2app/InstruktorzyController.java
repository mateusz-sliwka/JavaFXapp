package bd2app;

import bd2app.dao.KategoriaEntityDao;
import bd2app.model.InstruktorzyEntity;
import bd2app.model.InstruktorzyKategoriaEntity;
import bd2app.model.KategoriaEntity;
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

public class InstruktorzyController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button myAccountButton;
    @FXML
    private Button returnButton;
    @FXML
    private TableView<InstruktorzyEntity> instructorsList;
    @FXML
    private TableColumn<InstruktorzyEntity, String> name;
    @FXML
    private TableColumn<InstruktorzyEntity, String> surname;
    @FXML
    private TableColumn<InstruktorzyEntity, Integer> phone;
    @FXML
    private TableColumn<InstruktorzyEntity, String> mail;
    @FXML
    private TableColumn<InstruktorzyEntity, Integer> startHour;
    @FXML
    private TableColumn<InstruktorzyEntity, Integer> endHour;
    @FXML
    private Button showCategoriesBtn;
    @FXML
    private Button addCategoryBtn;
    @FXML
    private Button deleteInstructorBtn;
    @FXML
    private Button editInstructorBtn;
    @FXML
    private Button addInstructorBtn;


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
    void showCategories() {
        InstruktorzyEntity ie = instructorsList.getSelectionModel().getSelectedItem();
        if(ie == null){
            return;
        };
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kategorie instruktora");
        alert.setHeaderText(null);
        String kategorie = "Przypisane kategorie to:";
        List<InstruktorzyKategoriaEntity> ke = App.ikdao.findByInstruktorId(ie.getId());
        for (InstruktorzyKategoriaEntity iek : ke
                ) {
            kategorie += " " + App.kdao.findById(iek.getKategoriaid()).getOznaczenie();
        }
        alert.setContentText(kategorie);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        loadInstructors();
        if(!App.getUserType().equals("Administrator") && !App.getUserType().equals("Biurowy")) {
            showCategoriesBtn.setVisible(false);
            addCategoryBtn.setVisible(false);
            editInstructorBtn.setVisible(false);
            addInstructorBtn.setVisible(false);
            deleteInstructorBtn.setVisible(false);
        }
        else {
            showCategoriesBtn.setVisible(true);
            addCategoryBtn.setVisible(true);
            editInstructorBtn.setVisible(true);
            addInstructorBtn.setVisible(true);
            deleteInstructorBtn.setVisible(true);
        }
    }

    @FXML
    void addCategory() throws IOException {
        if(instructorsList.getSelectionModel().getSelectedItem() == null)
            return;
        Stage stage = new Stage();
        stage.setTitle("Nowe przypisanie");
        FXMLLoader loader = App.getLoader("nowePrzypisanieKategorii");
        stage.setScene(new Scene(loader.load()));
        loader.<NowePrzypisanieKategoriiControler>getController().initInstruktor(instructorsList.getSelectionModel().getSelectedItem());
        stage.show();
        stage.setOnCloseRequest(ev->{
            loadInstructors();
        });
    }

    void loadInstructors(){
        instructorsList.getItems().clear();
        List<InstruktorzyEntity> users = App.idao.findAll();
        ObservableList<InstruktorzyEntity> data = FXCollections.observableArrayList(users);
        instructorsList.setItems(data);
        name.setCellValueFactory(new PropertyValueFactory("imię"));
        surname.setCellValueFactory(new PropertyValueFactory("nazwisko"));
        phone.setCellValueFactory(new PropertyValueFactory("telefon"));
        mail.setCellValueFactory(new PropertyValueFactory("email"));
        startHour.setCellValueFactory(new PropertyValueFactory("godzStartuPracy"));
        endHour.setCellValueFactory(new PropertyValueFactory("godzKoncaPracy"));
    }
    @FXML
    public void deleteInstructor(){
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Usuwanie instruktora");
        alert2.setHeaderText("Próbujesz usunąc instruktora");
        alert2.setContentText("Razem z instruktorem usuna sie wszystkie jego rezerwacjei!");
        Optional<ButtonType> result = alert2.showAndWait();
        if (result.get() == ButtonType.OK) {
           InstruktorzyEntity ue = instructorsList.getSelectionModel().getSelectedItem();
            App.idao.delete(ue);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuwanie instruktora");
            alert.setHeaderText(null);
            alert.setContentText("Instruktor zostal usuniety!");
            alert.showAndWait();
            loadInstructors();
        } else {
            alert2.close();
        }
    }
    @FXML
    public void addInstructor() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Dodaj instruktora");
        FXMLLoader loader = App.getLoader("kontoInstruktora");
        stage.setScene(new Scene(loader.load()));
        stage.show();
        stage.setOnCloseRequest((event) -> {
            loadInstructors();
        });
    }
    @FXML
    public void editInstructor() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Edytuj instruktora");
        FXMLLoader loader = App.getLoader("kontoInstruktora");
        stage.setScene(new Scene(loader.load()));
        loader.<KontoInstruktoraController>getController().toUpdate(instructorsList.getSelectionModel().getSelectedItem());
        stage.show();
        stage.setOnCloseRequest((event) -> {
            loadInstructors();
        });
    }
    @FXML
    void checkAvailabilty() throws IOException {
        InstruktorzyEntity ie = instructorsList.getSelectionModel().getSelectedItem();
        if(ie == null){
            return;
        };
        Stage stage = new Stage();
        stage.setTitle("Wybierz datę");
        FXMLLoader loader = App.getLoader("dostepnosc");
        stage.setScene(new Scene(loader.load()));
        stage.show();
        DostepnoscController controller = (DostepnoscController) loader.getController();
        controller.setInstructor(ie);
    }
}
