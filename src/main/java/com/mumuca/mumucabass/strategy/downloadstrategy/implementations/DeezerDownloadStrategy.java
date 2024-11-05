package com.mumuca.mumucabass.strategy.downloadstrategy.implementations;

import com.mumuca.mumucabass.strategy.downloadstrategy.DownloadStrategy;

public class DeezerDownloadStrategy implements DownloadStrategy {
    @Override
    public void downloadMusic(String url) {
        System.out.println("Baixando música do Deezer: " + url);
        // Implementação específica do download para Deezer
    }
}