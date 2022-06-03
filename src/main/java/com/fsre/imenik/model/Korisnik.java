package com.fsre.imenik.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;

@Document("korisnik")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Korisnik {
    @Id
    private String id;

    @Field(name = "korisnicko_ime")
    @NotEmpty
    private String korisnickoIme;

    @Field(name = "lozinka")
    @NotEmpty
    private String lozinka;

    @Field(name = "uloga")
    @NotEmpty
    private String uloga;

    @Field(name = "ime")
    @NotEmpty
    private String ime;

    @Field(name = "prezime")
    @NotEmpty
    private String prezime;

    @Field(name = "broj_telefona")
    @NotEmpty
    private String brojTelefona;

    @Field(name = "adresa")
    @NotEmpty
    private String adresa;

    public Korisnik(String korisnickoIme, String lozinka, String uloga, String ime, String prezime, String brojTelefona, String adresa) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.uloga = uloga;
        this.prezime = prezime;
        this.brojTelefona = brojTelefona;
        this.adresa = adresa;
        this.ime = ime;
    }
}
