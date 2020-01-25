package bd2app;

import bd2app.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class NowaUslugaController {
    @FXML
    private ComboBox kategoriaSelect;
    @FXML
    private TextField cenaField;
    @FXML
    private TextField nazwaField;
    @FXML
    private Button addNewService;
    private UslugiEntity ue;
    boolean toUpdate = false;

    void toUpdate(UslugiEntity ue) {
        toUpdate = true;
        kategoriaSelect.getSelectionModel().select(App.kdao.findById(ue.getKategoriaid()));
        cenaField.setText(String.valueOf(ue.getCena()));
        nazwaField.setText(ue.getNazwa());
        this.ue = ue;
    }

    @FXML
    private void initialize() {
        ObservableList<KategoriaEntity> allKategoire = FXCollections.observableArrayList();
        allKategoire.addAll(App.kdao.findAll());
        kategoriaSelect.setItems(allKategoire);
    }

    @FXML
    private void newService() {
        if (!toUpdate)
            ue = new UslugiEntity();
        ue.setNazwa(nazwaField.getText());
        ue.setCena(Integer.parseInt(cenaField.getText()));
        KategoriaEntity ke = (KategoriaEntity) kategoriaSelect.getSelectionModel().getSelectedItem();
        ue.setKategoriaid(ke.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nowa usluga");
        alert.setHeaderText(null);
        Stage stage = (Stage) addNewService.getScene().getWindow();
        stage.fireEvent(
            new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
            )
        );
        if (!toUpdate) {
            App.usdao.persist(ue);
            alert.setContentText("Usluga została dodana!");
        } else {
            App.usdao.update(ue);
            alert.setContentText("Usluga została zaktualizowana!");
        }
        alert.showAndWait();
    }
}