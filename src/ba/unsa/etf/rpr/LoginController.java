package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController{

    public void clickRegistracija(ActionEvent actionEvent) throws IOException {
        Stage secondaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registracija.fxml"));
        RegistracijaController registracijaController = new RegistracijaController();
        loader.setController(registracijaController);
        Parent root = loader.load();
        secondaryStage.setTitle("Registracija");
        secondaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        secondaryStage.show();
    }

}
