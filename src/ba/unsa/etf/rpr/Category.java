package ba.unsa.etf.rpr;

public class Category {
    String nazivKategorije;

    public Category() {
        this.nazivKategorije="";
    }

    public Category(String nazivKategorije) {
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
