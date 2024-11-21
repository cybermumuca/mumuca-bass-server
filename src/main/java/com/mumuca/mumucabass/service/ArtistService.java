package com.mumuca.mumucabass.service;

import com.mumuca.mumucabass.api.deezer.DeezerAPI;
import com.mumuca.mumucabass.api.deezer.data.DeezerArtist;
import com.mumuca.mumucabass.api.deezer.data.DeezerArtistSearch;
import com.mumuca.mumucabass.api.deezer.data.DeezerArtistTopTracks;
import com.mumuca.mumucabass.dto.response.ArtistDTO;
import com.mumuca.mumucabass.dto.response.TopTrackDTO;
import com.mumuca.mumucabass.mapper.DeezerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final DeezerAPI deezerAPI;

    @Autowired
    public ArtistService(DeezerAPI deezerAPI) {
        this.deezerAPI = deezerAPI;
    }

    public DeezerArtistSearch search(String query) {
        return deezerAPI.searchArtist(query);
    }

    @Cacheable(value = "artist", key = "#id")
    public ArtistDTO getArtist(long id) {
        DeezerArtist artist = deezerAPI.getArtist(id);

        return DeezerMapper.toArtist(artist);
    }

    @Cacheable(value = "artistTopTracks", key = "#id")
    public List<TopTrackDTO> getTopTracks(long id) {
        DeezerArtistTopTracks deezerArtistTopTracks = deezerAPI.getArtistTopTracks(id);

        return DeezerMapper.toTopTracks(deezerArtistTopTracks);
    }
}
