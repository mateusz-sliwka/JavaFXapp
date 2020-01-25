package bd2app;

import bd2app.model.InstruktorzyEntity;
import bd2app.model.UzytkownicyEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DostepnoscController {

    InstruktorzyEntity instructor;

    @FXML
    private DatePicker pickDate;

    @FXML
    private Button confirm;

    @FXML
    private Button cancel;

    @FXML
    void checkHours(ActionEvent event) {
        List<Integer> hoursList = new ArrayList<>();
        for (int i = instructor.getGodzStartuPracy(); i < instructor.getGodzKoncaPracy(); i++) {
            LocalDate localDate = pickDate.getValue();
            String data;
            if (i < 10)
                data = localDate + " 0" + String.valueOf(i) + ":00:00";
            else
                data = localDate + " " + String.valueOf(i) + ":00:00";
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            if (instructor.czyDostepny(ts))
                hoursList.add(i);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wolne godziny");
        alert.setHeaderText(null);
        String kategorie = "Dostępne godziny:";

        for (Integer hour : hoursList) {
            kategorie += " " + hour.toString();
        }
        if(hoursList.size() == 0)
            kategorie = "Brak wolnych godzin w danym dniu";
        alert.setContentText(kategorie);
        alert.showAndWait();
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        pickDate.setValue(LocalDate.now());

    }

    void setInstructor(InstruktorzyEntity i) {
        this.instructor = i;
    }

}
