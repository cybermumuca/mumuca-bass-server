package com.mumuca.mumucabass.service;

import com.mumuca.mumucabass.api.deezer.DeezerAPI;
import com.mumuca.mumucabass.api.deezer.data.DeezerAlbum;
import com.mumuca.mumucabass.api.deezer.data.DeezerAlbumSearch;
import com.mumuca.mumucabass.dto.response.AlbumDTO;
import com.mumuca.mumucabass.mapper.DeezerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    private final DeezerAPI deezerAPI;

    @Autowired
    public AlbumService(DeezerAPI deezerAPI) {
        this.deezerAPI = deezerAPI;
    }

    public DeezerAlbumSearch search(String query) {
        return deezerAPI.searchAlbum(query);
    }

    @Cacheable(value = "album", key = "#id")
    public AlbumDTO getAlbum(long id) {
        DeezerAlbum deezerAlbum = deezerAPI.getAlbum(id);

        return DeezerMapper.toAlbum(deezerAlbum);
    }

//    public ResponseEntity<String> downloadAlbum(long id) {
//        return deezerAPI.downloadAlbum(id);
//    }
}
