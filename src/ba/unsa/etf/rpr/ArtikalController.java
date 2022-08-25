package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

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
    @FXML
    ImageView slikaArtikla;



    private String korisnickoIme;
    private String autor;

    private final DataModel model ;
    private KorisnikDAO dao = KorisnikDAO.getInstance();

    public ArtikalController(DataModel model){
        this.model = model;
    }
    public void setKorisnickoIme(Korisnik korisnik) {
        korisnickoIme = korisnik.getKorisnickoIme();
    }
    public void setAutor(String korisnik) {
        autor = korisnik;
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
        Kategorija kategorija1 = new Kategorija(kategorija.getText());
        Artikal artikal = new Artikal(naziv.getText(),kategorija1,cijena.getText(),lokacija.getText(),deskripcija.getText(),korisnickoIme);
        GlavnaController glavnaController = new GlavnaController();


        dao.obrisiArtikal(naziv.getText(),deskripcija.getText());
        Stage stage = (Stage) obrisiBtn.getScene().getWindow();
        stage.close();
    }
    public void clickProfil(ActionEvent actionEvent) throws IOException {
        Stage primaryStage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profil.fxml"));
        loader.setController(new ProfilController());
        Parent root=loader.load();
        ProfilController profilController=loader.getController();

        Korisnik k = dao.nadjiKorisnika(korisnik.getText());
        profilController.setKorisnik(k,autor);
        //profilController.setAutor(autor);

        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Profil - "+korisnik.getText());
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        naziv.textProperty().bind(model.nazivProperty());
        kategorija.textProperty().bind(model.kategorijaProperty());
        cijena.textProperty().bind(model.cijenaProperty());
        lokacija.textProperty().bind(model.lokacijaProperty());
        deskripcija.textProperty().bind(model.deskripcijaProperty());
        korisnik.textProperty().bind(model.korisnikProperty());


        Kategorija kategorija1 = new Kategorija(kategorija.getText());
        Artikal artikal = new Artikal(naziv.getText(),kategorija1,cijena.getText(),
                lokacija.getText(),deskripcija.getText(),korisnik.getText());
        ResultSet rs = dao.dajSlikuArtikla(artikal);

        InputStream inputStream;
        try{
            while(rs.next()){
                inputStream = rs.getBinaryStream("slika");
                if (inputStream != null && inputStream.available() > 1) {

                    Image imge = new Image(inputStream);
                    slikaArtikla.setImage(imge);

                }
            }
        } catch (SQLException | IOException e){
            e.printStackTrace();

        }



        if(korisnickoIme.equals(korisnik.getText())){
            kupiBtn.setVisible(false);

        } else{

            obrisiBtn.setVisible(false);
        }
    }
}

