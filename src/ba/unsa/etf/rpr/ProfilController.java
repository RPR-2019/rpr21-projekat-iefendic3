package ba.unsa.etf.rpr;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;



public class ProfilController implements Initializable {
    public ImageView slikaProfila;
    public Label labelSlikaProfila, labelaIme, labelaPrezime, labelaDatum, labelaMjesto, labelaAdresa, labelaBrojTel;
    private String ime, prezime, datum, mjesto, adresa, brojTel;
    private KorisnikDAO dao;
    private Korisnik korisnik;


    public ProfilController(){ dao = KorisnikDAO.getInstance();}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(getClass().getResourceAsStream("/img/user.png"));
        slikaProfila.setImage(image);
        labelSlikaProfila.setGraphic(slikaProfila);

    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
        labelaIme.setText(labelaIme.getText()+" "+korisnik.getOsoba().getIme());
        labelaPrezime.setText(labelaPrezime.getText()+" "+korisnik.getOsoba().getPrezime());
        labelaDatum.setText(labelaDatum.getText()+" "+korisnik.getOsoba().dajDatum());
        labelaMjesto.setText(labelaMjesto.getText()+" "+korisnik.getMjesto());
        labelaAdresa.setText(labelaAdresa.getText()+" "+korisnik.getAdresa());
        labelaBrojTel.setText(labelaBrojTel.getText()+" "+korisnik.getBrojTelefona());
    }
}
