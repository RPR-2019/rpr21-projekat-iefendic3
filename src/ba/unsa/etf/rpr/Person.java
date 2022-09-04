package ba.unsa.etf.rpr;

import java.time.LocalDate;

public class Person {
    private String ime;
    private String prezime;
    private String datumRodjenja;

    public Person(String ime, String prezime, String datumRodjenja) {
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
    }

    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }


    public String dajDatum() {
        LocalDate datum = LocalDate.parse(datumRodjenja);

        return datum.getDayOfMonth() + "." + datum.getMonthValue()+"."+datum.getYear()+".";
    }
}
