package com.mumuca.mumucabass.strategy.downloadstrategy.implementations;

import com.mumuca.mumucabass.strategy.downloadstrategy.DownloadStrategy;

public class YouTubeDownloadStrategy implements DownloadStrategy {
    @Override
    public void downloadMusic(String url) {
        System.out.println("Baixando música do Youtube: " + url);
        // Implementação específica do download para YouTube
    }
}