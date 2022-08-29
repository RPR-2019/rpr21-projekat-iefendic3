CREATE TABLE IF NOT EXISTS "korisnik" (
   "ime" TEXT,
   "prezime" TEXT,
   "datum_rodjenja" TEXT,
   "korisnicko_ime" TEXT,
   "password" TEXT,
   "mjesto" TEXT,
   "adresa" TEXT,
   "broj_telefona" TEXT

);

CREATE TABLE IF NOT EXISTS "slikaKorisnika" (
"korisnicko_ime" TEXT,
"slika" BLOB
);
CREATE TABLE IF NOT EXISTS "slikaArtikla" (
"naziv" TEXT,
"kategorija" TEXT,
"cijena" TEXT,
"lokacija" TEXT,
"deskripcija" TEXT,
"korisnik" TEXT,
"slika" BLOB
);
CREATE TABLE IF NOT EXISTS "kategorije" (
"naziv" TEXT
);

CREATE TABLE IF NOT EXISTS "artikli" (
"naziv" TEXT,
"kategorija" TEXT,
"cijena" TEXT,
"lokacija" TEXT,
"deskripcija" TEXT,
"korisnik" TEXT
);

CREATE TABLE IF NOT EXISTS "kupljeni_artikli" (
"naziv" TEXT,
"kategorija" TEXT,
"cijena" TEXT,
"lokacija" TEXT,
"deskripcija" TEXT,
"korisnik" TEXT
);

CREATE TABLE IF NOT EXISTS "prodani_artikli" (
"naziv" TEXT,
"kategorija" TEXT,
"cijena" TEXT,
"lokacija" TEXT,
"deskripcija" TEXT,
"korisnik" TEXT
);

CREATE TABLE IF NOT EXISTS "komentari"(
"korisnik" TEXT,
"tekst" TEXT,
"recenzija" TEXT,
"autor" TEXT
);

INSERT INTO "kategorije" VALUES("Vozila");
INSERT INTO "kategorije" VALUES("Nekretnine");
INSERT INTO "kategorije" VALUES("Kompjuteri i laptopi");
INSERT INTO "kategorije" VALUES("Računarska oprema");
INSERT INTO "kategorije" VALUES("Mobilni uređaji");
INSERT INTO "kategorije" VALUES("Odjeća i obuća");
INSERT INTO "kategorije" VALUES("Moj dom");

INSERT INTO "korisnik" VALUES("Test1","Test1","2001-10-26","test1","test1","Sarajevo","Bjelave","062333444");
INSERT INTO "korisnik" VALUES("Test2","Test2","2001-10-26","test2","test2","Sarajevo","Bjelave","062333444");

INSERT INTO "artikli" VALUES("Stan", "Nekretnine", "350000","Sarajevo - Otoka", "Stan od 100 kvadrata","test2");
INSERT INTO "artikli" VALUES("Mercedes S klasa", "Vozila", "100000","Sarajevo - Centar", "Mercedes S klasa 2020.","test1");
INSERT INTO "artikli" VALUES("Farmerke", "Odjeća i obuća", "50","Sarajevo - Čengić Vila", "Muške farmerke","test2");
INSERT INTO "artikli" VALUES("Lenovo laptop ideapad", "Računarska oprema", "800","Sarajevo - Malta", "i7 procesor, 16GB ram","test2");


