package ba.unsa.etf.rpr;


import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistracijaController implements Initializable {
    public TextField fldIme, fldPrezime, fldKorisnicko;
    public PasswordField fldPassword;
    public DatePicker datePicker;
    private boolean ispravno = false;
    public Button btnRegistrujSe;
    private KorisnikDAO dao;

    public RegistracijaController(){
        dao = KorisnikDAO.getInstance();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        datePicker.setEditable(false);

        datePicker.setValue(LocalDate.now());

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
        if (datePicker.getValue().toString().isBlank()){
            ispravno = false;
            datePicker.getStyleClass().removeAll("ispravno");
            datePicker.getStyleClass().add("neispravno");
        }
        else{
            ispravno = true;
            datePicker.getStyleClass().removeAll("neispravno");
            datePicker.getStyleClass().add("ispravno");
        }
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
        if(!fldIme.getText().isBlank() && !fldPrezime.getText().isBlank() && !datePicker.getValue().toString().isBlank() && !fldKorisnicko.getText().isBlank() && !fldPassword.getText().isBlank() && ispravno){
            Osoba osoba = new Osoba(fldIme.getText(),fldPrezime.getText(),datePicker.getValue().toString());
            Korisnik korisnik = new Korisnik(osoba, fldKorisnicko.getText(), fldPassword.getText());

            Stage stage = (Stage) btnRegistrujSe.getScene().getWindow();
            stage.close();
            ButtonType accept = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION,"",accept,cancel);
            alert1.setTitle("Uvjeti rada aplikacije");
            alert1.setHeaderText("Korisnik ove aplikacije je dužan da poštuje sve moralna i zakonska pravila kupoprodaje.\n" +
                    "Kršenjem nekog od pravila, korisnik snosi sve posljedice.\n" +
                    "Administrator aplikacije ne odgovara za kršenje pravila.");
            alert1.setContentText("Da li pristajete na uvjete rada aplikacije?");
            Optional<ButtonType> result = alert1.showAndWait();
            if(!result.isPresent()){
                // alert is exited, no button has been pressed.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška!");
                alert.setHeaderText("Niste pristali na uvjete rada aplikacije!");
                alert.setContentText("Račun nije kreiran.");
                alert.showAndWait();
                alert1.close();
            }

            else if(result.get() == accept){
                //oke button is pressed
                dao.dodajKorisnika(korisnik);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Registracija uspješna!");
                alert.setHeaderText("Uspješno ste se registrovali!");
                alert.setContentText("Možete se ulogovati na vaš račun.");
                alert.showAndWait();
            }

            else if(result.get() ==cancel){
                // cancel button is pressed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška!");
                alert.setHeaderText("Niste pristali na uvjete rada aplikacije!");
                alert.setContentText("Račun nije kreiran.");
                alert.showAndWait();
                alert1.close();
            }



        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registracija neuspješna!");
            alert.setHeaderText("Jedno ili više polja nije ispravno popunjeno!");
            alert.setContentText("Pokušajte ponovo!");
            alert.showAndWait();
        }
    }
}
