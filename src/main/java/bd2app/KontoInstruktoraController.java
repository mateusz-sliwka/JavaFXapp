package bd2app;

import bd2app.dao.InstruktorzyDao;
import bd2app.model.InstruktorzyEntity;
import bd2app.model.UzytkownicyEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class KontoInstruktoraController {
    @FXML
    TextField name;
    @FXML
    TextField surname;
    @FXML
    TextField mail;
    @FXML
    PasswordField password;
    @FXML
    TextField start;
    @FXML
    TextField end;
    @FXML
    TextField pesel;
    @FXML
    TextField phone;
    @FXML
    Button updateBtn;
    @FXML
    Button addBtn;
    @FXML
            Label errorLabel;
    InstruktorzyEntity ie;
    boolean toUpdate = false;

    public void toUpdate(InstruktorzyEntity ue) {
        addBtn.setVisible(false);
        toUpdate = true;
        ie = ue;
        updateBtn.setVisible(true);
        name.setText(ue.getImię());
        surname.setText(ue.getNazwisko());
        mail.setText(ue.getEmail());
        password.setText(ue.getHasło());
        end.setText(String.valueOf(ue.getGodzKoncaPracy()));
        start.setText(String.valueOf(ue.getGodzStartuPracy()));
        pesel.setText(ue.getPesel());
        phone.setText(String.valueOf(ue.getTelefon()));
    }

    @FXML
    public void update() {
        ie.setEmail(mail.getText());
        ie.setHasło(password.getText());
        ie.setImię(name.getText());
        ie.setNazwisko(surname.getText());
        ie.setPesel(pesel.getText());
        ie.setTelefon(Integer.parseInt(phone.getText()));
        ie.setGodzStartuPracy(Integer.parseInt(start.getText()));
        ie.setGodzKoncaPracy(Integer.parseInt(end.getText()));
        App.idao.update(ie);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aktualziacja instruktora");
        alert.setHeaderText(null);
        alert.setContentText("Instruktor zaktualizowany!");
        alert.showAndWait();
        Stage stage = (Stage) mail.getScene().getWindow();
        stage.fireEvent(
            new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
            )
        );
    }

    @FXML
    private void add() throws IOException {
        String firstName = name.getText();
        String lastName = surname.getText();
        String phone2 = phone.getText();

        String pesel2 = pesel.getText();
        String password2 = password.getText();
        String email = mail.getText();
        String startt = start.getText();
        String endd = end.getText();
        String[] inputs = {firstName, lastName, phone2, pesel2, endd,startt, password2, email};
        boolean sthEmpty = false;
        for (String s : inputs) {
            if (s.isEmpty())
                sthEmpty = true;
        }
        if (!sthEmpty && !App.udao.validateDuplicate(email, pesel2)) {
            ie = new InstruktorzyEntity();
            ie.setEmail(email);
            ie.setHasło(password2);
            ie.setImię(lastName);
            ie.setNazwisko(firstName);
            ie.setPesel(pesel2);
            ie.setTelefon(Integer.parseInt(phone2));
            ie.setGodzStartuPracy(Integer.parseInt(startt));
            ie.setGodzKoncaPracy(Integer.parseInt(endd));
            App.idao.persist(ie);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Formularz rejestracji");
            alert.setHeaderText(null);
            alert.setContentText("Instruktor dodany!");
            alert.showAndWait();
            Stage stage = (Stage) mail.getScene().getWindow();
            stage.fireEvent(
                new WindowEvent(
                    stage,
                    WindowEvent.WINDOW_CLOSE_REQUEST
                )
            );
        } else {
            if (sthEmpty)
                errorLabel.setText("Wypełnij wszystkie pola!");
            else {
                errorLabel.setText("Konto o takim e-mail lub pesel istnieje!");
                mail.setText("");
                pesel.setText("");
            }
            errorLabel.setVisible(true);
        }
    }
}
