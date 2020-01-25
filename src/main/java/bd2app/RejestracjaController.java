package bd2app;

import bd2app.dao.InstruktorzyDao;
import bd2app.dao.UzytkownicyDao;
import bd2app.model.UzytkownicyEntity;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.w3c.dom.Text;

import java.io.IOException;

public class RejestracjaController {
    @FXML
    TextField firstnameTF;
    @FXML
    TextField lastnameTF;
    @FXML
    TextField emailTF;
    @FXML
    PasswordField passwordTF;
    @FXML
    TextField pkkTF;
    @FXML
    TextField peselTF;
    @FXML
    TextField phoneTF;
    @FXML
    Label errorLabel;
    @FXML
    Button backBtn;
    @FXML
    Button registerButton1;
    @FXML
            Button updateBtn;
    UzytkownicyEntity clientToUpdate;

    boolean ifAdmin = false;
    boolean toUpdate = false;

    public void fromAdmin() {
        backBtn.setVisible(false);
        ifAdmin = true;
    }

    public void toUpdate(UzytkownicyEntity ue){
        registerButton1.setVisible(false);
        backBtn.setVisible(false);
        toUpdate=true;
        clientToUpdate=ue;
        updateBtn.setVisible(true);
        firstnameTF.setText(ue.getImię());
        lastnameTF.setText(ue.getNazwisko());
        emailTF.setText(ue.getEmail());
        passwordTF.setText(ue.getHasło());
        pkkTF.setText(ue.getPkk());
        peselTF.setText(ue.getPesel());
        phoneTF.setText(String.valueOf(ue.getTelefon()));

    }

    @FXML
    public void update(){
        clientToUpdate.setEmail(emailTF.getText());
        clientToUpdate.setImię(firstnameTF.getText());
        clientToUpdate.setNazwisko(lastnameTF.getText());
        clientToUpdate.setHasło(passwordTF.getText());
        clientToUpdate.setPkk(pkkTF.getText());
        clientToUpdate.setPesel(peselTF.getText());
        clientToUpdate.setTelefon(Integer.parseInt(phoneTF.getText()));
        App.udao.update(clientToUpdate);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Formularz rejestracji");
        alert.setHeaderText(null);
        alert.setContentText("Uzytkownik zaktualizowany!");
        alert.showAndWait();
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.fireEvent(
            new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
            )
        );

    }

    @FXML
    private void register() throws IOException {
        String firstName = firstnameTF.getText().toString();
        String lastName = lastnameTF.getText().toString();
        String phone = phoneTF.getText().toString();
        String pesel = peselTF.getText().toString();
        String pkk = pkkTF.getText().toString();
        String password = passwordTF.getText().toString();
        String email = emailTF.getText().toString();
        String[] inputs = {firstName, lastName, phone, pesel, pkk, password, email};
        boolean sthEmpty = false;
        for (String s : inputs) {
            if (s.isEmpty())
                sthEmpty = true;
        }
        if (!sthEmpty && !App.udao.validateDuplicate(email, pesel)) {
            UzytkownicyEntity user = new UzytkownicyEntity();
            user.setImię(firstName);
            user.setNazwisko(lastName);
            user.setTelefon(Integer.parseInt(phone));
            user.setPesel(pesel);
            user.setPkk(pkk);
            user.setHasło(password);
            user.setEmail(email);
            user.setTypUzytkownika(0);
            App.udao.persist(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Formularz rejestracji");
            alert.setHeaderText(null);
            if (!ifAdmin)
                alert.setContentText("Twoje konto zostało założone. Możesz się teraz zalogować!");
            else
                alert.setContentText("Uzytkownik dodany!");
            alert.showAndWait();
            if (!ifAdmin)
                App.setRoot("logowanie");
            else {
                Stage stage = (Stage) backBtn.getScene().getWindow();
                stage.fireEvent(
                    new WindowEvent(
                        stage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                    )
                );
            }
        } else {
            if (sthEmpty)
                errorLabel.setText("Wypełnij wszystkie pola!");
            else {
                errorLabel.setText("Konto o takim e-mail lub pesel istnieje!");
                emailTF.setText("");
                peselTF.setText("");
            }
            errorLabel.setVisible(true);
        }
    }

    @FXML
    public void backToLogin() throws IOException {
        App.setRoot("logowanie");
    }

    @FXML
    void initialize(){
        phoneTF.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));

        pkkTF.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));

        peselTF.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));

        phoneTF.setOnKeyTyped(event -> {
            String string = phoneTF.getText();
            if (string.length() > 9) {
                phoneTF.setText(string.substring(0, 9));
                phoneTF.positionCaret(string.length());
            }
        });

        firstnameTF.setOnKeyTyped(event -> {
            String string = firstnameTF.getText();
            if (string.length() > 25) {
                firstnameTF.setText(string.substring(0, 25));
                firstnameTF.positionCaret(string.length());
            }
        });

        lastnameTF.setOnKeyTyped(event -> {
            String string = lastnameTF.getText();
            if (string.length() > 25) {
                lastnameTF.setText(string.substring(0, 25));
                lastnameTF.positionCaret(string.length());
            }
        });

        emailTF.setOnKeyTyped(event -> {
            String string = emailTF.getText();
            if (string.length() > 30) {
                emailTF.setText(string.substring(0, 30));
                emailTF.positionCaret(string.length());
            }
        });

        passwordTF.setOnKeyTyped(event -> {
            String string = passwordTF.getText();
            if (string.length() > 25) {
                passwordTF.setText(string.substring(0, 25));
                passwordTF.positionCaret(string.length());
            }
        });

        pkkTF.setOnKeyTyped(event -> {
            String string = pkkTF.getText();
            if (string.length() > 20) {
                pkkTF.setText(string.substring(0, 20));
                pkkTF.positionCaret(string.length());
            }
        });

        peselTF.setOnKeyTyped(event -> {
            String string = peselTF.getText();
            if (string.length() > 11) {
                peselTF.setText(string.substring(0, 11));
                peselTF.positionCaret(string.length());
            }
        });
    }
}
