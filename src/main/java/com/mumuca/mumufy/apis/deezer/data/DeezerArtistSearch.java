package com.mumuca.mumufy.apis.deezer.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DeezerArtistSearch(
        List<ArtistData> data,
        int total,
        String prev,
        String next
) {
    record ArtistData(
            long id,
            String name,
            String link,
            String picture,
            String pictureSmall,
            String pictureMedium,
            String pictureBig,
            String pictureXl
    ) {}
}
