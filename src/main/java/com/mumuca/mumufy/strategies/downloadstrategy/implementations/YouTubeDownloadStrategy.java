package com.mumuca.mumufy.strategies.downloadstrategy.implementations;

import com.mumuca.mumufy.strategies.downloadstrategy.DownloadStrategy;

public class YouTubeDownloadStrategy implements DownloadStrategy {
    @Override
    public void downloadMusic(String url) {
        System.out.println("Baixando música do Youtube: " + url);
        // Implementação específica do download para YouTube
    }
}