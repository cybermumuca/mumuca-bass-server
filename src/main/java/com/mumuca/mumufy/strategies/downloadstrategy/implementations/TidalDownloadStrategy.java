package com.mumuca.mumufy.strategies.downloadstrategy.implementations;

import com.mumuca.mumufy.strategies.downloadstrategy.DownloadStrategy;

public class TidalDownloadStrategy implements DownloadStrategy {
    @Override
    public void downloadMusic(String url) {
        System.out.println("Baixando música do Tidal: " + url);
        // Implementação específica do download para Tidal
    }
}