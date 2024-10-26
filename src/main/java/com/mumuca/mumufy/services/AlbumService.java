package com.mumuca.mumufy.services;

import com.mumuca.mumufy.apis.deezer.DeezerAPI;
import com.mumuca.mumufy.apis.deezer.data.DeezerAlbum;
import com.mumuca.mumufy.apis.deezer.data.DeezerAlbumSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    @Autowired
    private DeezerAPI deezerAPI;

    public DeezerAlbumSearch search(String query) {
        return deezerAPI.searchAlbum(query);
    }

    public DeezerAlbum getAlbum(long id) {
        return deezerAPI.getAlbum(id);
    }

//    public ResponseEntity<String> downloadAlbum(long id) {
//        return deezerAPI.downloadAlbum(id);
//    }
}
