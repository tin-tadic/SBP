package com.fsre.imenik.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

@Document("propusteni_pozivi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PropusteniPoziv {
    @Id
    private String id;

    @Field(name = "datum_vrijeme")
    @NotEmpty
    private Date datumVrijeme;

    @Field(name = "id_imenika")
    @NotEmpty
    private String idImenika;


    public PropusteniPoziv(Date datumVrijeme, String idImenika) {
        this.datumVrijeme = datumVrijeme;
        this.idImenika = idImenika;
    }
}
