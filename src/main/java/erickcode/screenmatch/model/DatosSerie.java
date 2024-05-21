package erickcode.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        //JsonAlias permite leer el datos
        //JsonProperty permite leer y escribir datos

        @JsonAlias("Title")  String titulo,

        @JsonAlias("totalSeasons") Integer totalTemporadas,

        @JsonAlias("imdbRating") String evaluacion
        ) {
}
