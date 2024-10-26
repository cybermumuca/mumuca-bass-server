package com.mumuca.mumufy.apis.songlink.data;

public record PlatformLink(
        String country,
        String url,
        String nativeAppUriDesktop,
        String entityUniqueId
) {}