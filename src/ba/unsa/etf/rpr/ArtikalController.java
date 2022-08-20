package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


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
    @FXML
    Button kupiBtn;
    @FXML
    Button obrisiBtn;

    private String korisnickoIme;

    private final DataModel model ;
    private KorisnikDAO dao = KorisnikDAO.getInstance();

    public ArtikalController(DataModel model){
        this.model = model;
    }
    public void setKorisnickoIme(Korisnik korisnik) {
        korisnickoIme = korisnik.getKorisnickoIme();
    }




    public void clickKupi(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Uspješno!");
        alert.setContentText("Uspješno ste kupili artikal " + naziv.getText());
        alert.show();
        dao.obrisiArtikal(naziv.getText(),deskripcija.getText());

        Kategorija kategorija1 = new Kategorija(kategorija.getText());
        Artikal kupljeniArtikal = new Artikal(naziv.getText(),kategorija1,cijena.getText(),lokacija.getText(),deskripcija.getText(),korisnickoIme);
        Artikal prodaniArtikal = new Artikal(naziv.getText(),kategorija1,cijena.getText(),lokacija.getText(),deskripcija.getText(),korisnik.getText());
        dao.dodajKupljeniArtikal(kupljeniArtikal);
        dao.dodajProdaniArtikal(prodaniArtikal);

        Stage stage = (Stage) kupiBtn.getScene().getWindow();
        stage.close();
    }

    public void clickObrisi(ActionEvent actionEvent){
        dao.obrisiArtikal(naziv.getText(),deskripcija.getText());
        Stage stage = (Stage) obrisiBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        naziv.textProperty().bind(model.nazivProperty());
        kategorija.textProperty().bind(model.kategorijaProperty());
        cijena.textProperty().bind(model.cijenaProperty());
        lokacija.textProperty().bind(model.lokacijaProperty());
        deskripcija.textProperty().bind(model.deskripcijaProperty());
        korisnik.textProperty().bind(model.korisnikProperty());



        if(korisnickoIme.equals(korisnik.getText())){
            kupiBtn.setVisible(false);

        } else{

            obrisiBtn.setVisible(false);
        }
    }
}

