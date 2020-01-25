package bd2app;

import bd2app.model.TransakcjeEntity;
import bd2app.model.UstawieniaEntity;
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

public class NoweUstawieniaController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField valueField;
    @FXML
    private Button addNewSetting;
    UstawieniaEntity ue;
    boolean toUpdate = false;

    void toUpdate(UstawieniaEntity ue) {
        toUpdate = true;
        this.ue = ue;
        nameField.setText(ue.getNazwa());
        valueField.setText(String.valueOf(ue.getWartosc()));
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void newSetting() {
        if (!toUpdate)
            ue = new UstawieniaEntity();
        ue.setNazwa(nameField.getText());
        ue.setWartosc(Integer.parseInt(valueField.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nowe ustawienie");
        alert.setHeaderText(null);
        if (!toUpdate) {
            App.ustdao.persist(ue);
            alert.setContentText("Ustawienie zostalo dodane!");
        } else {
            App.ustdao.update(ue);
            alert.setContentText("Ustawienie zostalo zaktualizowane!");
        }
        alert.showAndWait();
        Stage stage = (Stage) addNewSetting.getScene().getWindow();
        stage.fireEvent(
            new WindowEvent(
                    stage,
                    WindowEvent.WINDOW_CLOSE_REQUEST
            )
        );
    }
}