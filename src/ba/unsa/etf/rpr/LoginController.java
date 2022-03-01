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

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController{
    private KorisnikDAO dao;
    public TextField fldKorisnickoIme;
    public PasswordField fldPassword;
    public Button btnPrijava;
    public LoginController(){ dao = KorisnikDAO.getInstance();}

    public String getKorisnickoIme (){

        return fldKorisnickoIme.getText();
    }

    public void clickRegistracija(ActionEvent actionEvent) throws IOException {
        fldKorisnickoIme.setText("");
        fldPassword.setText("");
        Stage secondaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registracija.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));

            loader.setController(new GlavnaController());
            Parent root = loader.load();
            GlavnaController glavnaController = loader.getController();
            glavnaController.setKorisnickoIme(fldKorisnickoIme.getText());

            if((k.getOsoba().getIme().substring(k.getOsoba().getIme().length() - 1)).equals("a") || ( k.getOsoba().getIme().substring(k.getOsoba().getIme().length() - 1)).equals("k"))
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
