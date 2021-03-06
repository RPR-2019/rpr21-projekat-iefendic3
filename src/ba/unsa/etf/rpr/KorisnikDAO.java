package ba.unsa.etf.rpr;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KorisnikDAO {
    private static KorisnikDAO instance = null;
    private Connection connection;
    private PreparedStatement dajKorisnikeUpit,dodajKorisnikaUpit, nadjiKorisnikaUpit, nadjiPasswordKorisnikaUpit, dodajSlikuKorisnikaUpit,
    dajSlikuKorisnikaUpit, obrisiSlikuKorisnikaUpit, dodajKategorijuUpit, dajKategorijeUpit;

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
           dodajKorisnikaUpit = connection.prepareStatement("INSERT INTO korisnik VALUES (?,?,?,?,?,?,?,?)");
           nadjiKorisnikaUpit = connection.prepareStatement("SELECT * FROM korisnik WHERE korisnicko_ime=?");
           nadjiPasswordKorisnikaUpit = connection.prepareStatement("SELECT * FROM korisnik WHERE password=?");
           dodajSlikuKorisnikaUpit=connection.prepareStatement("INSERT INTO slikaKorisnika VALUES (?,?)");
           dajSlikuKorisnikaUpit=connection.prepareStatement("SELECT slika FROM slikaKorisnika WHERE korisnicko_ime=?");
           obrisiSlikuKorisnikaUpit = connection.prepareStatement("DELETE FROM slikaKorisnika WHERE korisnicko_ime=?");
           dodajKategorijuUpit = connection.prepareStatement("INSERT INTO kategorije VALUES (?)");
           dajKategorijeUpit = connection.prepareStatement("SELECT * FROM kategorije");
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
            dodajKorisnikaUpit.setString(6,korisnik.getMjesto());
            dodajKorisnikaUpit.setString(7,korisnik.getAdresa());
            dodajKorisnikaUpit.setString(8,korisnik.getBrojTelefona());

            dodajKorisnikaUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void dodajKategoriju(Kategorija kategorija){
        try{
            dodajKategorijuUpit.setString(1,kategorija.getNazivKategorije());
            dodajKategorijuUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void dodajSlikuKorisnika(String korisnickoIme, FileInputStream inputStream, int duzina){
        try{
            obrisiSlikuKorisnikaUpit.setString(1,korisnickoIme);
            obrisiSlikuKorisnikaUpit.execute();
            dodajSlikuKorisnikaUpit.setString(1,korisnickoIme);
            dodajSlikuKorisnikaUpit.setBinaryStream(2,inputStream, duzina);
            dodajSlikuKorisnikaUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet dajSlikuKorisnika(String korisnickoIme){

        try {
            dajSlikuKorisnikaUpit.setString(1, korisnickoIme);
            ResultSet resultSet = dajSlikuKorisnikaUpit.executeQuery();
            if(resultSet!=null) return resultSet;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
return null;
    }

    public ResultSet dajKategorije(){
        try{
            return dajKategorijeUpit.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
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
                k.setMjesto(rs.getString(6));
                k.setAdresa(rs.getString(7));
                k.setBrojTelefona(rs.getString(8));
                k.setKorisnickoIme(korisnickoIme);
                return k;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public Korisnik nadjiPasswordKorisnika(String password){
        try{
            nadjiPasswordKorisnikaUpit.setString(1,password);
            ResultSet rs = nadjiPasswordKorisnikaUpit.executeQuery();
            if(rs.next()){
                Korisnik k = new Korisnik();
                Osoba o = new Osoba(rs.getString(1),rs.getString(2),rs.getString(3)); //Ime prezime datum
                k.setOsoba(o);
                k.setPassword(password);
                k.setMjesto(rs.getString(6));
                k.setAdresa(rs.getString(7));
                k.setBrojTelefona(rs.getString(8));

                k.setKorisnickoIme(rs.getString(4));
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

