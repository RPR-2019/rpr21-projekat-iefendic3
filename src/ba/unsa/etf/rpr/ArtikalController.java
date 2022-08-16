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
    Label naziv;
    @FXML
    Label kategorija;
    @FXML
    Label cijena;
    @FXML
    Label lokacija;
    @FXML
    Label deskripcija;
    @FXML
    Label korisnik;

    private final DataModel model ;

    public ArtikalController(DataModel model){
        this.model = model;
    }

//    public ArtikalController() {
//        this.naziv.setText("");
//        this.kategorija.setText("");
//        this.cijena.setText("");
//        this.lokacija.setText("");
//        this.deskripcija.setText("");
//
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        naziv.textProperty().bind(model.nazivProperty());
        kategorija.textProperty().bind(model.kategorijaProperty());
        cijena.textProperty().bind(model.cijenaProperty());
        lokacija.textProperty().bind(model.lokacijaProperty());
        deskripcija.textProperty().bind(model.deskripcijaProperty());
        korisnik.textProperty().bind(model.korisnikProperty());
    }
}

