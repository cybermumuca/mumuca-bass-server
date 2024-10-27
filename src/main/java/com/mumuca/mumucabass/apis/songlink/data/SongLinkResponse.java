package com.mumuca.mumucabass.apis.songlink.data;

import java.util.Map;

public record SongLinkResponse(
        String entityUniqueId,
        String userCountry,
        String pageUrl,
        Map<String, SongEntity> entitiesByUniqueId,
        Map<String, PlatformLink> linksByPlatform
) {}
