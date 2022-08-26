package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class RegistracijaControllerTest {
    Stage stageLogin;
    LoginController loginController;
    GlavnaController glavnaController;
    Stage stageGlavna;
    Alert alert1, alert;
    RegistracijaController registracijaController;
    Stage stageRegistracija;
    ButtonType accept, cancel,ok;
    @Start
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registracija.fxml"));
        registracijaController = new RegistracijaController();
        loader.setController(registracijaController);
        Parent root = loader.load();
        stage.setTitle("Registracija");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();

        stageRegistracija = stage;
         accept = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
         cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert1 = new Alert(Alert.AlertType.CONFIRMATION, "", accept, cancel);
        alert1.setTitle("Uvjeti rada aplikacije");
        alert1.setHeaderText("Korisnik ove aplikacije je dužan da poštuje sve moralna i zakonska pravila kupoprodaje.\n" +
                "Kršenjem nekog od pravila, korisnik snosi sve posljedice.\n" +
                "Administrator aplikacije ne odgovara za kršenje pravila.");
        alert1.setContentText("Da li pristajete na uvjete rada aplikacije?");

         alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška!");
        alert.setHeaderText("Password je prekratak!");
        alert.setContentText("Pokušajte ponovo!");



    }

    @Test
    public void testRegistracija1Valid(FxRobot robot) throws Exception{
        robot.clickOn("#fldIme");
        robot.write("Ibrahim");
        robot.clickOn("#fldPrezime");
        robot.write("Efendic");
        robot.clickOn("#datePicker");
        robot.write("26.10.2001.");
        robot.clickOn("#fldKorisnicko");
        robot.write("iefendic3");
        robot.clickOn("#fldMjesto");
        robot.write("Sarajevo");
        robot.clickOn("#fldAdresa");
        robot.write("Bjelave");
        robot.clickOn("#fldBrojTel");
        robot.write("061377563");
        robot.clickOn("#fldPassword");
        robot.write("sifra1");

        robot.clickOn("#btnRegistrujSe");

        robot.clickOn(accept.getText());
        robot.clickOn(ok.getText());

        assertFalse(stageRegistracija.isShowing());
    }

    @Test
    public void testRegistracija2Invalid(FxRobot robot){
        robot.clickOn("#btnRegistrujSe");
        robot.clickOn("OK");
        assertTrue(stageRegistracija.isShowing());
    }

    @Test
    public void testRegistracija3Invalid(FxRobot robot) throws Exception{
        robot.clickOn("#fldIme");
        robot.write("Ibrahim");
        robot.clickOn("#fldPrezime");
        robot.write("Efendic");
        robot.clickOn("#datePicker");
        robot.write("26.10.2001.");
        robot.clickOn("#fldKorisnicko");
        robot.write("iefendic3");
        robot.clickOn("#fldMjesto");
        robot.write("Sarajevo");
        robot.clickOn("#fldAdresa");
        robot.write("Bjelave");
        robot.clickOn("#fldBrojTel");
        robot.write("061377563");
        robot.clickOn("#fldPassword");
        robot.write("1");

        robot.clickOn("#btnRegistrujSe");

        assertTrue(alert.getAlertType().equals(Alert.AlertType.ERROR));
        robot.clickOn("OK");
        assertTrue(stageRegistracija.isShowing());


    }
}