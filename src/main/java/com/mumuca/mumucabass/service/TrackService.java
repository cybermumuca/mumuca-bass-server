package com.mumuca.mumucabass.service;

import com.mumuca.mumucabass.api.deezer.DeezerAPI;
import com.mumuca.mumucabass.api.deezer.data.DeezerTrack;
import com.mumuca.mumucabass.api.deezer.data.DeezerTrackSearch;
import com.mumuca.mumucabass.api.songlink.SongLinkAPI;
import com.mumuca.mumucabass.dto.response.TrackDTO;
import com.mumuca.mumucabass.mapper.DeezerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TrackService {

    private final DeezerAPI deezerAPI;
    private final SongLinkAPI songLinkAPI;

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
}
