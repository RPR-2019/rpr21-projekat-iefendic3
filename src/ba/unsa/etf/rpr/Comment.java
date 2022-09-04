package ba.unsa.etf.rpr;

public class Comment {
    private String korisnickoIme;
    private String tekstKomentara;
    private Critique critique;
    private String autor;

    public Comment(String korisnickoIme, String tekstKomentara, Critique critique, String autor) {
        this.korisnickoIme = korisnickoIme;
        this.tekstKomentara = tekstKomentara;
        this.critique = critique;
        this.autor = autor;
    }

    public Comment() {
        this.korisnickoIme="";
        this.tekstKomentara="";
        this.critique = Critique.POZITIVNA;
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

    public Critique getRecenzija() {
        return critique;
    }

    public void setRecenzija(Critique critique) {
        this.critique = critique;
    }

    @Override
    public String toString() {
        return tekstKomentara;
    }
}
