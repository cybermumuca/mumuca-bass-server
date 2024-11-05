package com.mumuca.mumucabass.api.songlink.data;

import java.util.List;

public record SongEntity(
        String id,
        String type,
        String title,
        String artistName,
        String thumbnailUrl,
        int thumbnailWidth,
        int thumbnailHeight,
        String apiProvider,
        List<String> platforms
) {}