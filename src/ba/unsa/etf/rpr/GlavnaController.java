package ba.unsa.etf.rpr;


import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class GlavnaController implements Initializable {

    public MenuBar menuBar;
    public Label labelDobrodosao;
    private KorisnikDAO dao;
    private String korisnickoIme;
    public ListView<Kategorija> lvKategorije;
    public Button btnDodajKategoriju, btnObjavi;
    public TextField txtFieldKategorija;
    private boolean postoji = false;
    @FXML
    public Label labelGreska;
    public ListView<Artikal> lvArtikli;

    public void setKorisnickoIme(String korisnickoIme){
        this.korisnickoIme=korisnickoIme;
    }

    public GlavnaController(){dao = KorisnikDAO.getInstance();}

    public void setLabelaZensko(String string){
        labelDobrodosao.setText(labelDobrodosao.getText()+" došao/la, "+string);
    }
    public void setLabelaMusko(String string){
        labelDobrodosao.setText(labelDobrodosao.getText()+" došao/la, "+string+"e");
    }

    public void clickDodajKategoriju(ActionEvent actionEvent) {
        postoji = false;
        Kategorija kategorija = new Kategorija();
        if (txtFieldKategorija.getText() != null) kategorija.setNazivKategorije(txtFieldKategorija.getText());
        for(Kategorija k : lvKategorije.getItems()){
            if(k.getNazivKategorije().toLowerCase().equals(kategorija.getNazivKategorije().toLowerCase())) postoji = true;
        }
        if (!kategorija.getNazivKategorije().equals("") && !postoji ){
                lvKategorije.getItems().add(kategorija);
                txtFieldKategorija.setText("");
                dao.dodajKategoriju(kategorija);
        }
        else {
            labelGreska.setVisible(true);
            txtFieldKategorija.setText("");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> labelGreska.setVisible(false));
            pause.play();
        }
    }

    public void clickObjaviArtikal(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/objava.fxml"));
        loader.setController(new ObjavaController());
        Parent root = loader.load();*/
        Parent root=(new FXMLLoader(getClass().getResource("/fxml/objava.fxml"))).load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Objavite artikal");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.showAndWait();
        if(primaryStage.getUserData() != null) {
            ArrayList<Kategorija> kategorije = (ArrayList<Kategorija>) primaryStage.getUserData();
            for (Kategorija k : kategorije) {
                lvKategorije.getItems().add(k);
            }
        }
    }

    public void clickLogOut(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        loader.setController(new LoginController());
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();

    }
    public void clickProfil(ActionEvent actionEvent) throws IOException {
        Stage primaryStage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profil.fxml"));
        loader.setController(new ProfilController());
        Parent root=loader.load();
       ProfilController profilController=loader.getController();

        Korisnik k = dao.nadjiKorisnika(korisnickoIme);
        profilController.setKorisnik(k);

        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Profil - "+korisnickoIme);
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void clickMenuExit(ActionEvent actionEvent){
        System.exit(0);
    }

    public void clickMenuAbout(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
        loader.setController(new AboutController());
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("About");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public ImageView slikaObjava, slikaLogo;
    public Button btnSlikaAbout;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img = new Image("/img/objava.png");
        Image img1 = new Image("/img/logo-no-bg.png");
        slikaObjava.setImage(img);
        slikaObjava.setFitHeight(80);
        slikaObjava.setPreserveRatio(true);
        //Setting a graphic to the button
        btnObjavi.setGraphic(slikaObjava);
        slikaLogo.setImage(img1);
        btnSlikaAbout.setGraphic(slikaLogo);
        btnSlikaAbout.setBackground(null);

        labelGreska.setVisible(false);
        try {
            ResultSet rs = dao.dajKategorije();
            while (rs.next()) {
                lvKategorije.getItems().add(new Kategorija(rs.getString(1)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
