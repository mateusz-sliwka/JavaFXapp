package bd2app;

import bd2app.dao.*;
import bd2app.model.*;
//import com.sun.deploy.security.SelectableSecurityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NowaRezerwacjaController {
    @FXML
    private ComboBox uslugaSelect;
    @FXML
    private ComboBox instruktorSelect;
    @FXML
    private Button addNewReservationBtn;
    @FXML
    private ComboBox hourSelect;
    @FXML
    private ComboBox statusSelect;
    @FXML
    private DatePicker calendar;
    @FXML
    private ComboBox kursantSelect;
    @FXML
    private Label saldoLabel;
    UzytkownicyEntity kursant;
    InstruktorzyEntity instruktor;
    UslugiEntity usluga;
    Timestamp data;
    RezerwacjeEntity re;
    boolean toUpdate = false;

    void toUpdate(RezerwacjeEntity re) {
        kursant = App.udao.findById(re.getKlient());
        saldoLabel.setText("Stan ePortfela: " + kursant.getEportfel().toString() + ".00zł");
        saldoLabel.setVisible(true);
        ObservableList<UslugiEntity> uslugaTypes = FXCollections.observableArrayList();
        for (UslugiEntity ue : App.usdao.findAll()
                ) {
            if (ue.getCena() <= kursant.getEportfel())
                uslugaTypes.add(ue);
        }
        uslugaSelect.setItems(uslugaTypes);
        toUpdate = true;
        uslugaSelect.getSelectionModel().select(App.usdao.findById(re.getUsługa()));
        instruktorSelect.getSelectionModel().select(App.idao.findById(re.getInstruktor()));
        kursantSelect.getSelectionModel().select(App.udao.findById(re.getKlient()));
        statusSelect.getSelectionModel().select(re.getObecnosc());
        uslugaSelect.setDisable(false);
        kursantSelect.setDisable(false);
        instruktorSelect.setDisable(false);
        calendar.setDisable(false);
        hourSelect.setDisable(true);
        addNewReservationBtn.setDisable(true);
        this.re = re;
    }

    @FXML
    private void newReservation() {
        if (!toUpdate) {
            re = new RezerwacjeEntity();
            re.setUsługa(usluga.getId());
        }
        LocalDate localDate = calendar.getValue();
        if(App.getUserType().equals("Kursant")) {
            List<RezerwacjeEntity> res = new ArrayList<RezerwacjeEntity>(App.udao.findById(App.getUserID()).getRezerwacjesById());
            res = res.stream().filter(item -> item.getData().toLocalDateTime().toLocalDate().equals(localDate)).collect(Collectors.toList());

            if(App.ustdao.findByNazwa("limit_rezerwacji_dziennych").getWartosc() <= res.size()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Przekroczyłeś limit rezerwacji na dany dzień");
                alert.showAndWait();
                return;
            }
        }
        String data;
        if ((int) hourSelect.getSelectionModel().getSelectedItem() < 10)
            data = localDate + " 0" + hourSelect.getSelectionModel().getSelectedItem().toString() + ":00:00";
        else
            data = localDate + " " + hourSelect.getSelectionModel().getSelectedItem().toString() + ":00:00";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        TransakcjeEntity transakcjeEntity = new TransakcjeEntity();
        transakcjeEntity.setData(new java.sql.Date(new Date().getTime()));
        transakcjeEntity.setUzytkownik(kursant.getId());
        int kwota = 0;
        if (uslugaSelect.getSelectionModel().getSelectedItem() != App.usdao.findById(re.getUsługa())) {

            kwota = (App.usdao.findById(re.getUsługa()).getCena() - ((UslugiEntity) uslugaSelect.getSelectionModel().getSelectedItem()).getCena()); //zwraca roznice po updacie ejzeli zmieniono usluge

            re.setUsługa(usluga.getId());
        }
        else
            kwota = -1*App.usdao.findById(re.getUsługa()).getCena();
        transakcjeEntity.setKwota(kwota);

        App.tdao.persist(transakcjeEntity);
        kursant.refreshPortfel();
        re.setData(ts);
        re.setInstruktor(instruktor.getId());
        re.setKlient(kursant.getId());
        if(App.getUserType().equals("Kursant")){
            re.setObecnosc("Oczekujący");
        }
        else {
            re.setObecnosc(statusSelect.getSelectionModel().getSelectedItem().toString());
        }

        App.udao.update(kursant);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nowa rezerwacja");
        alert.setHeaderText(null);

        if (!toUpdate) {
            App.rdao.persist(re);
            App.udao.refresh(kursant);
            alert.setContentText("Rezerwacja została dodana!");
        } else {
            alert.setContentText("Rezerwacja została zaktualizowana!");
            App.rdao.update(re);
        }
        alert.showAndWait();
        Stage stage = (Stage) hourSelect.getScene().getWindow();
        stage.fireEvent(
            new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
            )
        );
    }

    @FXML
    private void initialize() {
        ObservableList<UzytkownicyEntity> allKursant = FXCollections.observableArrayList();
        ObservableList<String> allStatus = FXCollections.observableArrayList();
        if (App.getUserType().equals("Kursant")) {
            allKursant.add(App.udao.findById(App.getUserID()));
            statusSelect.setVisible(false);
            kursantSelect.setDisable(true);
            kursantSelect.setItems(allKursant);
            kursantSelect.getSelectionModel().select(0);
            getServices();
        } else {
            allStatus.add("Nieobecny");
            allStatus.add("Obecny");
            allStatus.add("Oczekujące");
            statusSelect.setItems(allStatus);
            allKursant.addAll(App.udao.findAll().stream().filter(item -> item.getTypUzytkownika() == 0).collect(Collectors.toList()));
            kursantSelect.setItems(allKursant);
        }

        calendar.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }

    @FXML
    private void getInstructors() {
        addNewReservationBtn.setDisable(true);
        instruktorSelect.getSelectionModel().clearSelection();
        hourSelect.getSelectionModel().clearSelection();
        if (uslugaSelect.getSelectionModel().getSelectedItem() != null)
            if (uslugaSelect.getSelectionModel().getSelectedItem().toString() != "Wybierz usługę" && uslugaSelect.getSelectionModel().getSelectedItem().toString() != "ePortfel za mały") {
                usluga = (UslugiEntity) uslugaSelect.getSelectionModel().getSelectedItem();
                instruktorSelect.setDisable(false);
                hourSelect.setDisable(true);
                instruktorSelect.getSelectionModel().clearSelection();
                hourSelect.getSelectionModel().clearSelection();
                UslugiEntity ue = (UslugiEntity) uslugaSelect.getSelectionModel().getSelectedItem();
                ObservableList<InstruktorzyEntity> instruktorzyList = FXCollections.observableArrayList();
                instruktorzyList.addAll(App.kdao.instruktorzyByKategoria(ue.getKategoriaByKategoriaid(), App.idao));
                instruktorSelect.setItems(instruktorzyList);
            }
    }

    @FXML
    private void updateHours() {
        addNewReservationBtn.setDisable(true);
        hourSelect.setDisable(true);
        hourSelect.getSelectionModel().clearSelection();
        calendar.setDisable(false);
        calendar.getEditor().clear();
    }

    @FXML
    private void calendarChanged() {
        hourSelect.setDisable(false);
        addNewReservationBtn.setDisable(true);
        ObservableList<Integer> hoursList = FXCollections.observableArrayList();
        kursant = (UzytkownicyEntity) kursantSelect.getSelectionModel().getSelectedItem();
        instruktor = (InstruktorzyEntity) instruktorSelect.getSelectionModel().getSelectedItem();
        for (int i = instruktor.getGodzStartuPracy(); i < instruktor.getGodzKoncaPracy(); i++) {
            LocalDate localDate = calendar.getValue();
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
            if (kursant.czyDostepny(ts) && instruktor.czyDostepny(ts))
                hoursList.add(i);
        }
        hourSelect.setItems(hoursList);
    }

    @FXML
    private void getServices() {
        addNewReservationBtn.setDisable(true);
        instruktorSelect.setDisable(true);
        uslugaSelect.getSelectionModel().clearSelection();
        instruktorSelect.getSelectionModel().clearSelection();
        hourSelect.getSelectionModel().clearSelection();
        kursant = (UzytkownicyEntity) kursantSelect.getSelectionModel().getSelectedItem();
        kursant.refreshPortfel();
        App.udao.update(kursant);
        System.out.println(kursant);
        System.out.println(kursant.getEportfel());
        saldoLabel.setText("Stan ePortfela: " + kursant.getEportfel().toString() + ".00zł");
        saldoLabel.setVisible(true);
        ObservableList<UslugiEntity> uslugaTypes = FXCollections.observableArrayList();
        for (UslugiEntity ue : App.usdao.findAll()
                ) {
            if (ue.getCena() <= kursant.getEportfel())
                uslugaTypes.add(ue);
        }
        if (uslugaTypes.size() > 0) {
            uslugaSelect.setPromptText("Wybierz usługę");
            uslugaSelect.setDisable(false);
            uslugaSelect.setItems(uslugaTypes);
        } else {
            uslugaSelect.setDisable(true);
            uslugaSelect.setPromptText("ePortfel za mały");
        }
    }

    @FXML
    private void hourSelected() {
        addNewReservationBtn.setDisable(false);
        statusSelect.setDisable(false);
    }
}
