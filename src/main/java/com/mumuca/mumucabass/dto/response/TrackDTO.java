package com.mumuca.mumucabass.dto.response;

import java.util.List;

public record TrackDTO(
        long id,
        String title,
        boolean isExplicit,
        int duration,
        Float bpm,
        Album album,
        List<Artist> artists
) {
    public record Album(
            long id,
            String title,
            String cover,
            String coverSmall,
            String coverMedium,
            String coverBig,
            String coverXl,
            String releaseDate
    ) {}
    public record Artist(
            long id,
            String name,
            String picture,
            String pictureSmall,
            String pictureMedium,
            String pictureBig,
            String pictureXl
    ) {}
}



