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
    public TextField fldIme, fldPrezime, fldKorisnicko, fldMjesto, fldAdresa, fldBrojTel;
    public PasswordField fldPassword;
    public DatePicker datePicker;
    private boolean ispravno = false;
    private boolean ispravanPassword = false;
    public Button btnRegistrujSe;
    private KorisnikDAO dao;
    public ProgressBar progressBar;

    public RegistracijaController(){
        dao = KorisnikDAO.getInstance();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fldPassword.textProperty().addListener(
                (obs, oldV, newV)->{
                    progressBar.setProgress((double)newV.length()/10);
                    if(newV.length()<5){
                        ispravanPassword=false;
                        progressBar.getStyleClass().removeAll("crveniProgress","zeleniProgress","zutiProgress");
                        progressBar.getStyleClass().add("crveniProgress");
                    }
                    else if(newV.length()>=5 && newV.length()<8){
                        progressBar.getStyleClass().removeAll("crveniProgress","zeleniProgress","zutiProgress");
                        progressBar.getStyleClass().add("zutiProgress");
                    }
                    else{
                        ispravanPassword=true;
                        progressBar.getStyleClass().removeAll("crveniProgress","zeleniProgress","zutiProgress");
                        progressBar.getStyleClass().add("zeleniProgress");
                    }
                });

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
        fldMjesto.textProperty().addListener((obs,oldValue,newValue) -> {
            if(newValue.isBlank()){
                ispravno = false;
                fldMjesto.getStyleClass().removeAll("ispravno");
                fldMjesto.getStyleClass().add("neispravno");
            }
            else{
                ispravno = true;
                fldMjesto.getStyleClass().removeAll("neispravno");
                fldMjesto.getStyleClass().add("ispravno");
            }
        });
        fldAdresa.textProperty().addListener((obs,oldValue,newValue) -> {
            if(newValue.isBlank()){
                ispravno = false;
                fldAdresa.getStyleClass().removeAll("ispravno");
                fldAdresa.getStyleClass().add("neispravno");
            }
            else{
                ispravno = true;
                fldAdresa.getStyleClass().removeAll("neispravno");
                fldAdresa.getStyleClass().add("ispravno");
            }
        });
        fldBrojTel.textProperty().addListener((obs,oldValue,newValue) -> {
            if(newValue.isBlank()){
                ispravno = false;
                fldBrojTel.getStyleClass().removeAll("ispravno");
                fldBrojTel.getStyleClass().add("neispravno");
            }
            else{
                ispravno = true;
                fldBrojTel.getStyleClass().removeAll("neispravno");
                fldBrojTel.getStyleClass().add("ispravno");
            }
        });
        fldPassword.textProperty().addListener((obs,oldValue,newValue) -> {
            if(newValue.length()<5){
                ispravanPassword = false;
                fldPassword.getStyleClass().removeAll("ispravno");
                fldPassword.getStyleClass().add("neispravno");
            }
            else{
                ispravanPassword = true;
                fldPassword.getStyleClass().removeAll("neispravno");
                fldPassword.getStyleClass().add("ispravno");
            }
        });

    }

    public void clickRegistrujSe(ActionEvent actionEvent){
        if(!fldIme.getText().isBlank() && !fldPrezime.getText().isBlank() && !datePicker.getValue().toString().isBlank() && !fldKorisnicko.getText().isBlank() && !fldPassword.getText().isBlank()
                && !fldMjesto.getText().isBlank() && !fldAdresa.getText().isBlank() && !fldBrojTel.getText().isBlank() && ispravno) {
            if (ispravanPassword == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Gre??ka!");
                alert.setHeaderText("Password je prekratak!");
                alert.setContentText("Poku??ajte ponovo!");
                alert.showAndWait();

            } else {
                Osoba osoba = new Osoba(fldIme.getText(), fldPrezime.getText(), datePicker.getValue().toString());
                Korisnik korisnik = new Korisnik(osoba, fldKorisnicko.getText(), fldPassword.getText(), fldMjesto.getText(),fldAdresa.getText(),fldBrojTel.getText());

                Stage stage = (Stage) btnRegistrujSe.getScene().getWindow();
                stage.close();

                //
                //ProfilController profilController = loader.getController();


                ButtonType accept = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "", accept, cancel);
                alert1.setTitle("Uvjeti rada aplikacije");
                alert1.setHeaderText("Korisnik ove aplikacije je du??an da po??tuje sve moralna i zakonska pravila kupoprodaje.\n" +
                        "Kr??enjem nekog od pravila, korisnik snosi sve posljedice.\n" +
                        "Administrator aplikacije ne odgovara za kr??enje pravila.");
                alert1.setContentText("Da li pristajete na uvjete rada aplikacije?");

                Optional<ButtonType> result = alert1.showAndWait();
                if (!result.isPresent()) {
                    // alert is exited, no button has been pressed.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Gre??ka!");
                    alert.setHeaderText("Niste pristali na uvjete rada aplikacije!");
                    alert.setContentText("Ra??un nije kreiran.");
                    alert.showAndWait();
                    alert1.close();
                } else if (result.get() == accept) {
                    //oke button is pressed
                    dao.dodajKorisnika(korisnik);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Registracija uspje??na!");
                    alert.setHeaderText("Uspje??no ste se registrovali!");
                    alert.setContentText("Mo??ete se ulogovati na va?? ra??un.");
                    alert.showAndWait();
                } else if (result.get() == cancel) {
                    // cancel button is pressed
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Gre??ka!");
                    alert.setHeaderText("Niste pristali na uvjete rada aplikacije!");
                    alert.setContentText("Ra??un nije kreiran.");
                    alert.showAndWait();
                    alert1.close();
                }


            }
        }
        else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registracija neuspje??na!");
                alert.setHeaderText("Jedno ili vi??e polja nije ispravno popunjeno!");
                alert.setContentText("Poku??ajte ponovo!");
                alert.showAndWait();
            }

    }
}
