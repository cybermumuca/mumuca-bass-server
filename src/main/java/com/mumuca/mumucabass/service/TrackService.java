package com.mumuca.mumucabass.service;

import com.mumuca.mumucabass.api.deezer.DeezerAPI;
import com.mumuca.mumucabass.api.deezer.data.DeezerTrackSearch;
import com.mumuca.mumucabass.api.deezer.data.DeezerTrack;
import com.mumuca.mumucabass.api.songlink.SongLinkAPI;
import com.mumuca.mumucabass.api.songlink.data.PlatformLink;
import com.mumuca.mumucabass.api.songlink.data.SongLinkResponse;
import com.mumuca.mumucabass.dto.response.TrackDTO;
import com.mumuca.mumucabass.mapper.DeezerMapper;
import com.mumuca.mumucabass.strategy.downloadstrategy.DownloadStrategy;
import com.mumuca.mumucabass.strategy.downloadstrategy.DownloadStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrackService {

    private final DeezerAPI deezerAPI;
    private final SongLinkAPI songLinkAPI;
    // Precedence was chosen based on the criteria of Availability and Quality
    private final List<String> precedence = List.of("spotify", "deezer", "tidal", "youtube");

    @Autowired
    public TrackService(DeezerAPI deezerAPI, SongLinkAPI songLinkAPI) {
        this.deezerAPI = deezerAPI;
        this.songLinkAPI = songLinkAPI;
    }

    public DeezerTrackSearch search(String query) {
        return deezerAPI.searchTrack(query);
    }

    @Cacheable(value = "track", key = "#id")
    public TrackDTO getTrack(long id) {
        DeezerTrack deezerTrack = deezerAPI.getTrack(id);

        return DeezerMapper.toTrack(deezerTrack);
    }

    // TODO: Implement the streamTrack method
    public String streamTrack(long id) {
        DeezerTrack track = deezerAPI.getTrack(id);

        SongLinkResponse response = songLinkAPI.getLinks(track.link());

        Map<String, PlatformLink> links = response.linksByPlatform();

        Optional<String> successfulDownloadPlatform = precedence.stream()
                .map(platform -> Map.entry(platform, links.get(platform)))
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> {
                    DownloadStrategy strategy = DownloadStrategyFactory.getStrategy(entry.getKey());
                    if (strategy != null) {
                        try {
                            strategy.downloadMusic(entry.getValue().url());
                            System.out.println("Download realizado com sucesso da plataforma: " + entry.getKey());
                            return true;
                        } catch (Exception e) {
                            System.out.println("Falha ao baixar da plataforma: " + entry.getKey() + ". Tentando próxima.");
                            return false;
                        }
                    } else {
                        System.out.println("Nenhuma estratégia disponível para a plataforma: " + entry.getKey());
                        return false;
                    }
                })
                .map(Map.Entry::getKey)
                .findFirst();

        return successfulDownloadPlatform
                .map(platform -> "Download bem-sucedido da plataforma: " + platform)
                .orElse("Nenhum download foi bem-sucedido de qualquer plataforma.");
    }

}
