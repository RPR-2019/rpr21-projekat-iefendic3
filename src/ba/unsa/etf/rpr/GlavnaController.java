package ba.unsa.etf.rpr;


import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
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
    /*private final DataModel model ;
    public GlavnaController(DataModel model){
        this.model = model;
    }*/
    @FXML
    public Label labelGreska;
    public ListView<Artikal> lvArtikli = new ListView<Artikal>();

    public void setKorisnickoIme(String korisnickoIme){
        this.korisnickoIme=korisnickoIme;
    }

    public GlavnaController(){dao = KorisnikDAO.getInstance();}

    public void setLabelaZensko(String string){
        labelDobrodosao.setText(labelDobrodosao.getText()+"Dobrodošao/la, "+string);
    }
    public void setLabelaMusko(String string){
        labelDobrodosao.setText(labelDobrodosao.getText()+"Dobrodošao/la, "+string+"e");
    }

    public void setArtikli(ArrayList<Artikal> artikli){
        ObservableList<Artikal> observableList = FXCollections.observableList(artikli);
        lvArtikli.setItems(observableList);
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/objava.fxml"));
        loader.setController(new ObjavaController());
        Parent root=loader.load();
        ObjavaController objavaController=loader.getController();

        Korisnik korisnik = dao.nadjiKorisnika(korisnickoIme);
        objavaController.setKorisnickoIme(korisnik);


        //Parent root=(new FXMLLoader(getClass().getResource("/fxml/objava.fxml"))).load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Objavite artikal");
        Scene scene = new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

        //Stage stage = (Stage) btnObjavi.getScene().getWindow();
        //stage.close();
        if(primaryStage.getUserData() != null) {
            /*ArrayList<Artikal> artikli = (ArrayList<Artikal>) primaryStage.getUserData();
            for(Artikal a: artikli){
                lvArtikli.getItems().add(a);
            }*/
            ArrayList<Kategorija> kategorije = (ArrayList<Kategorija>) primaryStage.getUserData();
            for (Kategorija k : kategorije) {
                lvKategorije.getItems().add(k);
            }

        }
        try {
            ResultSet rsArtikli = dao.dajArtikle();
            ResultSet rs = dao.dajKategorije();
            Artikal zadnjiArtikal = new Artikal();
            //Zadnji element
            while(rsArtikli.next()){
                Kategorija kategorija1 = new Kategorija(rsArtikli.getString(2));
                zadnjiArtikal.setNaziv(rsArtikli.getString(1));
                zadnjiArtikal.setKategorija(kategorija1);
                zadnjiArtikal.setCijena(rsArtikli.getString(3));
                zadnjiArtikal.setLokacija(rsArtikli.getString(4));
                zadnjiArtikal.setDeskripcija(rsArtikli.getString(5));
                zadnjiArtikal.setKorisnik(rsArtikli.getString(6));
            }

            if(!lvArtikli.getItems().contains(zadnjiArtikal)){
                lvArtikli.getItems().add(zadnjiArtikal);
            }
            /*while (rs.next()) {
                lvKategorije.getItems().add(new Kategorija(rs.getString(1)));
            }*/

        } catch (SQLException e){
            e.printStackTrace();
        }
        primaryStage.setWidth(USE_COMPUTED_SIZE-0.0001);

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



    @FXML public void handleMouseClick(MouseEvent arg0) throws IOException {
        DataModel model1 = new DataModel();
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/artikal.fxml"));
        ArtikalController controller = new ArtikalController(model1);
        loader.setController(controller);

        model1.setNaziv(lvArtikli.getSelectionModel().getSelectedItem().toString());
        model1.setKategorija(lvArtikli.getSelectionModel().getSelectedItem().getKategorija().toString());
        model1.setCijena(lvArtikli.getSelectionModel().getSelectedItem().getCijena().toString());
        model1.setLokacija(lvArtikli.getSelectionModel().getSelectedItem().getLokacija().toString());
        model1.setDeskripcija(lvArtikli.getSelectionModel().getSelectedItem().getDeskripcija().toString());
        model1.setKorisnik(lvArtikli.getSelectionModel().getSelectedItem().getKorisnik());
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Artikal - "+lvArtikli.getSelectionModel().getSelectedItem());
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();


       // System.out.println("clicked on " + lvArtikli.getSelectionModel().getSelectedItem());
    }

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
            ResultSet rsArtikli = dao.dajArtikle();
            ResultSet rs = dao.dajKategorije();
            while (rs.next()) {
                lvKategorije.getItems().add(new Kategorija(rs.getString(1)));
            }
            while(rsArtikli.next()){
                Kategorija kategorija = new Kategorija(rsArtikli.getString(2));
                lvArtikli.getItems().add(new Artikal(rsArtikli.getString(1), kategorija, rsArtikli.getString(3),rsArtikli.getString(4),
                        rsArtikli.getString(5), rsArtikli.getString(6)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
