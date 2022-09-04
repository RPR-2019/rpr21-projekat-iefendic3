package ba.unsa.etf.rpr;

public enum Critique {
    POZITIVNA("Pozitivno"), NEGATIVNA("Negativno");

    private final String recenzija;

    Critique(String recenzija){
        this.recenzija = recenzija;
    }

    @Override
    public String toString() {
        return recenzija;
    }
}
