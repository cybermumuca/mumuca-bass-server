package com.mumuca.mumucabass.dto.response;

public record ArtistDTO(
        long id,
        String name,
        String picture,
        String pictureSmall,
        String pictureMedium,
        String pictureBig,
        String pictureXl
) {}
