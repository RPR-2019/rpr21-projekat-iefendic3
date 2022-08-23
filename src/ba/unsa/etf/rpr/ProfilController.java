package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class ProfilController implements Initializable {
    public ImageView slikaProfila;
    public Hyperlink btnSlikaProfila;
    public Label  labelaIme, labelaPrezime, labelaDatum, labelaMjesto, labelaAdresa, labelaBrojTel;
    private String korisnickoIme;
    private String autor;
    private Korisnik korisnik;
    private KorisnikDAO dao;
    final FileChooser fc = new FileChooser();

    public ProfilController(){ dao = KorisnikDAO.getInstance();}

    public void clickKupljeniArtikli(ActionEvent actionEvent) throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kupljeni.fxml"));
        KupljeniController controller = new KupljeniController();
        loader.setController(controller);
        controller.setKorisnickoIme(korisnik);
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Kupljeni artikli");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public void clickProdaniArtikli(ActionEvent actionEvent) throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/prodani.fxml"));
        ProdaniController controller = new ProdaniController();
        loader.setController(controller);
        controller.setKorisnickoIme(korisnik);
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Prodani artikli");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public void clickAktivniArtikli(ActionEvent actionEvent) throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aktivni.fxml"));
        AktivniController controller = new AktivniController();
        loader.setController(controller);
        controller.setKorisnickoIme(korisnik);
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Aktivni artikli");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void clickKomentari(ActionEvent actionEvent) throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/komentari.fxml"));
        KomentariController controller = new KomentariController();
        loader.setController(controller);
        controller.setKorisnickoIme(korisnik);
        controller.setAutor(autor);

        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Komentari");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet rs = dao.dajSlikuKorisnika(korisnickoIme);
        Image image = null;

        try{
            while(rs.next()){
                Blob blob = rs.getBlob(2);
                InputStream is = blob.getBinaryStream();
                image = new Image(is);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

       // Image image = new Image(getClass().getResourceAsStream("/img/user.png"));
        slikaProfila.setImage(image);
        btnSlikaProfila.setGraphic(slikaProfila);
    }


    public void clickBtn (ActionEvent actionEvent) {
        fc.setTitle("My File Chooser");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().clear();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.*"));

        File file = fc.showOpenDialog(null);

        if(file != null){
            try {
                Image img = new Image(file.toURI().toString());
                slikaProfila.setImage(img);
                FileInputStream fileInputStream = new FileInputStream(file);
                dao.dodajSlikuKorisnika(korisnickoIme, fileInputStream, fileInputStream.available());
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            System.out.println("A file is invalid!");
        }
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
        korisnickoIme = korisnik.getKorisnickoIme();
        labelaIme.setText(labelaIme.getText()+" "+korisnik.getOsoba().getIme());
        labelaPrezime.setText(labelaPrezime.getText()+" "+korisnik.getOsoba().getPrezime());
        labelaDatum.setText(labelaDatum.getText()+" "+korisnik.getOsoba().dajDatum());
        labelaMjesto.setText(labelaMjesto.getText()+" "+korisnik.getMjesto());
        labelaAdresa.setText(labelaAdresa.getText()+" "+korisnik.getAdresa());
        labelaBrojTel.setText(labelaBrojTel.getText()+" "+korisnik.getBrojTelefona());
    }

    public void setAutor(String autor1){
        this.autor = autor1;
    }
}
