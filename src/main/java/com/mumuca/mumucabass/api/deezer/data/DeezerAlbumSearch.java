package com.mumuca.mumucabass.api.deezer.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DeezerAlbumSearch(
        List<AlbumData> data,
        int total,
        String prev,
        String next
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record AlbumData(
            long id,
            String title,
            String link,
            String type,
            String cover,
            String coverSmall,
            String coverMedium,
            String coverBig,
            String coverXl,
            @JsonProperty("nb_tracks") int trackLength,
            String recordType,
            @JsonProperty("tracklist") String tracklistURL,
            boolean explicitLyrics,
            Artist artist
    ) {}

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record Artist(
            long id,
            String name,
            String link,
            String picture,
            String pictureSmall,
            String pictureMedium,
            String pictureBig,
            String pictureXl,
            String type
    ) {}
}
