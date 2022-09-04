package ba.unsa.etf.rpr;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class PublishController implements Initializable {
    public TextField txtFieldNaslov,txtFieldKategorija,txtFieldCijena, txtFieldLokacija;
    public ChoiceBox<Category> choiceKategorije;
    private final UserDAO dao;
    private String korisnickoIme;
    public Button btnDodajKategoriju, btnObjavi, btnOdustani;
    private final ArrayList<Category> kategorije = new ArrayList<>();
    private final ArrayList<Article> artikli = new ArrayList<>();
    public TextArea txtAreaDeskripcija;
    final FileChooser fc = new FileChooser();
    @FXML
    ImageView slikaArtikla;
    ProfileController profileController = new ProfileController();

    public PublishController() {dao= UserDAO.getInstance();}


    public void setKorisnickoIme(User user) {
        korisnickoIme = user.getKorisnickoIme();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnObjavi.getStyleClass().add("zelenoDugme");
        btnOdustani.getStyleClass().add("crvenoDugme");
        txtFieldKategorija.setVisible(false);
        btnDodajKategoriju.setVisible(false);
        Category odaberi = new Category("Odaberi kategoriju");
        Category dodaj = new Category("Dodaj novu kategoriju");

        choiceKategorije.getItems().add(dodaj);
        choiceKategorije.getItems().add(odaberi);
        choiceKategorije.setValue(odaberi);
        try {
            ResultSet rs = dao.dajKategorije();
            while (rs.next()) {
                choiceKategorije.getItems().add(new Category(rs.getString(1)));
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
        Category k = new Category(txtFieldKategorija.getText());
        if(!txtFieldKategorija.getText().isBlank() && !choiceKategorije.getItems().contains(k)){
            choiceKategorije.getItems().add(k);
            //dao.dodajKategoriju(new Kategorija(txtFieldKategorija.getText()));
            txtFieldKategorija.setText("");
            kategorije.add(k);
        }
        for(Category ka : kategorije){
            dao.dodajKategoriju(new Category(ka.getNazivKategorije()));
        }
        Stage stage = (Stage) btnObjavi.getScene().getWindow();
        stage.setUserData(kategorije);
    }

    public void clickObjavi(ActionEvent actionEvent) {
        if(!txtFieldNaslov.getText().isBlank() && choiceKategorije.getValue() != null  && !txtFieldCijena.getText().isBlank() && !txtFieldLokacija.getText().isBlank()
        && !txtAreaDeskripcija.getText().isBlank()){
            Category category = new Category();
            if(choiceKategorije.getValue().toString().equals("Odaberi kategoriju")) category =new Category("/");
            else
                category = choiceKategorije.getValue();
            Article artikal = new Article(txtFieldNaslov.getText(), category,txtFieldCijena.getText(),txtFieldLokacija.getText(),txtAreaDeskripcija.getText(),korisnickoIme);
            artikli.add(artikal);
            dao.dodajArtikal(artikal);

            Stage stage = (Stage) btnObjavi.getScene().getWindow();


            stage.close();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Greška pri objavljivanju");
            alert.setContentText("Morate popuniti sva polja!");
            alert.show();
        }




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
        Article artikal = new Article(txtFieldNaslov.getText(),choiceKategorije.getValue(),txtFieldCijena.getText(),txtFieldLokacija.getText(),
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
