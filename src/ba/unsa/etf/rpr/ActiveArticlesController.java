package ba.unsa.etf.rpr;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ActiveArticlesController implements Initializable {
    private final UserDAO dao;
    private String korisnickoIme;

    public ActiveArticlesController(){ dao = UserDAO.getInstance();}

    public void setKorisnickoIme(User user) {
        korisnickoIme = user.getKorisnickoIme();
    }

    @FXML
    ListView lvAktivni;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet rs = dao.dajArtikleKorisnika(korisnickoIme);

        try {
            while (rs.next()){
                Category category1 = new Category(rs.getString(2));
                lvAktivni.getItems().add(new Article(rs.getString(1), category1, rs.getString(3),rs.getString(4),
                        rs.getString(5), rs.getString(6)));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}