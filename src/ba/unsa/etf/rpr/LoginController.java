package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController{
    private KorisnikDAO dao;
    public TextField fldKorisnickoIme;
    public PasswordField fldPassword;
    public Button btnPrijava;
    public LoginController(){ dao = KorisnikDAO.getInstance();}

    public void clickRegistracija(ActionEvent actionEvent) throws IOException {
        fldKorisnickoIme.setText("");
        fldPassword.setText("");
        Stage secondaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registracija.fxml"));
        RegistracijaController registracijaController = new RegistracijaController();
        loader.setController(registracijaController);
        Parent root = loader.load();
        secondaryStage.setTitle("Registracija");
        secondaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        secondaryStage.show();
    }

    public void clickPrijava(ActionEvent actionEvent){
        if(dao.nadjiKorisnika(fldKorisnickoIme.getText()) != null && dao.nadjiPasswordKorisnika(fldPassword.getText()) != null) {
            Stage stage = (Stage) btnPrijava.getScene().getWindow();
            stage.close();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška!");
            alert.setHeaderText("Korisnik sa ovim podacima ne postoji!");
            alert.setContentText("Pokušajte ponovo!");
            alert.showAndWait();
        }
    }

}
