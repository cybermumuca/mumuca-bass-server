package com.mumuca.mumufy.services;

import com.mumuca.mumufy.apis.deezer.DeezerAPI;
import com.mumuca.mumufy.apis.deezer.data.DeezerArtist;
import com.mumuca.mumufy.apis.deezer.data.DeezerArtistSearch;
import com.mumuca.mumufy.apis.deezer.data.DeezerArtistTopTracks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    @Autowired
    private DeezerAPI deezerAPI;

    public DeezerArtistSearch search(String query) {
        return deezerAPI.searchArtist(query);
    }

    @Cacheable(value = "artist", key = "#id")
    public DeezerArtist getArtist(long id) {
        return deezerAPI.getArtist(id);
    }

    @Cacheable(value = "artistTopTracks", key = "#id")
    public DeezerArtistTopTracks getTopTracks(long id) {
        return deezerAPI.getArtistTopTracks(id);
    }
}
