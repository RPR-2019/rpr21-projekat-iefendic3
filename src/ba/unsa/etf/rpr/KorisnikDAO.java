package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class KorisnikDAO {
    private static KorisnikDAO instance = null;
    private Connection connection;
    private PreparedStatement dajKorisnikeUpit,dodajKorisnikaUpit, nadjiKorisnikaUpit, nadjiPasswordKorisnikaUpit, dodajSlikuKorisnikaUpit,
    dajSlikuKorisnikaUpit, obrisiSlikuKorisnikaUpit, dodajKategorijuUpit, dajKategorijeUpit, dodajArtikalUpit, dajArtikleUpit, obrisiArtikalUpit, dodajKupljeniArtikalUpit,
            dajKupljeneArtikleZaKorisnikaUpit, dajProdaneArtikleZaKorisnikaUpit, dajProdaniArtikalUpit, dajArtikleKorisnikaUpit,
    dodajKomentarUpit, dajKomentareKorisnikaUpit;

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
           dodajArtikalUpit = connection.prepareStatement("INSERT INTO artikli VALUES (?,?,?,?,?,?)");
           dajArtikleUpit = connection.prepareStatement("SELECT * FROM artikli");
           obrisiArtikalUpit = connection.prepareStatement("DELETE from artikli WHERE naziv=? AND deskripcija=?");
           dodajKupljeniArtikalUpit = connection.prepareStatement("INSERT INTO kupljeni_artikli VALUES (?,?,?,?,?,?)");
           dajProdaniArtikalUpit = connection.prepareStatement("INSERT INTO prodani_artikli VALUES (?,?,?,?,?,?)");
           dajKupljeneArtikleZaKorisnikaUpit = connection.prepareStatement("SELECT * FROM kupljeni_artikli WHERE korisnik=?");
           dajProdaneArtikleZaKorisnikaUpit = connection.prepareStatement("SELECT * FROM prodani_artikli WHERE korisnik=?");
           dajArtikleKorisnikaUpit = connection.prepareStatement("SELECT * FROM artikli WHERE korisnik=?");
           dodajKomentarUpit = connection.prepareStatement("INSERT INTO komentari VALUES (?,?,?)");
           dajKomentareKorisnikaUpit = connection.prepareStatement("SELECT * FROM komentari WHERE korisnik=?");
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

    public void obrisiArtikal(String naziv, String deskripcija){
        try{
            obrisiArtikalUpit.setString(1,naziv);
            obrisiArtikalUpit.setString(2,deskripcija);
            obrisiArtikalUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void dodajKupljeniArtikal(Artikal artikal){
        try{
            dodajKupljeniArtikalUpit.setString(1,artikal.getNaziv());
            dodajKupljeniArtikalUpit.setString(2,artikal.getKategorija().getNazivKategorije());
            dodajKupljeniArtikalUpit.setString(3,artikal.getCijena());
            dodajKupljeniArtikalUpit.setString(4,artikal.getLokacija());
            dodajKupljeniArtikalUpit.setString(5,artikal.getDeskripcija());
            dodajKupljeniArtikalUpit.setString(6,artikal.getKorisnik());
            dodajKupljeniArtikalUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void dodajKomentar(Komentar komentar){
        try{
            dodajKomentarUpit.setString(1,komentar.getKorisnickoIme());
            dodajKomentarUpit.setString(2,komentar.getTekstKomentara());
            dodajKomentarUpit.setString(3,komentar.getRecenzija().toString());
            dodajKomentarUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void dodajProdaniArtikal(Artikal artikal){
        try{
            dajProdaniArtikalUpit.setString(1,artikal.getNaziv());
            dajProdaniArtikalUpit.setString(2,artikal.getKategorija().getNazivKategorije());
            dajProdaniArtikalUpit.setString(3,artikal.getCijena());
            dajProdaniArtikalUpit.setString(4,artikal.getLokacija());
            dajProdaniArtikalUpit.setString(5,artikal.getDeskripcija());
            dajProdaniArtikalUpit.setString(6,artikal.getKorisnik());
            dajProdaniArtikalUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
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
    public void dodajArtikal(Artikal artikal){
        try{
            dodajArtikalUpit.setString(1,artikal.getNaziv());
            dodajArtikalUpit.setString(2,artikal.getKategorija().getNazivKategorije());
            dodajArtikalUpit.setString(3,artikal.getCijena());
            dodajArtikalUpit.setString(4,artikal.getLokacija());
            dodajArtikalUpit.setString(5,artikal.getDeskripcija());
            dodajArtikalUpit.setString(6,artikal.getKorisnik());
            dodajArtikalUpit.execute();
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


    public ResultSet dajKupljeneArtikle(String korisnik){
        try{
            dajKupljeneArtikleZaKorisnikaUpit.setString(1,korisnik);
            return dajKupljeneArtikleZaKorisnikaUpit.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet dajKomentareKorisnika(String korisnik){
        try{
            dajKomentareKorisnikaUpit.setString(1,korisnik);
            return dajKomentareKorisnikaUpit.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet dajProdaneArtikle(String korisnik){
        try{
            dajProdaneArtikleZaKorisnikaUpit.setString(1,korisnik);
            return dajProdaneArtikleZaKorisnikaUpit.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
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
    public ResultSet dajArtikleKorisnika(String korisnik){
        try{
            dajArtikleKorisnikaUpit.setString(1, korisnik);
            return dajArtikleKorisnikaUpit.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet dajArtikle(){
        try{
            return dajArtikleUpit.executeQuery();
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

