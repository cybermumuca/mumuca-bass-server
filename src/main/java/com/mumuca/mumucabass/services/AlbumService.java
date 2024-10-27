package com.mumuca.mumucabass.services;

import com.mumuca.mumucabass.apis.deezer.DeezerAPI;
import com.mumuca.mumucabass.apis.deezer.data.DeezerAlbum;
import com.mumuca.mumucabass.apis.deezer.data.DeezerAlbumSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    @Autowired
    private DeezerAPI deezerAPI;

    public DeezerAlbumSearch search(String query) {
        return deezerAPI.searchAlbum(query);
    }

    @Cacheable(value = "album", key = "#id")
    public DeezerAlbum getAlbum(long id) {
        return deezerAPI.getAlbum(id);
    }

//    public ResponseEntity<String> downloadAlbum(long id) {
//        return deezerAPI.downloadAlbum(id);
//    }
}
