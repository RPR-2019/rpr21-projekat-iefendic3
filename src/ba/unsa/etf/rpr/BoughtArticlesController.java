package ba.unsa.etf.rpr;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BoughtArticlesController implements Initializable {

    private final UserDAO dao;
    private String korisnickoIme;

    public BoughtArticlesController(){ dao = UserDAO.getInstance();}

    public void setKorisnickoIme(User user) {
        korisnickoIme = user.getKorisnickoIme();
    }

    @FXML
    ListView lvKupljeni;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet rs = dao.dajKupljeneArtikle(korisnickoIme);

        try {
            while (rs.next()){
                Category category1 = new Category(rs.getString(2));
                lvKupljeni.getItems().add(new Article(rs.getString(1), category1, rs.getString(3),rs.getString(4),
                        rs.getString(5), rs.getString(6)));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
