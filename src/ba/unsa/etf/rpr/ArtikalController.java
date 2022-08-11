package ba.unsa.etf.rpr;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ArtikalController implements Initializable {
    @FXML
    Label naziv, kategorija, cijena, lokacija, deskripcija;


    public Label getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv.setText(naziv);
    }

    public Label getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija.setText(kategorija);
    }

    public Label getCijena() {
        return cijena;
    }

    public void setCijena(String cijena) {
        this.cijena.setText(cijena);
    }

    public Label getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija.setText(lokacija);
    }

    public Label getDeskripcija() {
        return deskripcija;
    }

    public void setDeskripcija(String deskripcija) {
        this.deskripcija.setText(deskripcija);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // Image slika = new Image(getClass().getResourceAsStream("/img/aboutPicture.jpg"));
       // imageView.setImage(slika);
       // imageView.setOpacity(0.3);
    }
}

