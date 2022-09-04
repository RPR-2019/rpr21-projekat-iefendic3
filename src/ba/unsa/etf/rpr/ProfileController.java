package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class ProfileController implements Initializable {


    public Label  labelaIme, labelaPrezime, labelaDatum, labelaMjesto, labelaAdresa, labelaBrojTel;
    private String korisnickoIme;
    private String korisnickoIme1;
    private String autor;
    private User user;
    private final UserDAO dao;
    final FileChooser fc = new FileChooser();
    ResourceBundle bundle = ResourceBundle.getBundle("Translation");

    @FXML
    ImageView slikaProfila;
    @FXML
    Hyperlink btnSlikaProfila;

    public ProfileController(){ dao = UserDAO.getInstance();}

    public void setKorisnickoIme(String korisnickoIme){
        korisnickoIme1=korisnickoIme;
    }

    public void clickKupljeniArtikli(ActionEvent actionEvent) throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kupljeni.fxml"),bundle);
        BoughtArticlesController controller = new BoughtArticlesController();
        loader.setController(controller);
        controller.setKorisnickoIme(user);
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Kupljeni artikli");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public void clickProdaniArtikli(ActionEvent actionEvent) throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/prodani.fxml"),bundle);
        SoldArticlesController controller = new SoldArticlesController();
        loader.setController(controller);
        controller.setKorisnickoIme(user);
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Prodani artikli");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public void clickAktivniArtikli(ActionEvent actionEvent) throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/aktivni.fxml"),bundle);
        ActiveArticlesController controller = new ActiveArticlesController();
        loader.setController(controller);
        controller.setKorisnickoIme(user);
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Aktivni artikli");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    public void clickKomentari(ActionEvent actionEvent) throws IOException{
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/komentari.fxml"),bundle);
        CommentsController controller = new CommentsController();
        loader.setController(controller);
        controller.setKorisnickoIme(user);
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


    public void setKorisnik(User user, String autor1) throws IOException{
        this.autor = autor1;
        this.user = user;
        korisnickoIme = user.getKorisnickoIme();
        labelaIme.setText(labelaIme.getText()+" "+ user.getOsoba().getIme());
        labelaPrezime.setText(labelaPrezime.getText()+" "+ user.getOsoba().getPrezime());
        labelaDatum.setText(labelaDatum.getText()+" "+ user.getOsoba().dajDatum());
        labelaMjesto.setText(labelaMjesto.getText()+" "+ user.getMjesto());
        labelaAdresa.setText(labelaAdresa.getText()+" "+ user.getAdresa());
        labelaBrojTel.setText(labelaBrojTel.getText()+" "+ user.getBrojTelefona());


        if(autor!=null){
            if(!autor.equals(korisnickoIme)){
                btnSlikaProfila.setDisable(true);
            }
        }

        ResultSet rs = dao.dajSlikuKorisnika(korisnickoIme);

        InputStream inputStream;
        try{
            while(rs.next()){
                inputStream = rs.getBinaryStream("slika");
                if (inputStream != null && inputStream.available() > 1) {

                    Image imge = new Image(inputStream);
                    slikaProfila.setImage(imge);

                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }


        btnSlikaProfila.setGraphic(slikaProfila);
    }

    public void setAutor(String autor1){
        this.autor = autor1;
    }
}
