package com.fsre.imenik.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;

@Document("broj_drzave")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BrojDrzave {

    @Id
    private String id;

    @Field(name = "drzava")
    @NotEmpty
    private String drzava;

    @Field(name = "pozivni_broj_drzave")
    @NotEmpty
    private String pozivniBrojDrzave;

    public BrojDrzave(String drzava, String pozivniBrojDrzave) {
        this.drzava = drzava;
        this.pozivniBrojDrzave = pozivniBrojDrzave;
    }
}
