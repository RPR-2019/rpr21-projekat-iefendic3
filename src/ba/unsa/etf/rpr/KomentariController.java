package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class KomentariController implements Initializable {
    private KorisnikDAO dao;
    private String korisnickoIme;

    public KomentariController(){ dao = KorisnikDAO.getInstance();}

    public void setKorisnickoIme(Korisnik korisnik) {
        korisnickoIme = korisnik.getKorisnickoIme();
    }

    @FXML
    ListView lvKomentari;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet rs = dao.dajKomentareKorisnika(korisnickoIme);
        Recenzija recenzija;
        try {
            while (rs.next()){
                if(rs.getString(3).equals("Pozitivno")) recenzija = Recenzija.POZITIVNA;
                else
                    recenzija = Recenzija.NEGATIVNA;
                lvKomentari.getItems().add(new Komentar(rs.getString(1),rs.getString(2),recenzija));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void clickDodajKomentar(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/komentar.fxml"));
        KomentarController controller = new KomentarController();
        loader.setController(controller);
        controller.setKorisnickoIme(korisnickoIme);
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Dodaj novi komentar");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
