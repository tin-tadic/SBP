package com.fsre.imenik.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;

@Document("favoriti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Imenik {
    @Id
    private String id;

    @Field(name = "ime")
    @NotEmpty
    private String ime;

    @Field(name = "prezime")
    @NotEmpty
    private String prezime;

    @Field(name = "broj_telefona")
    @NotEmpty
    private String brojTelefona;

    @Field(name = "id_broja_drzave")
    @NotEmpty
    private String idBrojaDrzave;

    @Field(name = "id_korisnika")
    @NotEmpty
    private String idKorisnika;

    public Imenik(String ime, String prezime, String brojTelefona, String idBrojaDrzave, String idKorisnika) {
        this.ime = ime;
        this.prezime = prezime;
        this.brojTelefona = brojTelefona;
        this.idBrojaDrzave = idBrojaDrzave;
        this.idKorisnika = idKorisnika;
    }
}
