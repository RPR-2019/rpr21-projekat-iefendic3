package ba.unsa.etf.rpr;

public class Komentar {
    private String korisnickoIme;
    private String tekstKomentara;
    private Recenzija recenzija;
    private String autor;

    public Komentar(String korisnickoIme, String tekstKomentara, Recenzija recenzija, String autor) {
        this.korisnickoIme = korisnickoIme;
        this.tekstKomentara = tekstKomentara;
        this.recenzija = recenzija;
        this.autor = autor;
    }

    public Komentar() {
        this.korisnickoIme="";
        this.tekstKomentara="";
        this.recenzija=Recenzija.POZITIVNA;
        this.autor="";
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getTekstKomentara() {
        return tekstKomentara;
    }

    public void setTekstKomentara(String tekstKomentara) {
        this.tekstKomentara = tekstKomentara;
    }

    public Recenzija getRecenzija() {
        return recenzija;
    }

    public void setRecenzija(Recenzija recenzija) {
        this.recenzija = recenzija;
    }

    @Override
    public String toString() {
        return tekstKomentara;
    }
}
