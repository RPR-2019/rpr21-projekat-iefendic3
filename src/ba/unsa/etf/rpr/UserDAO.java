package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class UserDAO {
    private static UserDAO instance = null;
    private Connection connection;
    private PreparedStatement dajKorisnikeUpit,dodajKorisnikaUpit, nadjiKorisnikaUpit, nadjiPasswordKorisnikaUpit, dodajSlikuKorisnikaUpit,
    dajSlikuKorisnikaUpit, obrisiSlikuKorisnikaUpit, dodajKategorijuUpit, dajKategorijeUpit, dodajArtikalUpit, dajArtikleUpit, obrisiArtikalUpit, dodajKupljeniArtikalUpit,
            dajKupljeneArtikleZaKorisnikaUpit, dajProdaneArtikleZaKorisnikaUpit, dodajProdaniArtikal, dajArtikleKorisnikaUpit,
    dodajKomentarUpit, dajKomentareKorisnikaUpit,dodajSlikuArtiklaUpit,dajSlikuArtiklaUpit;

    private UserDAO() {
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
           dodajProdaniArtikal = connection.prepareStatement("INSERT INTO prodani_artikli VALUES (?,?,?,?,?,?)");
           dajKupljeneArtikleZaKorisnikaUpit = connection.prepareStatement("SELECT * FROM kupljeni_artikli WHERE korisnik=?");
           dajProdaneArtikleZaKorisnikaUpit = connection.prepareStatement("SELECT * FROM prodani_artikli WHERE korisnik=?");
           dajArtikleKorisnikaUpit = connection.prepareStatement("SELECT * FROM artikli WHERE korisnik=?");
           dodajKomentarUpit = connection.prepareStatement("INSERT INTO komentari VALUES (?,?,?,?)");
           dajKomentareKorisnikaUpit = connection.prepareStatement("SELECT * FROM komentari WHERE korisnik=?");
           dodajSlikuArtiklaUpit = connection.prepareStatement("INSERT INTO slikaArtikla VALUES (?,?,?,?,?,?,?)");
           dajSlikuArtiklaUpit=connection.prepareStatement("SELECT slika FROM slikaArtikla WHERE naziv=? AND kategorija=?" +
                   "AND cijena=? AND lokacija=? AND deskripcija=? AND korisnik=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public void vratiBazuNaDefault() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM artikli");
        stmt.executeUpdate("DELETE FROM prodani_artikli");
        stmt.executeUpdate("DELETE FROM kupljeni_artikli");
        stmt.executeUpdate("DELETE FROM kategorije");
        stmt.executeUpdate("DELETE FROM korisnik");
        stmt.executeUpdate("DELETE FROM komentari");
        stmt.executeUpdate("DELETE FROM slikaArtikla");
        stmt.executeUpdate("DELETE FROM slikaKorisnika");
        // Regeneriši bazu neće ponovo kreirati tabele jer u .sql datoteci stoji
        // CREATE TABLE IF NOT EXISTS
        // Ali će ponovo napuniti default podacima
        generisiBazu();
    }


    public static UserDAO getInstance() {
        if (instance == null) instance = new UserDAO();
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

    public void dodajKupljeniArtikal(Article artikal){
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
    public void dodajKomentar(Comment comment){
        try{
            dodajKomentarUpit.setString(1, comment.getKorisnickoIme());
            dodajKomentarUpit.setString(2, comment.getTekstKomentara());
            dodajKomentarUpit.setString(3, comment.getRecenzija().toString());
            dodajKomentarUpit.setString(4, comment.getAutor());
            dodajKomentarUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void dodajProdaniArtikal(Article artikal){
        try{
            dodajProdaniArtikal.setString(1,artikal.getNaziv());
            dodajProdaniArtikal.setString(2,artikal.getKategorija().getNazivKategorije());
            dodajProdaniArtikal.setString(3,artikal.getCijena());
            dodajProdaniArtikal.setString(4,artikal.getLokacija());
            dodajProdaniArtikal.setString(5,artikal.getDeskripcija());
            dodajProdaniArtikal.setString(6,artikal.getKorisnik());

            dodajProdaniArtikal.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void dodajKorisnika(User user){
        try{
            dodajKorisnikaUpit.setString(1, user.getOsoba().getIme());
            dodajKorisnikaUpit.setString(2, user.getOsoba().getPrezime());
            dodajKorisnikaUpit.setString(3, user.getOsoba().getDatumRodjenja());
            dodajKorisnikaUpit.setString(4, user.getKorisnickoIme());
            dodajKorisnikaUpit.setString(5, user.getPassword());
            dodajKorisnikaUpit.setString(6, user.getMjesto());
            dodajKorisnikaUpit.setString(7, user.getAdresa());
            dodajKorisnikaUpit.setString(8, user.getBrojTelefona());

            dodajKorisnikaUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void dodajKategoriju(Category category){
        try{
            dodajKategorijuUpit.setString(1, category.getNazivKategorije());
            dodajKategorijuUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void dodajArtikal(Article artikal){
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

    public void dodajSlikuArtikla(Article artikal, FileInputStream inputStream, int duzina){
        try{
            dodajSlikuArtiklaUpit.setString(1,artikal.getNaziv());
            dodajSlikuArtiklaUpit.setString(2,artikal.getKategorija().getNazivKategorije());
            dodajSlikuArtiklaUpit.setString(3,artikal.getCijena());
            dodajSlikuArtiklaUpit.setString(4,artikal.getLokacija());
            dodajSlikuArtiklaUpit.setString(5,artikal.getDeskripcija());
            dodajSlikuArtiklaUpit.setString(6,artikal.getKorisnik());
            dodajSlikuArtiklaUpit.setBinaryStream(7,inputStream, duzina);
            dodajSlikuArtiklaUpit.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public ResultSet dajSlikuArtikla(Article artikal){

        try {
            dajSlikuArtiklaUpit.setString(1,artikal.getNaziv());
            dajSlikuArtiklaUpit.setString(2,artikal.getKategorija().getNazivKategorije());
            dajSlikuArtiklaUpit.setString(3,artikal.getCijena());
            dajSlikuArtiklaUpit.setString(4,artikal.getLokacija());
            dajSlikuArtiklaUpit.setString(5,artikal.getDeskripcija());
            dajSlikuArtiklaUpit.setString(6,artikal.getKorisnik());
            //ResultSet resultSet = dajSlikuKorisnikaUpit.executeQuery();
            //if(resultSet!=null) return resultSet;
            return dajSlikuArtiklaUpit.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();

        }
        return null;
    }

    public ResultSet dajSlikuKorisnika(String korisnickoIme){

        try {
            dajSlikuKorisnikaUpit.setString(1, korisnickoIme);
            //ResultSet resultSet = dajSlikuKorisnikaUpit.executeQuery();
            //if(resultSet!=null) return resultSet;
            return dajSlikuKorisnikaUpit.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();

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

    public User nadjiKorisnika(String korisnickoIme){
        try{
            nadjiKorisnikaUpit.setString(1,korisnickoIme);
            ResultSet rs = nadjiKorisnikaUpit.executeQuery();
            if(rs.next()){
                User k = new User();
                Person o = new Person(rs.getString(1),rs.getString(2),rs.getString(3)); //Ime prezime datum
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
    public User nadjiPasswordKorisnika(String password){
        try{
            nadjiPasswordKorisnikaUpit.setString(1,password);
            ResultSet rs = nadjiPasswordKorisnikaUpit.executeQuery();
            if(rs.next()){
                User k = new User();
                Person o = new Person(rs.getString(1),rs.getString(2),rs.getString(3)); //Ime prezime datum
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

