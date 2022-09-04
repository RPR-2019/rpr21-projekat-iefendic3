package ba.unsa.etf.rpr;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;


public class CommentDetailsController implements Initializable {
    private final UserDAO dao = UserDAO.getInstance();
    private String korisnickoIme;
    private String autor;
    private final DataModel model ;
    @FXML
    TextArea tekstKomentara;
    @FXML
    Label recenzija;
    @FXML
    Label autorKomentara;

    public CommentDetailsController(DataModel model){
        this.model = model;
    }
    public void setKorisnickoIme(User user) {
        korisnickoIme = user.getKorisnickoIme();
    }
    public void setAutor(String korisnik) {
        autor = korisnik;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tekstKomentara.textProperty().bind(model.tekstKomentaraProperty());
        recenzija.textProperty().bind(model.recenzijaProperty());
        autorKomentara.textProperty().bind(model.autorProperty());

    }
}
