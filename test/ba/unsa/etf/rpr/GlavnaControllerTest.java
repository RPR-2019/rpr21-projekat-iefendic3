package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class GlavnaControllerTest {
    Stage theStage;
    GlavnaController glavnaController;
    KorisnikDAO dao = KorisnikDAO.getInstance();


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

    @BeforeEach
    public void resetujBazu() throws SQLException {
        dao.vratiBazuNaDefault();
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
    public void testObjaviArtikal(FxRobot robot) throws Exception{
        robot.clickOn("#btnObjavaArtikla");
        robot.lookup("#txtFieldNaslov").tryQuery().isPresent();

        robot.clickOn("#txtFieldNaslov");
        robot.write("BMW");

        robot.clickOn("#choiceKategorije");
        ChoiceBox<Kategorija> comboBox = robot.lookup("#choiceKategorije").queryAs(ChoiceBox.class);
        robot.clickOn(comboBox.getItems().get(2).getNazivKategorije());

        robot.clickOn("#txtFieldCijena");
        robot.write("150000 KM");

        robot.clickOn("#txtFieldLokacija");
        robot.write("Sarajevo");

        robot.clickOn("#txtAreaDeskripcija");
        robot.write("BMW G30");

        robot.clickOn("#btnObjavi");

        ListView<Artikal> lvArtikli = robot.lookup("#lvArtikli").queryAs(ListView.class);
        assertEquals(3, lvArtikli.getItems().size());

        ResultSet rsArtikli = dao.dajArtikle();
        ArrayList<Artikal> listaArtikala = new ArrayList<>();
        while(rsArtikli.next()){
            Kategorija kategorija = new Kategorija(rsArtikli.getString(2));
            listaArtikala.add(new Artikal(rsArtikli.getString(1), kategorija, rsArtikli.getString(3),rsArtikli.getString(4),
                    rsArtikli.getString(5), rsArtikli.getString(6)));
        }
        assertEquals(3, listaArtikala.size());
    }

    @Test
    public void otvoriArtikal(FxRobot robot){
        ListView<Artikal> lvArtikli = robot.lookup("#lvArtikli").queryAs(ListView.class);
        robot.clickOn(lvArtikli.getItems().get(0).getNaziv());
        assertTrue(robot.lookup("#kupiBtn").tryQuery().isPresent());
        assertTrue(robot.lookup("#cijena").queryAs(Label.class).getText().equals("350000 KM"));


    }

    @Test
    public void kupiArtikal(FxRobot robot){
        ListView<Artikal> lvArtikli = robot.lookup("#lvArtikli").queryAs(ListView.class);
        robot.clickOn(lvArtikli.getItems().get(0).getNaziv());
        robot.lookup("#kupiBtn").tryQuery().isPresent();
        robot.clickOn("#kupiBtn");
        robot.lookup("OK");
        robot.clickOn("OK");
        robot.clickOn("#btnProfil");
        robot.lookup("#kupljeniBtn").tryQuery().isPresent();
        robot.clickOn("#kupljeniBtn");
        ListView<Artikal> kupljeni = robot.lookup("#lvKupljeni").queryAs(ListView.class);
        assertEquals(1,kupljeni.getItems().size());
    }
}