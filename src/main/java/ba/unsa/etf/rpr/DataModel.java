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

    private final StringProperty korisnickoIme = new SimpleStringProperty();
    private final StringProperty tekstKomentara = new SimpleStringProperty();
    private final StringProperty recenzija = new SimpleStringProperty();
    private final StringProperty autor = new SimpleStringProperty();

    public String getKorisnickoIme() {
        return korisnickoIme.get();
    }

    public StringProperty korisnickoImeProperty() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme.set(korisnickoIme);
    }

    public String getTekstKomentara() {
        return tekstKomentara.get();
    }

    public StringProperty tekstKomentaraProperty() {
        return tekstKomentara;
    }

    public void setTekstKomentara(String tekstKomentara) {
        this.tekstKomentara.set(tekstKomentara);
    }

    public String getRecenzija() {
        return recenzija.get();
    }

    public StringProperty recenzijaProperty() {
        return recenzija;
    }

    public void setRecenzija(String recenzija) {
        this.recenzija.set(recenzija);
    }

    public String getAutor() {
        return autor.get();
    }

    public StringProperty autorProperty() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor.set(autor);
    }

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
