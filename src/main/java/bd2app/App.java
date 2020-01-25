package bd2app;

import bd2app.dao.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private static String userType;

    private static int userID;
    private static Stage stage;
    public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Entities");
    public static EntityManager entityManager = entityManagerFactory.createEntityManager();
    public static UzytkownicyDao udao = new UzytkownicyDao(entityManagerFactory, entityManager);
    public static InstruktorzyDao idao = new InstruktorzyDao(entityManagerFactory, entityManager);
    public static InstruktorzyKategoriaDao ikdao = new InstruktorzyKategoriaDao(entityManagerFactory, entityManager);
    public static RezerwacjeDao rdao = new RezerwacjeDao(entityManagerFactory, entityManager);
    public static TransakcjeDao tdao = new TransakcjeDao(entityManagerFactory, entityManager);
    public static UslugiDao usdao = new UslugiDao(entityManagerFactory, entityManager);
    public static KategoriaEntityDao kdao = new KategoriaEntityDao(entityManagerFactory, entityManager);
    public static UstawieniaDao ustdao = new UstawieniaDao(entityManagerFactory, entityManager);

    @Override
    public void start(Stage st) throws IOException {
        System.out.println(ustdao.findByNazwa("limit_rezerwacji_oczekujacych").getWartosc());
        stage = st;
        scene = new Scene(loadFXML("logowanie"));
        stage.setScene(scene);
        stage.setTitle("Logowanie do aplikacji");
        stage.show();
    }

    static void setDatabaseObjects(UzytkownicyDao u, InstruktorzyDao i, RezerwacjeDao r, TransakcjeDao t, UslugiDao us, KategoriaEntityDao kd) {
        App.udao = u;
        App.idao = i;
        App.tdao = t;
        App.rdao = r;
        App.usdao = us;
        App.kdao = kd;
    }

    static void setRoot(String fxml) throws IOException {
        stage.setTitle(fxml);
        scene.setRoot(loadFXML(fxml));
    }

    static void goBack() throws IOException {
        switch (userType) {
            case "Administrator":
                App.setRoot("administrator");
                break;
            case "Instruktor":
                App.setRoot("instruktor");
                break;
            case "Ksiegowy":
                App.setRoot("ksiegowy");
                break;
            case "Biurowy":
                App.setRoot("biurowy");
                break;
            case "Kursant":
                App.setRoot("kursant");
                break;
        }
    }

    static void logout() throws IOException {
        App.userType = "";
        App.userID = -1;
        App.setRoot("logowanie");
    }

    static String getUserType() {
        return userType;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        App.userID = userID;
    }

    static void setUserType(String userType) {
        App.userType = userType;
    }

    protected static FXMLLoader getLoader(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    protected static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}