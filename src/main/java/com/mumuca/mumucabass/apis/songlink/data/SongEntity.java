package com.mumuca.mumucabass.apis.songlink.data;

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