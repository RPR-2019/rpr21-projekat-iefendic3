package ba.unsa.etf.rpr;

public enum Recenzija {
    POZITIVNA("Pozitivno"), NEGATIVNA("Negativno");

    private String recenzija;

    private Recenzija(String recenzija){
        this.recenzija = recenzija;
    }

    @Override
    public String toString() {
        return recenzija;
    }
}
