package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController{
    private final KorisnikDAO dao;
    public TextField fldKorisnickoIme;
    public PasswordField fldPassword;
    public Button btnPrijava;
    public LoginController(){ dao = KorisnikDAO.getInstance();}

    public String getKorisnickoIme (){

        return fldKorisnickoIme.getText();
    }
    public void setLanguageEnglish(ActionEvent actionEvent){

        Locale.setDefault(new Locale("en_US","EN"));
    }
    public void setLanguageBosnian(ActionEvent actionEvent){
        Locale.setDefault(new Locale("bs","BA"));
    }
    public void clickRegistracija(ActionEvent actionEvent) throws IOException {
        fldKorisnickoIme.setText("");
        fldPassword.setText("");
        Stage secondaryStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registracija.fxml"), bundle);
        RegistracijaController registracijaController = new RegistracijaController();
        loader.setController(registracijaController);
        Parent root = loader.load();
        secondaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        secondaryStage.setTitle("Registracija");
        secondaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        secondaryStage.setResizable(false);
        secondaryStage.show();
    }

    public void clickPrijava(ActionEvent actionEvent) throws IOException {
        if(dao.nadjiKorisnika(fldKorisnickoIme.getText()) != null && dao.nadjiPasswordKorisnika(fldPassword.getText()) != null) {

            Korisnik k = dao.nadjiKorisnika(fldKorisnickoIme.getText());

            Stage stage = (Stage) btnPrijava.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"),bundle);

            loader.setController(new GlavnaController());
            Parent root = loader.load();
            GlavnaController glavnaController = loader.getController();
            glavnaController.setKorisnickoIme(fldKorisnickoIme.getText());
            glavnaController.setAutorKomentara(fldKorisnickoIme.getText());

            if((k.getOsoba().getIme()).endsWith("a") || (k.getOsoba().getIme()).endsWith("k"))
            glavnaController.setLabelaZensko(k.getOsoba().getIme());
            else
                glavnaController.setLabelaMusko(k.getOsoba().getIme());
            primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
            primaryStage.setTitle("IE - Kupoprodaja");
            primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            primaryStage.show();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška!");
            alert.setHeaderText("Korisnik sa ovim podacima ne postoji!");
            alert.setContentText("Pokušajte ponovo!");
            alert.showAndWait();
        }
    }

}
