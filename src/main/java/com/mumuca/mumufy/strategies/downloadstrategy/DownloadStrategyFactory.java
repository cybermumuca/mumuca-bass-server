package com.mumuca.mumufy.strategies.downloadstrategy;

import com.mumuca.mumufy.strategies.downloadstrategy.implementations.DeezerDownloadStrategy;
import com.mumuca.mumufy.strategies.downloadstrategy.implementations.SpotifyDownloadStrategy;
import com.mumuca.mumufy.strategies.downloadstrategy.implementations.TidalDownloadStrategy;
import com.mumuca.mumufy.strategies.downloadstrategy.implementations.YouTubeDownloadStrategy;

import java.util.Map;

public class DownloadStrategyFactory {
    private static final Map<String, DownloadStrategy> strategies = Map.of(
            "spotify", new SpotifyDownloadStrategy(),
            "deezer", new DeezerDownloadStrategy(),
            "tidal", new TidalDownloadStrategy(),
            "youtube", new YouTubeDownloadStrategy()
    );

    public static DownloadStrategy getStrategy(String platform) {
        return strategies.get(platform);
    }
}