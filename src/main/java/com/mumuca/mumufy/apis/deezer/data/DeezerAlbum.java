package com.mumuca.mumufy.apis.deezer.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DeezerAlbum(
        long id,
        String title,
        String link,
        String cover,
        String coverSmall,
        String coverMedium,
        String coverBig,
        String coverXl,
        String releaseDate,
        boolean explicitLyrics,
        int duration,
        String recordType,
        Genre genres,
        @JsonProperty("nb_tracks") int trackLength,
        Artist artist,
        Track tracks
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record Genre(
            List<GenreData> data
    ) {
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        record GenreData(
                long id,
                String name
        ) {}
    }
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
    record Track(
            List<TrackData> data
    ) {
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        record TrackData(
                long id,
                String title,
                int duration,
                boolean explicitLyrics,
                String preview,
                String link,
                ArtistOnTrack artist
        ) {
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            record ArtistOnTrack(
                    long id,
                    String name
            ) {}
        }
    }
}
