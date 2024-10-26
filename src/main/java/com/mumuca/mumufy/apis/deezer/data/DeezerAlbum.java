package com.mumuca.mumufy.apis.deezer.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;
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
        LocalDate releaseDate,
        boolean explicitLyrics,
        int duration,
        String recordType,
        Genre genres,
        @JsonProperty("nb_tracks") int trackLength,
        Artist artist,
        Track tracks
) {
    record Genre(
            List<GenreData> data
    ) {
        record GenreData(
                long id,
                String name
        ) {}
    }
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
        record TrackData(
                long id,
                String title,
                int duration,
                boolean explicitLyrics,
                String preview,
                String link,
                ArtistOnTrack artist
        ) {
            record ArtistOnTrack(
                    long id,
                    String name
            ) {}
        }
    }
}
