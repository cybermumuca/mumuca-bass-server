package com.mumuca.mumucabass.api.songlink.data;

public record PlatformLink(
        String country,
        String url,
        String nativeAppUriDesktop,
        String entityUniqueId
) {}