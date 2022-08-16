package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataModel {

    private final StringProperty naziv = new SimpleStringProperty();
    private final StringProperty kategorija = new SimpleStringProperty();
    private final StringProperty cijena = new SimpleStringProperty();
    private final StringProperty lokacija = new SimpleStringProperty();
    private final StringProperty deskripcija = new SimpleStringProperty();
    private final StringProperty korisnik = new SimpleStringProperty();

    public String getKorisnik() {
        return korisnik.get();
    }

    public StringProperty korisnikProperty() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik.set(korisnik);
    }

    public String getKategorija() {
        return kategorija.get();
    }

    public StringProperty kategorijaProperty() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija.set(kategorija);
    }

    public StringProperty nazivProperty() {
        return naziv ;
    }

    public final String getNaziv() {
        return nazivProperty().get();
    }

    public final void setNaziv(String naziv) {
        nazivProperty().set(naziv);
    }

    public String getCijena() {
        return cijena.get();
    }

    public StringProperty cijenaProperty() {
        return cijena;
    }

    public void setCijena(String cijena) {
        this.cijena.set(cijena);
    }

    public String getLokacija() {
        return lokacija.get();
    }

    public StringProperty lokacijaProperty() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija.set(lokacija);
    }

    public String getDeskripcija() {
        return deskripcija.get();
    }

    public StringProperty deskripcijaProperty() {
        return deskripcija;
    }

    public void setDeskripcija(String deskripcija) {
        this.deskripcija.set(deskripcija);
    }
}
