package com.fsre.imenik.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

@Document("pozivi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Poziv {
    @Id
    private String id;

    @Field(name = "trajanje")
    @NotEmpty
    private Long trajanje;

    @Field(name = "datum")
    @NotEmpty
    private Date datum;

    @Field(name = "id_imenika")
    @NotEmpty
    private String idImenika;


    public Poziv(Long trajanje, Date datum, String idImenika) {
        this.trajanje = trajanje;
        this.datum = datum;
        this.idImenika = idImenika;
    }
}
