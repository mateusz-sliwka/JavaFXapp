package bd2app;

import bd2app.model.KategoriaEntity;
import bd2app.model.TransakcjeEntity;
import bd2app.model.UslugiEntity;
import bd2app.model.UzytkownicyEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.Date;
import java.util.Calendar;

public class NowaTransakcjaController {
    @FXML
    private ComboBox kursantSelect;
    @FXML
    private TextField kwotaField;
    @FXML
    private Button addNewService;
    TransakcjeEntity te;
    boolean toUpdate = false;

    void toUpdate(TransakcjeEntity te) {
        toUpdate = true;
        kursantSelect.getSelectionModel().select(App.udao.findById(te.getUzytkownik()));
        kwotaField.setText(te.getKwota().toString());
        this.te = te;
    }

    @FXML
    private void initialize() {
        ObservableList<UzytkownicyEntity> allKursanci = FXCollections.observableArrayList();
        for (UzytkownicyEntity ue : App.udao.findAll()
                ) {
            if (ue.getTypUzytkownika() == 0)
                allKursanci.add(ue);
        }
        kursantSelect.setItems(allKursanci);
    }

    @FXML
    private void newService() {
        if (!toUpdate)
            te = new TransakcjeEntity();
        te.setData(new Date(System.currentTimeMillis()));
        te.setKwota(Integer.parseInt(kwotaField.getText()));
        UzytkownicyEntity ue = (UzytkownicyEntity) kursantSelect.getSelectionModel().getSelectedItem();
        te.setUzytkownik(ue.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nowa transakcja");
        alert.setHeaderText(null);
        Stage stage = (Stage) addNewService.getScene().getWindow();
        stage.fireEvent(
            new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
            )
        );
        if (!toUpdate) {
            App.tdao.persist(te);
            ue.refreshPortfel();
            alert.setContentText("Transakcja zostala dodana!");
        } else {
            App.tdao.update(te);
            ue.refreshPortfel();
            alert.setContentText("Transakcja zostala zaktualizowana!");
        }
        alert.showAndWait();
    }

}