package ba.unsa.etf.rpr;


import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistracijaController implements Initializable {
    public TextField fldIme, fldPrezime, fldDatum, fldKorisnicko, fldPassword;
    private boolean ispravno = false;
    public Button btnRegistrujSe;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fldIme.textProperty().addListener((obs,oldValue,newValue) -> {
            if(newValue.isBlank()){
                fldIme.getStyleClass().removeAll("ispravno");
                fldIme.getStyleClass().add("neispravno");
                ispravno = false;
            }
            else{
                ispravno = true;
                fldIme.getStyleClass().removeAll("neispravno");
                fldIme.getStyleClass().add("ispravno");
            }
        });
        fldPrezime.textProperty().addListener((obs,oldValue,newValue) -> {
            if(newValue.isBlank()){
                ispravno = false;
                fldPrezime.getStyleClass().removeAll("ispravno");
                fldPrezime.getStyleClass().add("neispravno");
            }
            else{
                ispravno = true;
                fldPrezime.getStyleClass().removeAll("neispravno");
                fldPrezime.getStyleClass().add("ispravno");
            }
        });
        fldDatum.textProperty().addListener((obs,oldValue,newValue) -> {
            if(newValue.isBlank()){
                ispravno = false;
                fldDatum.getStyleClass().removeAll("ispravno");
                fldDatum.getStyleClass().add("neispravno");
            }
            else{
                ispravno = true;
                fldDatum.getStyleClass().removeAll("neispravno");
                fldDatum.getStyleClass().add("ispravno");
            }
        });
        fldKorisnicko.textProperty().addListener((obs,oldValue,newValue) -> {
            if(newValue.isBlank()){
                ispravno = false;
                fldKorisnicko.getStyleClass().removeAll("ispravno");
                fldKorisnicko.getStyleClass().add("neispravno");
            }
            else{
                ispravno = true;
                fldKorisnicko.getStyleClass().removeAll("neispravno");
                fldKorisnicko.getStyleClass().add("ispravno");
            }
        });
        fldPassword.textProperty().addListener((obs,oldValue,newValue) -> {
            if(newValue.isBlank()){
                ispravno = false;
                fldPassword.getStyleClass().removeAll("ispravno");
                fldPassword.getStyleClass().add("neispravno");
            }
            else{
                ispravno = true;
                fldPassword.getStyleClass().removeAll("neispravno");
                fldPassword.getStyleClass().add("ispravno");
            }
        });

    }

    public void clickRegistrujSe(ActionEvent actionEvent){
        if(!fldIme.getText().isBlank() && !fldPrezime.getText().isBlank() && !fldDatum.getText().isBlank() && !fldKorisnicko.getText().isBlank() && !fldPassword.getText().isBlank() && ispravno){
            Stage stage = (Stage) btnRegistrujSe.getScene().getWindow();
            stage.close();
        }
    }
}
