package ba.unsa.etf.rpr;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class AboutControllerTest {
    Stage theStage;
    AboutController ctrl;
    MainController mainController;
    Stage stageGlavna;
    Alert alert;
    RegistrationController registrationController;
    Stage stageRegistracija;

    @Start
    public void start (Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"),bundle);
        ctrl = new AboutController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("About");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();

        theStage = stage;
    }

    @Test
    public void testAboutSlika(FxRobot robot){
        Image slika = new Image(getClass().getResourceAsStream("/img/aboutPicture.jpg"));

        boolean iste = false;
        for (int i = 0; i < slika.getWidth(); i++)
        {
            for (int j = 0; j < slika.getHeight(); j++)
            {
                if (ctrl.imageView.getImage().getPixelReader().getColor(i, j).equals(slika.getPixelReader().getColor(i, j))) iste = true;
            }
        }
        assertTrue(iste);
    }

}