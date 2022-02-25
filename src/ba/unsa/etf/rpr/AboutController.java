package ba.unsa.etf.rpr;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image slika = new Image(getClass().getResourceAsStream("/img/aboutPicture.jpg"));
        imageView.setImage(slika);
        imageView.setOpacity(0.3);
    }
}
