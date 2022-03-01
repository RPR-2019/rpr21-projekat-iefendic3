package ba.unsa.etf.rpr;


import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ObjavaController implements Initializable {
    public TextField txtFieldNaslov,txtFieldKategorija;
    public ChoiceBox<Kategorija> choiceKategorije;
    private KorisnikDAO dao;
    public Button btnDodajKategoriju, btnObjavi, btnOdustani;
    private ArrayList<Kategorija> kategorije = new ArrayList<>();

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
    }

    public void clickObjavi(ActionEvent actionEvent) {
        for(Kategorija k : kategorije){
            dao.dodajKategoriju(new Kategorija(k.getNazivKategorije()));
        }
        Stage stage = (Stage) btnObjavi.getScene().getWindow();
        stage.setUserData(kategorije);
        stage.close();
    }
}
