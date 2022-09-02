package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.jasperreports.engine.JRException;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    ImageView imageView;
    private final KorisnikDAO dao;

    public AboutController(){dao = KorisnikDAO.getInstance();}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image slika = new Image(getClass().getResourceAsStream("/img/aboutPicture.jpg"));
        imageView.setImage(slika);
        imageView.setOpacity(0.3);
    }
    public void stampajKorisnike(ActionEvent actionEvent) {
        try {
            new PrintReport().showReport(dao.getConnection());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }
}
