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

