package ba.unsa.etf.rpr;

public class Korisnik {
    private Osoba osoba;
    private String korisnickoIme;
    private String password, mjesto, adresa, brojTelefona;

    public Korisnik(Osoba osoba, String korisnickoIme, String password, String mjesto, String adresa, String brojTelefona) {
        this.osoba = osoba;
        this.korisnickoIme = korisnickoIme;
        this.password = password;
        this.mjesto = mjesto;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
    }

    public Korisnik() {
        this.osoba = new Osoba("","","");
        this.korisnickoIme="";
        this.password="";
        this.mjesto = "";
        this.adresa = "";
        this.brojTelefona = "";
    }

    public String getMjesto() {
        return mjesto;
    }

    public void setMjesto(String mjesto) {
        this.mjesto = mjesto;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
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
