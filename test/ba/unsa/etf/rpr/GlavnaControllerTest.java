package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        glavnaController.setAutorKomentara("test1");
        Parent root = loader.load();
        stage.setTitle("IE - Kupoprodaja");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();

        theStage = stage;

    }

    @BeforeEach
    @AfterEach
    public void resetujBazu() throws SQLException {
        dao.vratiBazuNaDefault();
    }

    @Test
    public void test12KeyboardLogOut(FxRobot robot) {
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
    public void test11ClickLogOut(FxRobot robot){
        robot.clickOn("#btnOdjava");
        assertTrue(robot.lookup("#fldKorisnickoIme").tryQuery().isPresent());
    }

    @Test
    public void test9ClickProfil(FxRobot robot){
        robot.clickOn("#btnProfil");
        assertTrue(robot.lookup("#slikaProfila").tryQuery().isPresent());
    }

    @Test
    public void test8ObjaviArtikal(FxRobot robot) throws Exception{
        robot.clickOn("#btnObjavaArtikla");
        robot.lookup("#txtFieldNaslov").tryQuery().isPresent();

        robot.clickOn("#txtFieldNaslov");
        robot.write("BMW");

        robot.clickOn("#choiceKategorije");
        ChoiceBox<Kategorija> comboBox = robot.lookup("#choiceKategorije").queryAs(ChoiceBox.class);
        robot.lookup(comboBox.getItems().get(2).getNazivKategorije()).tryQuery().isPresent();
        robot.clickOn(comboBox.getItems().get(2).getNazivKategorije());

        robot.clickOn("#txtFieldCijena");
        robot.write("150000 KM");

        robot.clickOn("#txtFieldLokacija");
        robot.write("Sarajevo");

        robot.clickOn("#txtAreaDeskripcija");
        robot.write("BMW G30");

        robot.clickOn("#btnObjavi");

        ListView<Artikal> lvArtikli = robot.lookup("#lvArtikli").queryAs(ListView.class);
        assertEquals(5, lvArtikli.getItems().size());

        ResultSet rsArtikli = dao.dajArtikle();
        ArrayList<Artikal> listaArtikala = new ArrayList<>();
        while(rsArtikli.next()){
            Kategorija kategorija = new Kategorija(rsArtikli.getString(2));
            listaArtikala.add(new Artikal(rsArtikli.getString(1), kategorija, rsArtikli.getString(3),rsArtikli.getString(4),
                    rsArtikli.getString(5), rsArtikli.getString(6)));
        }
        assertEquals(5, listaArtikala.size());
    }

    @Test
    public void test10OtvoriArtikal(FxRobot robot){
        ListView<Artikal> lvArtikli = robot.lookup("#lvArtikli").queryAs(ListView.class);
        robot.clickOn(lvArtikli.getItems().get(0).getNaziv());
        assertTrue(robot.lookup("#kupiBtn").tryQuery().isPresent());



    }

    @Test
    public void test1KupiArtikal(FxRobot robot){
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
    @Test
    public void test77ObrisiArtikal(FxRobot robot){
        ListView<Artikal> lvArtikli = robot.lookup("#lvArtikli").queryAs(ListView.class);
        int velicinaPrije = lvArtikli.getItems().size();
        robot.lookup(lvArtikli.getItems().get(1).getNaziv()).tryQuery().isPresent();
        robot.clickOn(lvArtikli.getItems().get(1).getNaziv());
        robot.lookup("#obrisiBtn").tryQuery().isPresent();
        robot.clickOn("#obrisiBtn");
        ListView<Artikal> lvArtikliPoslije = robot.lookup("#lvArtikli").queryAs(ListView.class);

        assertEquals(lvArtikliPoslije.getItems().size(),3);
    }
    @Test void test5DodajKategoriju(FxRobot robot) throws Exception{

        robot.lookup("#txtFieldKategorija");
        robot.clickOn("#txtFieldKategorija");
        robot.write("Fitness");
        robot.clickOn("#btnDodajKategoriju");


        ResultSet rsKategorije = dao.dajKategorije();
        ArrayList<Kategorija> listaKategorija = new ArrayList<>();
        while(rsKategorije.next()){

            listaKategorija.add(new Kategorija(rsKategorije.getString(1)));
        }
        assertEquals(8, listaKategorija.size());
    }

    @Test
    public void test6Search(FxRobot robot){
        robot.clickOn("#searchBar");
        robot.write("Stan");
        ListView<Artikal> lvArtikli = robot.lookup("#lvArtikli").queryAs(ListView.class);
        assertEquals(lvArtikli.getItems().size(),1);
    }

    @Test
    public void test7Filter(FxRobot robot){
        ListView<Artikal> lvArtikli = robot.lookup("#lvArtikli").queryAs(ListView.class);
        robot.clickOn("#filterChoice");
        robot.clickOn("Naziv (a-z)");

        assertEquals(lvArtikli.getItems().get(0).getNaziv(),"Farmerke");

        robot.clickOn("#filterChoice");
        robot.clickOn("Naziv (z-a)");
        assertEquals(lvArtikli.getItems().get(0).getNaziv(),"Stan");

        robot.clickOn("#filterChoice");
        robot.clickOn("Cijena (najjeftinije prvo)");
        assertEquals(lvArtikli.getItems().get(0).getNaziv(),"Farmerke");

        robot.clickOn("#filterChoice");
        robot.clickOn("Cijena (najskuplje prvo)");
        assertEquals(lvArtikli.getItems().get(0).getNaziv(),"Stan");

        robot.clickOn("#filterChoice");
        robot.clickOn("Lokacija (a-z)");
        assertEquals(lvArtikli.getItems().get(0).getNaziv(),"Mercedes S klasa");

        robot.clickOn("#filterChoice");
        robot.clickOn("Lokacija (z-a)");
        assertEquals(lvArtikli.getItems().get(0).getNaziv(),"Farmerke");
    }


    @Test
    public void test2DodajKomentar(FxRobot robot){
        ListView<Artikal> lvArtikli = robot.lookup("#lvArtikli").queryAs(ListView.class);
        robot.clickOn(lvArtikli.getItems().get(0).getNaziv());
        robot.lookup("#profilBtn");
        robot.clickOn("#profilBtn");
        robot.lookup("#komentariBtn");
        robot.clickOn("#komentariBtn");
        robot.lookup("#dodajKomentarBtn");
        robot.clickOn("#dodajKomentarBtn");
        robot.lookup("#txtArea");
        robot.clickOn("#txtArea");
        robot.write("Sve pohvale!");
        robot.clickOn("#radioPozitivna");
        robot.clickOn("#btnObjavi");

        ListView<Komentar> lvKomentari = robot.lookup("#lvKomentari").queryAs(ListView.class);
        assertEquals(1,lvKomentari.getItems().size());

        robot.clickOn(lvKomentari.getItems().get(0).getTekstKomentara());
        String tekstKomentara = robot.lookup("#tekstKomentara").queryAs(TextArea.class).getText();
        String recenzija = robot.lookup("#recenzija").queryAs(Label.class).getText();
        String autorKomentara = robot.lookup("#autorKomentara").queryAs(Label.class).getText();

        assertEquals("Sve pohvale!",tekstKomentara);
        assertEquals("Pozitivno",recenzija);
        assertEquals("test1",autorKomentara);
    }

    @Test
    public void testAktivniArtikli(FxRobot robot){

        robot.lookup("#btnProfil");
        robot.clickOn("#btnProfil");
        robot.lookup("#aktivniBtn");
        robot.clickOn("#aktivniBtn");


        ListView<Komentar> lvAktivni = robot.lookup("#lvAktivni").queryAs(ListView.class);
        assertEquals(1,lvAktivni.getItems().size());
    }
}