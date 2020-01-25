package bd2app;

import bd2app.model.InstruktorzyEntity;
import bd2app.model.InstruktorzyKategoriaEntity;
import bd2app.model.KategoriaEntity;
import bd2app.model.UslugiEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class NowePrzypisanieKategoriiControler {
    @FXML
    private ComboBox kategoriaSelect;
    @FXML
    private Button addNewService;
    private InstruktorzyEntity instruktor;

    @FXML
    private void initialize() {
    }

    void initInstruktor(InstruktorzyEntity instruktor) {
        this.instruktor = instruktor;
        ObservableList<KategoriaEntity> allKategoire = FXCollections.observableArrayList();
        System.out.println(instruktor.getEmail() + instruktor.getId());
        //System.out.println(instruktor.getInstruktorzyKategoriasById().size());
        for (KategoriaEntity ke : App.kdao.findAll()
                ) {
            boolean czydodac = true;
            for (InstruktorzyKategoriaEntity ike : instruktor.getInstruktorzyKategoriasById()
                    ) {
                if (ike.getKategoriaid() == ke.getId())
                    czydodac = false;
            }
            if (czydodac)
                allKategoire.add(ke);
        }
        kategoriaSelect.setItems(allKategoire);
    }

    @FXML
    private void newService() {
        InstruktorzyKategoriaEntity ie = new InstruktorzyKategoriaEntity();
        System.out.println(instruktor.getEmail() + instruktor.getId());
        ie.setInstruktorzyid(instruktor.getId());
        KategoriaEntity ke = (KategoriaEntity) kategoriaSelect.getSelectionModel().getSelectedItem();
        ie.setKategoriaid(ke.getId());
        System.out.println("chcesz dodac ins: " + ie.getInstruktorzyid() + " kat: " + ie.getKategoriaid());
        App.ikdao.persist(ie);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nowe przypisanie");
        alert.setHeaderText(null);
        alert.setContentText("Przypisanie zostalo dodane!");
        alert.showAndWait();
        Stage stage = (Stage) addNewService.getScene().getWindow();
        stage.fireEvent(
            new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
            )
        );
    }
}