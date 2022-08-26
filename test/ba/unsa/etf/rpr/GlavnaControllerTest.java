package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class GlavnaControllerTest {
    Stage theStage;
    AboutController aboutController;
    GlavnaController glavnaController;
    Stage stageAbout;
    Alert alert;
    RegistracijaController registracijaController;
    Stage stageRegistracija;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));
        glavnaController = new GlavnaController();
        loader.setController(glavnaController);
        glavnaController.setKorisnickoIme("test1");
        Parent root = loader.load();
        stage.setTitle("IE - Kupoprodaja");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();

        theStage = stage;

    }

    @Test
    public void testKeyboardLogOut(FxRobot robot) {
        robot.press(KeyCode.ALT ,KeyCode.O);
        robot.release(KeyCode.O);
        robot.press(KeyCode.L);
        robot.release(KeyCode.L);
        robot.release(KeyCode.ALT);

        assertTrue(robot.lookup("#fldKorisnickoIme").tryQuery().isPresent());

    }

    @Test
    public void testKeyboardAbout(FxRobot robot) {
        robot.press(KeyCode.ALT ,KeyCode.H);
        robot.release(KeyCode.H);
        robot.press(KeyCode.B);
        robot.release(KeyCode.B);
        robot.release(KeyCode.ALT);
        assertTrue(robot.lookup("#imageView").tryQuery().isPresent());
    }

    @Test
    public void testClickLogOut(FxRobot robot){
        robot.clickOn("#btnOdjava");
        assertTrue(robot.lookup("#fldKorisnickoIme").tryQuery().isPresent());
    }

    @Test
    public void testClickProfil(FxRobot robot){
        robot.clickOn("#btnProfil");
        assertTrue(robot.lookup("#slikaProfila").tryQuery().isPresent());
    }

    @Test
    public void testObjaviArtikal(FxRobot robot){
        robot.clickOn("#btnObjavi");
        robot.lookup("#txtFieldNaslov").tryQuery().isPresent();

        robot.clickOn("#txtFieldNaslov");
        robot.write("BMW");

        robot.clickOn("#choiceKategorije");
        robot.clickOn("Automobili");

        robot.clickOn("#txtFieldCijena");
        robot.write("150000 KM");

        robot.clickOn("#txtFieldLokacija");
        robot.write("Sarajevo");

        robot.clickOn("#txtFieldDeskripcija");
        robot.write("BMW G30");

        robot.clickOn("#btnObjavi");

        ListView<Artikal> lvArtikli = robot.lookup("#lvArtikli").queryAs(ListView.class);
        assertEquals(2, lvArtikli.getItems().size());
    }
}