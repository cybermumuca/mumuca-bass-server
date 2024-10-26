package com.mumuca.mumufy.strategies.downloadstrategy.implementations;

import com.mumuca.mumufy.strategies.downloadstrategy.DownloadStrategy;

public class DeezerDownloadStrategy implements DownloadStrategy {
    @Override
    public void downloadMusic(String url) {
        System.out.println("Baixando música do Deezer: " + url);
        // Implementação específica do download para Deezer
    }
}