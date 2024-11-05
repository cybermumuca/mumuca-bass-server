package com.mumuca.mumucabass.api.deezer.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DeezerTrackSearch(
        List<Track> data,
        int total,
        String prev,
        String next
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record Track(
            long id,
            String title,
            String link,
            int duration,
            boolean explicitLyrics,
            DeezerArtist artist,
            Album album
    ) {
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
