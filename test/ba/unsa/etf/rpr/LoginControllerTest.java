package ba.unsa.etf.rpr;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {

    Stage theStage;
    LoginController ctrl;
    MainController mainController;
    Stage stageGlavna;
    Alert alert;
    RegistrationController registrationController;
    Stage stageRegistracija;

    @Start
    public void start (Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"),bundle);
        ctrl = new LoginController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();

        theStage = stage;



        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Greška");

    }

   @Test
    public void testLogin1Valid(FxRobot robot){
        robot.clickOn("#fldKorisnickoIme");
        robot.write("test1");
        robot.clickOn("#fldPassword");
        robot.write("test1");
        robot.clickOn("#btnPrijava");
        assertFalse(theStage.isShowing());
        assertTrue(robot.lookup("#lvArtikli").tryQuery().isPresent());

    }

    @Test
    public void testLogin2Invalid(FxRobot robot){

        robot.clickOn("#fldKorisnickoIme");
        robot.write(" ");
        robot.clickOn("#fldPassword");
        robot.write(" ");
        robot.clickOn("#btnPrijava");
        assertTrue(alert.getHeaderText().equals("Greška"));
        robot.clickOn("OK");
    }

    @Test
    public void testLogin0Registracija(FxRobot robot){

        robot.clickOn("#btnRegistracija");


        assertTrue(robot.lookup("#btnRegistrujSe").tryQuery().isPresent());

    }


}