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

public class ArticleController implements Initializable {
    @FXML
    Label naziv;
    @FXML
    Label category;
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
    private final UserDAO dao = UserDAO.getInstance();

    public ArticleController(DataModel model){
        this.model = model;
    }
    public void setKorisnickoIme(User user) {
        korisnickoIme = user.getKorisnickoIme();
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

        Category category1 = new Category(category.getText());
        Article kupljeniArtikal = new Article(naziv.getText(), category1,cijena.getText(),lokacija.getText(),deskripcija.getText(),korisnickoIme);
        Article prodaniArtikal = new Article(naziv.getText(), category1,cijena.getText(),lokacija.getText(),deskripcija.getText(),korisnik.getText());
        dao.dodajKupljeniArtikal(kupljeniArtikal);
        dao.dodajProdaniArtikal(prodaniArtikal);

        Stage stage = (Stage) kupiBtn.getScene().getWindow();
        stage.close();
    }

    public void clickObrisi(ActionEvent actionEvent){
        Category category1 = new Category(category.getText());
        Article artikal = new Article(naziv.getText(), category1,cijena.getText(),lokacija.getText(),deskripcija.getText(),korisnickoIme);
        MainController glavnaController = new MainController();


        dao.obrisiArtikal(naziv.getText(),deskripcija.getText());
        Stage stage = (Stage) obrisiBtn.getScene().getWindow();
        stage.close();
    }
    public void clickProfil(ActionEvent actionEvent) throws IOException {
        Stage primaryStage=new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profil.fxml"),bundle);
        loader.setController(new ProfileController());
        Parent root=loader.load();
        ProfileController profileController =loader.getController();

        User k = dao.nadjiKorisnika(korisnik.getText());
        profileController.setKorisnik(k,autor);
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
        category.textProperty().bind(model.kategorijaProperty());
        cijena.textProperty().bind(model.cijenaProperty());
        lokacija.textProperty().bind(model.lokacijaProperty());
        deskripcija.textProperty().bind(model.deskripcijaProperty());
        korisnik.textProperty().bind(model.korisnikProperty());


        Category category1 = new Category(category.getText());
        Article artikal = new Article(naziv.getText(), category1,cijena.getText(),
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

