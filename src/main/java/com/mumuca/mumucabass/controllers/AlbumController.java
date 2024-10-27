package com.mumuca.mumucabass.controllers;

import com.mumuca.mumucabass.apis.deezer.data.DeezerAlbum;
import com.mumuca.mumucabass.apis.deezer.data.DeezerAlbumSearch;
import com.mumuca.mumucabass.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping("/search")
    public DeezerAlbumSearch search(@RequestParam("query") String query) {
        return albumService.search(query);
    }

    @GetMapping("/{id}")
    public DeezerAlbum getAlbum(@PathVariable("id") long id) {
        return albumService.getAlbum(id);
    }

//    @GetMapping("/{id}/download")
//    public ResponseEntity<String> downloadAlbum(@PathVariable("id") long id) {
//        return albumService.downloadAlbum(id);
//    }

}
