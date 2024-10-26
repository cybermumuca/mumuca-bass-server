package com.mumuca.mumufy.apis.deezer.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DeezerArtistTopTracks(
        List<TrackData> data,
        int total
) {
    record TrackData(
            long id,
            String title,
            String link,
            int duration,
            String preview,
            boolean explicitLyrics,
            Album album,
            @JsonProperty("contributors") List<Artist> interpreters
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
                String cover,
                String coverSmall,
                String coverMedium,
                String coverBig,
                String coverXl
        ) {}
    }
}