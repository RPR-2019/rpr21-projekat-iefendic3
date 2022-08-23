package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class KomentarController implements Initializable {
    private KorisnikDAO dao;
    private String korisnickoIme;
    private String autor;
    private Komentar komentar = new Komentar("","",Recenzija.POZITIVNA,"");

    @FXML
    TextArea txtArea;
    @FXML
    RadioButton radioPozitivna;
    @FXML
    RadioButton radioNegativna;

    public KomentarController(){ dao = KorisnikDAO.getInstance();}

    public void setKorisnickoIme(String korisnik) {
        korisnickoIme = korisnik;
    }
    public void setAutorKomentara(String autor1){
        autor=autor1;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void clickObjaviKomentar(ActionEvent actionEvent){
        komentar.setKorisnickoIme(korisnickoIme);
        if(txtArea.getText()!=null && (radioPozitivna.isSelected() || radioNegativna.isSelected())){

            komentar.setTekstKomentara(txtArea.getText());
            if(radioPozitivna.isSelected()){
                komentar.setRecenzija(Recenzija.POZITIVNA);
            }
            else if(radioNegativna.isSelected()){
                komentar.setRecenzija(Recenzija.NEGATIVNA);
            }
            komentar.setAutor(autor);
            dao.dodajKomentar(komentar);
            Stage stage = (Stage) radioPozitivna.getScene().getWindow();
            stage.close();
        }



    }
}
