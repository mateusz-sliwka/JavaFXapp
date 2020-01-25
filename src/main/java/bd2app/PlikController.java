package bd2app;

import bd2app.model.RezerwacjeEntity;
import bd2app.model.TransakcjeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PlikController {

    ObservableList<TransakcjeEntity> transactions;
    ObservableList<RezerwacjeEntity> reservations;
    String type;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirm(ActionEvent event) throws IOException {
        LocalDate from = dateFrom.getValue();
        LocalDate to = dateTo.getValue();
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file == null) {
            return;
        }
        Writer csvWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");;

        if(type.equals("t")) {
            csvWriter.append("Data");
            csvWriter.append(";");
            csvWriter.append("Kwota");
            csvWriter.append(";");
            csvWriter.append("Użytkownik");
            csvWriter.append("\n");
            transactions = FXCollections.observableArrayList( transactions.stream().filter(item -> (item.getData().toLocalDate().isAfter(from) || item.getData().toLocalDate().isEqual(from)) && (item.getData().toLocalDate().isBefore(to) || item.getData().toLocalDate().isEqual(to))).collect(Collectors.toList()));
            for (TransakcjeEntity rowData : transactions) {
                csvWriter.append(rowData.getData().toString());
                csvWriter.append(";");
                csvWriter.append(rowData.getKwota().toString());
                csvWriter.append(";");
                csvWriter.append(rowData.getUzytkownicyByUzytkownik().toString());
                csvWriter.append("\n");
            }
        }
        if(type.equals("r")) {
            csvWriter.append("Data");
            csvWriter.append(";");
            csvWriter.append("Usługa");
            csvWriter.append(";");
            csvWriter.append("Instruktor");
            csvWriter.append(";");
            csvWriter.append("Użytkownik");
            csvWriter.append("\n");
            reservations = FXCollections.observableArrayList( reservations.stream().filter(item -> (new Date(item.getData().getTime()).toLocalDate().isAfter(from) || new Date(item.getData().getTime()).toLocalDate().isEqual(from)) && (new Date(item.getData().getTime()).toLocalDate().isBefore(to) || new Date(item.getData().getTime()).toLocalDate().isEqual(to))).collect(Collectors.toList()));
            for (RezerwacjeEntity rowData : reservations) {
                csvWriter.append(rowData.getData().toString());
                csvWriter.append(";");
                csvWriter.append(rowData.getUslugiByUsługa().toString());
                csvWriter.append(";");
                csvWriter.append(rowData.getInstruktorzyByInstruktor().toString());
                csvWriter.append(";");
                csvWriter.append(rowData.getUzytkownicyByKlient().toString());
                csvWriter.append("\n");
            }
        }
        csvWriter.flush();
        csvWriter.close();
       stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wygenerowano raport");
        alert.setHeaderText(null);
        alert.setContentText("Raport został zapisany!");
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now());
    }

    public void setTransactions(ObservableList<TransakcjeEntity> t) {
        this.type = "t";
        this.transactions = t;
    }

    public void setReservations(ObservableList<RezerwacjeEntity> r) {
        this.type = "r";
        this.reservations = r;
    }

}
