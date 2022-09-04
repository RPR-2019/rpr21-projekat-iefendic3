package ba.unsa.etf.rpr;

public class Article {
    private String naziv;
    private Category category;
    private String cijena;
    private String lokacija;
    private String deskripcija;
    private String korisnik;

    public Article() {
    }

    public Article(String naziv, Category category, String cijena, String lokacija, String deskripcija, String korisnik) {
        this.naziv = naziv;
        this.category = category;
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

    public Category getKategorija() {
        return category;
    }

    public void setKategorija(Category category) {
        this.category = category;
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
