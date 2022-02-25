package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class KorisnikDAO {
    private static KorisnikDAO instance = null;
    private Connection connection;
    private PreparedStatement dajKorisnikeUpit,dodajKorisnikaUpit, nadjiKorisnikaUpit;

    private KorisnikDAO() {
        String url = "jdbc:sqlite:baza.db";
        try {
            connection = DriverManager.getConnection(url);
            dajKorisnikeUpit = connection.prepareStatement("SELECT * FROM korisnik");
        } catch (SQLException e) {
            generisiBazu();

        }
        try {
           dajKorisnikeUpit = connection.prepareStatement("SELECT * FROM korisnik");
           dodajKorisnikaUpit = connection.prepareStatement("INSERT INTO korisnik VALUES (?,?,?,?,?)");
           nadjiKorisnikaUpit = connection.prepareStatement("SELECT * FROM korisnik WHERE korisnicko_ime=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static KorisnikDAO getInstance() {
        if (instance == null) instance = new KorisnikDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance != null) {
            try {
                instance.connection.close();
                instance = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void dodajKorisnika(Korisnik korisnik){
        try{
            dodajKorisnikaUpit.setString(1,korisnik.getOsoba().getIme());
            dodajKorisnikaUpit.setString(2,korisnik.getOsoba().getPrezime());
            dodajKorisnikaUpit.setString(3,korisnik.getOsoba().getDatumRodjenja());
            dodajKorisnikaUpit.setString(4,korisnik.getKorisnickoIme());
            dodajKorisnikaUpit.setString(5,korisnik.getPassword());
            dodajKorisnikaUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Korisnik nadjiKorisnika(String korisnickoIme){
        try{
            nadjiKorisnikaUpit.setString(1,korisnickoIme);
            ResultSet rs = nadjiKorisnikaUpit.executeQuery();
            if(rs.next()){
                Korisnik k = new Korisnik();
                Osoba o = new Osoba(rs.getString(1),rs.getString(2),rs.getString(3)); //Ime prezime datum
                k.setOsoba(o);
                k.setPassword(rs.getString(5));
                k.setKorisnickoIme(korisnickoIme);
                return k;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void generisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka... nastavljam sa praznom bazom");
        }
    }
}
