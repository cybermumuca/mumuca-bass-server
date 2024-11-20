package com.mumuca.mumucabass.dto.response;

import java.util.List;

public record AlbumDTO(
        long id,
        String title,
        String cover,
        String coverSmall,
        String coverMedium,
        String coverBig,
        String coverXl,
        String releaseDate,
        boolean isExplicit,
        int duration,
        String type,
        int numberOfTracks,
        Artist artist,
        List<Track> tracks
) {
    public record Artist(
            long id,
            String name,
            String picture,
            String pictureSmall,
            String pictureMedium,
            String pictureBig,
            String pictureXl
    ) {}
    public record Track(
            long id,
            String title,
            int duration,
            boolean isExplicit
    ) {}
}
