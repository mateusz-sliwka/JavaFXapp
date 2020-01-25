package bd2app;

import bd2app.model.InstruktorzyEntity;
import bd2app.model.UzytkownicyEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class KontoController {
    @FXML
    private Button logoutButton;

    @FXML
    private Button returnButton;
    @FXML
    private Label peselLabel;

    @FXML
    private Label pkkLabel;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField pesel;

    @FXML
    private TextField pkk;

    @FXML
    private TextField phone;

    @FXML
    private TextField mail;

    @FXML
    private PasswordField password;

    @FXML
    private Button applyChangesButton;

    @FXML
    void applyChanges(ActionEvent event) {
        if(App.getUserType() == "Kursant"){
            UzytkownicyEntity user = App.udao.findById(App.getUserID());
            user.setImię(name.getText());
            user.setNazwisko(surname.getText());
            user.setPesel(pesel.getText());
            user.setPkk(pkk.getText());
            user.setTelefon(Integer.parseInt(phone.getText()));
            user.setEmail(mail.getText());
            user.setHasło(password.getText());
            App.udao.update(user);
        }
        else if (App.getUserType() != "Instruktor"){
            UzytkownicyEntity user = App.udao.findById(App.getUserID());
            user.setImię(name.getText());
            user.setNazwisko(surname.getText());
            user.setTelefon(Integer.parseInt(phone.getText()));
            user.setEmail(mail.getText());
            user.setHasło(password.getText());
            App.udao.update(user);
        }
        else{
            InstruktorzyEntity user = App.idao.findById(App.getUserID());
            user.setImię(name.getText());
            user.setNazwisko(surname.getText());
            user.setTelefon(Integer.parseInt(phone.getText()));
            user.setEmail(mail.getText());
            user.setHasło(password.getText());
            App.idao.update(user);
        }
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        App.goBack();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        App.logout();
    }

    @FXML
    void initialize() {
        if(App.getUserType() == "Kursant"){
            pkk.setVisible(true);
            pesel.setVisible(true);
            pkkLabel.setVisible(true);
            peselLabel.setVisible(true);
            UzytkownicyEntity user = App.udao.findById(App.getUserID());
            name.setText(user.getImię());
            surname.setText(user.getNazwisko());
            pesel.setText(user.getPesel());
            pkk.setText(user.getPkk());
            phone.setText(Integer.toString(user.getTelefon()));
            mail.setText(user.getEmail());
            password.setText(user.getHasło());
        }
        else if (App.getUserType() != "Instruktor"){
            pkk.setVisible(false);
            pesel.setVisible(false);
            pkkLabel.setVisible(false);
            peselLabel.setVisible(false);
            UzytkownicyEntity user = App.udao.findById(App.getUserID());
            name.setText(user.getImię());
            surname.setText(user.getNazwisko());
            phone.setText(Integer.toString(user.getTelefon()));
            mail.setText(user.getEmail());
            password.setText(user.getHasło());
        }
        else{
            pkk.setVisible(false);
            pesel.setVisible(false);
            pkkLabel.setVisible(false);
            peselLabel.setVisible(false);
            InstruktorzyEntity user = App.idao.findById(App.getUserID());
            name.setText(user.getImię());
            surname.setText(user.getNazwisko());
            phone.setText(Integer.toString(user.getTelefon()));
            mail.setText(user.getEmail());
            password.setText(user.getHasło());
        }
    }
}
