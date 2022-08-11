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
CREATE TABLE IF NOT EXISTS "kategorije" (
"naziv" TEXT
);

CREATE TABLE IF NOT EXISTS "artikli" (
"naziv" TEXT,
"kategorija" TEXT,
"cijena" TEXT,
"lokacija" TEXT,
"deskripcija" TEXT
);

INSERT INTO "kategorije" VALUES("Vozila");
INSERT INTO "kategorije" VALUES("Nekretnine");
INSERT INTO "kategorije" VALUES("Kompjuteri i laptopi");
INSERT INTO "kategorije" VALUES("Računarska oprema");
INSERT INTO "kategorije" VALUES("Mobilni uređaji");
INSERT INTO "kategorije" VALUES("Odjeća i obuća");
INSERT INTO "kategorije" VALUES("Moj dom");

INSERT INTO "artikli" VALUES("Artikal1","Vozila","12345","Sarajevo","Deskripcija1");

