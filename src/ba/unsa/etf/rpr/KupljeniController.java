package ba.unsa.etf.rpr;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class KupljeniController implements Initializable {

    private KorisnikDAO dao;

    public KupljeniController(){ dao = KorisnikDAO.getInstance();}

    @FXML
    ListView lvKupljeni;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet rs = dao.dajKupljeneArtikle();
    }
}
