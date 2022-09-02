package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class KomentariController implements Initializable {
    private final KorisnikDAO dao;
    private String korisnickoIme;
    private String autor;

    public KomentariController(){ dao = KorisnikDAO.getInstance();}

    public void setKorisnickoIme(Korisnik korisnik) {
        korisnickoIme = korisnik.getKorisnickoIme();
    }
    public void setAutor(String korisnik) {
        autor = korisnik;
    }

    @FXML
    ListView<Komentar> lvKomentari;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet rs = dao.dajKomentareKorisnika(korisnickoIme);
        Recenzija recenzija;
        try {
            while (rs.next()){
                if(rs.getString(3).equals("Pozitivno")) recenzija = Recenzija.POZITIVNA;
                else
                    recenzija = Recenzija.NEGATIVNA;
                lvKomentari.getItems().add(new Komentar(rs.getString(1),rs.getString(2),recenzija,rs.getString(4)));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void clickDodajKomentar(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/komentar.fxml"),bundle);
        KomentarController controller = new KomentarController();
        loader.setController(controller);
        controller.setKorisnickoIme(korisnickoIme);
        controller.setAutorKomentara(autor);

        Parent root = loader.load();
        primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
        primaryStage.setTitle("Dodaj novi komentar");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.showAndWait();

        try {
            ResultSet rsKomentari = dao.dajKomentareKorisnika(korisnickoIme);
            Komentar zadnjiKomentar = new Komentar();
            Recenzija recenzija;
            //Zadnji element
            while(rsKomentari.next()){
                if(rsKomentari.getString(3).equals("Pozitivno")) recenzija = Recenzija.POZITIVNA;
                else
                    recenzija = Recenzija.NEGATIVNA;
                zadnjiKomentar.setKorisnickoIme(rsKomentari.getString(1));
                zadnjiKomentar.setTekstKomentara(rsKomentari.getString(2));
                zadnjiKomentar.setRecenzija(recenzija);
                zadnjiKomentar.setAutor(rsKomentari.getString(4));
            }

            boolean tmp = false;
            for(int i = 0; i<lvKomentari.getItems().size();i++){
                if(lvKomentari.getItems().get(i).getKorisnickoIme().equals(zadnjiKomentar.getKorisnickoIme())
                        && lvKomentari.getItems().get(i).getTekstKomentara().equals(zadnjiKomentar.getTekstKomentara())
                        && lvKomentari.getItems().get(i).getRecenzija().toString().equals(zadnjiKomentar.getRecenzija().toString())
                        && lvKomentari.getItems().get(i).getAutor().equals(zadnjiKomentar.getAutor())
                ) {
                    tmp=true;
                }
            }
            if(!tmp) {

                lvKomentari.getItems().add(zadnjiKomentar);
            }


        } catch (SQLException e){
            e.printStackTrace();
        }

        primaryStage.setWidth(USE_COMPUTED_SIZE-0.0001);
    }

    @FXML public void handleMouseClick(MouseEvent arg0) throws IOException {
        try {
            if (lvKomentari.getSelectionModel().getSelectedItem() != null) {
                DataModel model1 = new DataModel();
                Stage primaryStage = new Stage();
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/komentar-detalji.fxml"),bundle);
                KomentarDetaljiController controller = new KomentarDetaljiController(model1);
                loader.setController(controller);

                Korisnik korisnik = dao.nadjiKorisnika(korisnickoIme);
                controller.setKorisnickoIme(korisnik);
                controller.setAutor(autor);

                model1.setTekstKomentara(lvKomentari.getSelectionModel().getSelectedItem().toString());
                model1.setKorisnickoIme(lvKomentari.getSelectionModel().getSelectedItem().getKorisnickoIme());
                model1.setRecenzija(lvKomentari.getSelectionModel().getSelectedItem().getRecenzija().toString());
                model1.setAutor(lvKomentari.getSelectionModel().getSelectedItem().getAutor());

                Stage stage = (Stage) lvKomentari.getScene().getWindow();
                stage.setWidth(USE_COMPUTED_SIZE - 0.0001);

                Parent root = loader.load();
                primaryStage.getIcons().add(new Image("/img/logo-no-bg.png"));
                primaryStage.setTitle("Komentar - " + lvKomentari.getSelectionModel().getSelectedItem());
                primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                primaryStage.show();
            } else {
                throw new IncorrectArticleException("Niste odabrali validan komentar!");
            }
        } catch (IncorrectArticleException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Greška!");
            alert.setContentText("Kliknuli ste na nepostojeći komentar!");
            alert.show();
        }


    }
}
