package ba.unsa.etf.rpr;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ObjavaController implements Initializable {
    public TextField txtFieldNaslov,txtFieldKategorija,txtFieldCijena, txtFieldLokacija;
    public ChoiceBox<Kategorija> choiceKategorije;
    private KorisnikDAO dao;
    private String korisnickoIme;
    public Button btnDodajKategoriju, btnObjavi, btnOdustani;
    private ArrayList<Kategorija> kategorije = new ArrayList<>();
    private ArrayList<Artikal> artikli = new ArrayList<>();
    public TextArea txtAreaDeskripcija;
    final FileChooser fc = new FileChooser();
    @FXML
    ImageView slikaArtikla;
    ProfilController profilController = new ProfilController();

    public ObjavaController() {dao=KorisnikDAO.getInstance();}


    public void setKorisnickoIme(Korisnik korisnik) {
        korisnickoIme = korisnik.getKorisnickoIme();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnObjavi.getStyleClass().add("zelenoDugme");
        btnOdustani.getStyleClass().add("crvenoDugme");
        txtFieldKategorija.setVisible(false);
        btnDodajKategoriju.setVisible(false);

        Kategorija dodaj = new Kategorija("Dodaj novu kategoriju");

        choiceKategorije.getItems().add(dodaj);
        try {
            ResultSet rs = dao.dajKategorije();
            while (rs.next()) {
                choiceKategorije.getItems().add(new Kategorija(rs.getString(1)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        if(choiceKategorije.getSelectionModel().getSelectedItem()!=null && choiceKategorije.getValue().toString().equals(dodaj.toString())){
            txtFieldKategorija.setVisible(true);
            btnDodajKategoriju.setVisible(true);
        }
    }

    public void promijeni(MouseEvent mouseEvent) {
        if(choiceKategorije.getSelectionModel().getSelectedItem()!=null && choiceKategorije.getValue().toString().equals("Dodaj novu kategoriju")){
            txtFieldKategorija.setVisible(true);
            btnDodajKategoriju.setVisible(true);
        }
        else{
            txtFieldKategorija.setVisible(false);
            btnDodajKategoriju.setVisible(false);
        }
    }

    public void clickDodajKategoriju(ActionEvent actionEvent) {
        Kategorija k = new Kategorija(txtFieldKategorija.getText());
        if(!txtFieldKategorija.getText().isBlank() && !choiceKategorije.getItems().contains(k)){
            choiceKategorije.getItems().add(k);
            //dao.dodajKategoriju(new Kategorija(txtFieldKategorija.getText()));
            txtFieldKategorija.setText("");
            kategorije.add(k);
        }
        for(Kategorija ka : kategorije){
            dao.dodajKategoriju(new Kategorija(ka.getNazivKategorije()));
        }
        Stage stage = (Stage) btnObjavi.getScene().getWindow();
        stage.setUserData(kategorije);
    }

    public void clickObjavi(ActionEvent actionEvent) {
        if(!txtFieldNaslov.getText().isBlank() && choiceKategorije.getValue() != null && !txtFieldCijena.getText().isBlank() && !txtFieldLokacija.getText().isBlank()
        && !txtAreaDeskripcija.getText().isBlank()){
            Artikal artikal = new Artikal(txtFieldNaslov.getText(),choiceKategorije.getValue(),txtFieldCijena.getText(),txtFieldLokacija.getText(),txtAreaDeskripcija.getText(),korisnickoIme);
            artikli.add(artikal);
            dao.dodajArtikal(artikal);
        }

        Stage stage = (Stage) btnObjavi.getScene().getWindow();


        stage.close();


    }

    public void clickOdustani(ActionEvent actionEvent){
        Stage stage = (Stage) btnObjavi.getScene().getWindow();
        stage.close();

    }

    public void clickBtn (ActionEvent actionEvent) {
        fc.setTitle("My File Chooser");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().clear();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.*"));

        File file = fc.showOpenDialog(null);
        Artikal artikal = new Artikal(txtFieldNaslov.getText(),choiceKategorije.getValue(),txtFieldCijena.getText(),txtFieldLokacija.getText(),
                txtAreaDeskripcija.getText(),korisnickoIme);

        if(file != null){
            try {
                Image img = new Image(file.toURI().toString());
                slikaArtikla.setImage(img);
                FileInputStream fileInputStream = new FileInputStream(file);
                dao.dodajSlikuArtikla(artikal, fileInputStream, fileInputStream.available());
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            System.out.println("A file is invalid!");
        }
    }
}
