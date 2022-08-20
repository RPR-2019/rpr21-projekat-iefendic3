package ba.unsa.etf.rpr;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AktivniController implements Initializable {
    private KorisnikDAO dao;
    private String korisnickoIme;

    public AktivniController(){ dao = KorisnikDAO.getInstance();}

    public void setKorisnickoIme(Korisnik korisnik) {
        korisnickoIme = korisnik.getKorisnickoIme();
    }

    @FXML
    ListView lvAktivni;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet rs = dao.dajArtikle();

        try {
            while (rs.next()){
                Kategorija kategorija1 = new Kategorija(rs.getString(2));
                lvAktivni.getItems().add(new Artikal(rs.getString(1), kategorija1, rs.getString(3),rs.getString(4),
                        rs.getString(5), rs.getString(6)));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
