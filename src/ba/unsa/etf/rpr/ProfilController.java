package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class ProfilController implements Initializable {
    public ImageView slikaProfila;
    public Hyperlink btnSlikaProfila;
    public Label  labelaIme, labelaPrezime, labelaDatum, labelaMjesto, labelaAdresa, labelaBrojTel;
    private String korisnickoIme;
    private Korisnik korisnik;
    private KorisnikDAO dao;
    final FileChooser fc = new FileChooser();

    public ProfilController(){ dao = KorisnikDAO.getInstance();}

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
}
