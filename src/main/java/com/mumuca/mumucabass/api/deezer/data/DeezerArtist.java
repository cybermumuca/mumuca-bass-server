package com.mumuca.mumucabass.api.deezer.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DeezerArtist(
        long id,
        String name,
        String link,
        String picture,
        String pictureSmall,
        String pictureMedium,
        String pictureBig,
        String pictureXl
) {}
