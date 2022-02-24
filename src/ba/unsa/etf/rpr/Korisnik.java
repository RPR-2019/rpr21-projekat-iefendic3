package ba.unsa.etf.rpr;

public class Korisnik {
    private Osoba osoba;
    private String korisnickoIme;
    private String password;

    public Korisnik(Osoba osoba, String korisnickoIme, String password) {
        this.osoba = osoba;
        this.korisnickoIme = korisnickoIme;
        this.password = password;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
