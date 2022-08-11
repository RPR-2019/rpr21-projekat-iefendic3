package ba.unsa.etf.rpr;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    public Button btnDodajKategoriju, btnObjavi, btnOdustani;
    private ArrayList<Kategorija> kategorije = new ArrayList<>();
    private ArrayList<Artikal> artikli = new ArrayList<>();
    public TextArea txtAreaDeskripcija;

    public ObjavaController() {dao=KorisnikDAO.getInstance();}



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
            Artikal artikal = new Artikal(txtFieldNaslov.getText(),choiceKategorije.getValue(),txtFieldCijena.getText(),txtFieldLokacija.getText(),txtAreaDeskripcija.getText());
            artikli.add(artikal);
            dao.dodajArtikal(artikal);
        }

        Stage stage = (Stage) btnObjavi.getScene().getWindow();

        //stage.setUserData(artikli);
        stage.close();
        try{
           // FXMLLoader loader = FXMLLoader.load(getClass().getClassLoader().getResource("/fxml/glavna.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));
            GlavnaController controller = new GlavnaController();
            controller.setArtikli(artikli);

            loader.setController(controller);

            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("IE - Kupoprodaja");
            stage.show();
        } catch (IOException e){
            System.err.print(String.format("Error: %s", e.getMessage()));
        }

    }
}
