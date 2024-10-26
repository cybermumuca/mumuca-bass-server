package com.mumuca.mumufy.apis.deezer.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DeezerTrack(
        long id,
        String title,
        String link,
        boolean explicitLyrics,
        int duration,
        float bpm,
        @JsonProperty("contributors") List<Artist> interpreters,
        Album album
) {
    record Artist(
            long id,
            String name,
            String link,
            String picture,
            String pictureSmall,
            String pictureMedium,
            String pictureBig,
            String pictureXl
    ) {}
    record Album(
            long id,
            String title,
            String link,
            String cover,
            String coverSmall,
            String coverMedium,
            String coverBig,
            String coverXl,
            LocalDate releaseDate
    ) {}
}
