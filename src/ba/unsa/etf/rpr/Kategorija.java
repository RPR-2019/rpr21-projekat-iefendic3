package ba.unsa.etf.rpr;

public class Kategorija {
    String nazivKategorije;

    public Kategorija() {
        this.nazivKategorije="";
    }

    public Kategorija(String nazivKategorije) {
        this.nazivKategorije = nazivKategorije;
    }

    public String getNazivKategorije() {
        return nazivKategorije;
    }

    public void setNazivKategorije(String nazivKategorije) {
        this.nazivKategorije = nazivKategorije;
    }

    @Override
    public String toString() {
        return nazivKategorije;
    }
}
