package ba.unsa.etf.rpr;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class GlavnaController {
    public MenuItem menuLogOut;
    public MenuBar menuBar;

    public void clickMenuLogOut(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        loader.setController(new LoginController());
        Parent root = loader.load();
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();

    }

    public void clickMenuExit(ActionEvent actionEvent){
        System.exit(0);
    }
}
