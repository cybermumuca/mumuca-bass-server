package com.mumuca.mumucabass.strategy.downloadstrategy;

import com.mumuca.mumucabass.strategy.downloadstrategy.implementations.DeezerDownloadStrategy;
import com.mumuca.mumucabass.strategy.downloadstrategy.implementations.SpotifyDownloadStrategy;
import com.mumuca.mumucabass.strategy.downloadstrategy.implementations.TidalDownloadStrategy;
import com.mumuca.mumucabass.strategy.downloadstrategy.implementations.YouTubeDownloadStrategy;

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
