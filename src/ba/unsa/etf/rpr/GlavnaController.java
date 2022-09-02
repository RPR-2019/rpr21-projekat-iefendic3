package ba.unsa.etf.rpr;


import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class GlavnaController implements Initializable {

    @FXML
    public TextField searchBar;
    @FXML
    public Button searchBtn;

    public MenuBar menuBar;
    public Label labelDobrodosao;
    private final KorisnikDAO dao;
    private String korisnickoIme;
    private String autor;
    public ListView<Kategorija> lvKategorije;
    public Button btnDodajKategoriju, btnObjavaArtikla;
    public TextField txtFieldKategorija;
    private boolean postoji = false;
    @FXML
    public Label labelGreska;
    public ListView<Artikal> lvArtikli = new ListView<Artikal>();
    public ArrayList<Artikal> words = new ArrayList<>();
    private Artikal artikalZaBrisanje ;

    @FXML
    private ChoiceBox<String> filterChoice;

    ObservableList<String> options = FXCollections.observableArrayList("Filter","Naziv (a-z)", "Naziv (z-a)", "Cijena (najjeftinije prvo)", "Cijena (najskuplje prvo)","Lokacija (a-z)","Lokacija (z-a)");



    public void setArtikal(Artikal artikal){
        //lvArtikli.getItems().remove(artikal);

        lvArtikli.getItems().clear();
    }

    public void populateArrayList() {
        try {
            ResultSet rs = dao.dajArtikle();
            while (rs.next()) {
                Kategorija kategorija = new Kategorija(rs.getString(2));
                words.add(new Artikal(rs.getString(1), kategorija, rs.getString(3),rs.getString(4),
                       rs.getString(5), rs.getString(6)));

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    @FXML
    public void search(KeyEvent event){
        lvArtikli.getItems().clear();
        lvArtikli.getItems().addAll(searchList(searchBar.getText(),words));
    }

    private List<Artikal> searchList(String searchWords, List<Artikal> listOfStrings){
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.getNaziv().toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }

    public void setKorisnickoIme(String korisnickoIme){
        this.korisnickoIme=korisnickoIme;
    }
    public void setAutorKomentara(String korisnickoIme){
        this.autor=korisnickoIme;
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
            if(k.getNazivKategorije().equalsIgnoreCase(kategorija.getNazivKategorije())) postoji = true;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/objava.fxml"));
        loader.setController(new ObjavaController());
        Parent root=loader.load();
        ObjavaController objavaController=loader.getController();

        Korisnik korisnik = dao.nadjiKorisnika(korisnickoIme);
        objavaController.setKorisnickoIme(korisnik);



        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Objavite artikal");
        Scene scene = new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

        if(primaryStage.getUserData() != null) {

            ArrayList<Kategorija> kategorije = (ArrayList<Kategorija>) primaryStage.getUserData();
            for (Kategorija k : kategorije) {
                lvKategorije.getItems().add(k);
            }

        }
        try {
            ResultSet rsArtikli = dao.dajArtikle();
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

            if(zadnjiArtikal.getNaziv()!=null && zadnjiArtikal.getKategorija().toString()!=null
            && zadnjiArtikal.getCijena()!=null && zadnjiArtikal.getLokacija()!=null
            && zadnjiArtikal.getDeskripcija()!=null && zadnjiArtikal.getKorisnik()!=null) {

                boolean tmp = false;
                for (int i = 0; i < lvArtikli.getItems().size(); i++) {
                    if (lvArtikli.getItems().get(i).getNaziv() != null &&
                            lvArtikli.getItems().get(i).getKategorija().toString() != null &&
                            lvArtikli.getItems().get(i).getDeskripcija() != null &&
                            lvArtikli.getItems().get(i).getLokacija() != null &&
                            lvArtikli.getItems().get(i).getCijena() != null &&
                            lvArtikli.getItems().get(i).getKorisnik() != null
                    )
                        if (lvArtikli.getItems().get(i).getNaziv().equals(zadnjiArtikal.getNaziv())
                                && lvArtikli.getItems().get(i).getKategorija().toString().equals(zadnjiArtikal.getKategorija().toString())
                                && lvArtikli.getItems().get(i).getDeskripcija().equals(zadnjiArtikal.getDeskripcija())
                                && lvArtikli.getItems().get(i).getLokacija().equals(zadnjiArtikal.getLokacija())
                                && lvArtikli.getItems().get(i).getCijena().equals(zadnjiArtikal.getCijena())
                                && lvArtikli.getItems().get(i).getKorisnik().equals(zadnjiArtikal.getKorisnik())
                        ) {
                            tmp = true;
                        }
                }
                if (!tmp) {
                    words.add(zadnjiArtikal);
                    lvArtikli.getItems().add(zadnjiArtikal);
                }

            }
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
        profilController.setKorisnik(k,autor);
        profilController.setAutor(autor);
        profilController.setKorisnickoIme(korisnickoIme);


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

        try {
            if (lvArtikli.getSelectionModel().getSelectedItem() != null) {


                DataModel model1 = new DataModel();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/artikal.fxml"));
                ArtikalController controller = new ArtikalController(model1);
                loader.setController(controller);


                Korisnik korisnik = dao.nadjiKorisnika(korisnickoIme);
                controller.setKorisnickoIme(korisnik);
                controller.setAutor(autor);

                model1.setNaziv(lvArtikli.getSelectionModel().getSelectedItem().toString());
                model1.setKategorija(lvArtikli.getSelectionModel().getSelectedItem().getKategorija().toString());
                model1.setCijena(lvArtikli.getSelectionModel().getSelectedItem().getCijena());
                model1.setLokacija(lvArtikli.getSelectionModel().getSelectedItem().getLokacija());
                model1.setDeskripcija(lvArtikli.getSelectionModel().getSelectedItem().getDeskripcija());
                model1.setKorisnik(lvArtikli.getSelectionModel().getSelectedItem().getKorisnik());


                Stage stage = (Stage) btnObjavaArtikla.getScene().getWindow();
                stage.setWidth(USE_COMPUTED_SIZE - 0.0001);

                Parent root = loader.load();
                primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
                primaryStage.setTitle("Artikal - " + lvArtikli.getSelectionModel().getSelectedItem());
                primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                primaryStage.showAndWait();
                words.clear();
                lvArtikli.getItems().clear();
                try {
                    ResultSet rs = dao.dajArtikle();
                    while (rs.next()) {
                        Kategorija kategorija = new Kategorija(rs.getString(2));
                        words.add(new Artikal(rs.getString(1), kategorija, rs.getString(3),rs.getString(4),
                                rs.getString(5), rs.getString(6)));

                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
                lvArtikli.getItems().addAll(words);



            } else {
                throw new IncorrectArticleException("Niste odabrali validan artikal!");
            }
        } catch (IncorrectArticleException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Greška!");
            alert.setContentText("Kliknuli ste na nepostojeći artikal!");
            alert.show();
        }

    }

    public void setFilter(ActionEvent actionEvent){
        String choice = filterChoice.getValue();
        if(choice.equals("Naziv (a-z)")){
            lvArtikli.getItems().sort((o1,o2)->{
                if(o1.getNaziv().equals(o2.getNaziv())) return 0;
                if(o1.getNaziv().compareTo(o2.getNaziv()) > 0)
                    return 1;
                else
                    return -1;
            });
        }
        else if(choice.equals("Naziv (z-a)")){
            lvArtikli.getItems().sort((o1,o2)->{
                if(o1.getNaziv().equals(o2.getNaziv())) return 0;
                if(o1.getNaziv().compareTo(o2.getNaziv()) < 0)
                    return 1;
                else
                    return -1;
            });
        }
        else if(choice.equals("Cijena (najjeftinije prvo)")){
            lvArtikli.getItems().sort((o1,o2)->{
                if(Integer.parseInt(o1.getCijena())==Integer.parseInt(o2.getCijena())) return 0;
                if(Integer.parseInt(o1.getCijena())>Integer.parseInt(o2.getCijena()))
                    return 1;
                else
                    return -1;
            });
        }
        else if(choice.equals("Cijena (najskuplje prvo)")){
            lvArtikli.getItems().sort((o1,o2)->{
                if(Integer.parseInt(o1.getCijena())==Integer.parseInt(o2.getCijena())) return 0;
                if(Integer.parseInt(o1.getCijena())<Integer.parseInt(o2.getCijena()))
                    return 1;
                else
                    return -1;
            });
        }
        else if(choice.equals("Lokacija (a-z)")){
            lvArtikli.getItems().sort((o1,o2)->{
                if(o1.getLokacija().equals(o2.getLokacija())) return 0;
                if(o1.getLokacija().compareTo(o2.getLokacija()) > 0)
                    return 1;
                else
                    return -1;
            });
        }
        else if(choice.equals("Lokacija (z-a)")){
            lvArtikli.getItems().sort((o1,o2)->{
                if(o1.getLokacija().equals(o2.getLokacija())) return 0;
                if(o1.getLokacija().compareTo(o2.getLokacija()) < 0)
                    return 1;
                else
                    return -1;
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        filterChoice.setValue("Filter");
        filterChoice.setItems(options);
        filterChoice.setOnAction(this::setFilter);
        if(lvArtikli.getItems()!=null)
        lvArtikli.getItems().clear();
        if(words!=null){
            words.removeAll(words);
        }
        populateArrayList();
        lvArtikli.getItems().addAll(words);
        lvArtikli.refresh();


        Image img = new Image("/img/objava.png");
        Image img1 = new Image("/img/logo-no-bg.png");
        slikaObjava.setImage(img);
        slikaObjava.setFitHeight(80);
        slikaObjava.setPreserveRatio(true);
        //Setting a graphic to the button
        btnObjavaArtikla.setGraphic(slikaObjava);
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
           /* while(rsArtikli.next()){
                Kategorija kategorija = new Kategorija(rsArtikli.getString(2));
                lvArtikli.getItems().add(new Artikal(rsArtikli.getString(1), kategorija, rsArtikli.getString(3),rsArtikli.getString(4),
                        rsArtikli.getString(5), rsArtikli.getString(6)));
            }*/
        } catch (SQLException e){
            e.printStackTrace();
        }



    }




}
