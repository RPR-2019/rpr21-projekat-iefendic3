package ba.unsa.etf.rpr;

import java.util.ArrayList;

public class Artikal {
    private String naziv;
    private Kategorija kategorija;
    private String cijena;
    private String lokacija;
    private String deskripcija;
    private String korisnik;

    public Artikal() {
    }

    public Artikal(String naziv, Kategorija kategorija, String cijena, String lokacija, String deskripcija, String korisnik) {
        this.naziv = naziv;
        this.kategorija = kategorija;
        this.cijena = cijena;
        this.lokacija = lokacija;
        this.deskripcija = deskripcija;
        this.korisnik = korisnik;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public String getCijena() {
        return cijena;
    }

    public void setCijena(String cijena) {
        this.cijena = cijena;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getDeskripcija() {
        return deskripcija;
    }

    public void setDeskripcija(String deskripcija) {
        this.deskripcija = deskripcija;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
