package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CommentController implements Initializable {
    private final UserDAO dao;
    private String korisnickoIme;
    private String autor;
    private final Comment comment = new Comment("","", Critique.POZITIVNA,"");

    @FXML
    TextArea txtArea;
    @FXML
    RadioButton radioPozitivna;
    @FXML
    RadioButton radioNegativna;

    public CommentController(){ dao = UserDAO.getInstance();}

    public void setKorisnickoIme(String korisnik) {
        korisnickoIme = korisnik;
    }
    public void setAutorKomentara(String autor1){
        autor=autor1;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void clickObjaviKomentar(ActionEvent actionEvent){
        comment.setKorisnickoIme(korisnickoIme);
        if(txtArea.getText()!=null && (radioPozitivna.isSelected() || radioNegativna.isSelected())){

            comment.setTekstKomentara(txtArea.getText());
            if(radioPozitivna.isSelected()){
                comment.setRecenzija(Critique.POZITIVNA);
            }
            else if(radioNegativna.isSelected()){
                comment.setRecenzija(Critique.NEGATIVNA);
            }
            comment.setAutor(autor);
            dao.dodajKomentar(comment);
            Stage stage = (Stage) radioPozitivna.getScene().getWindow();
            stage.close();
        }



    }
}
