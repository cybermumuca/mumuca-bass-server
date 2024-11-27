package com.mumuca.mumucabass.controller;

import com.mumuca.mumucabass.api.deezer.data.DeezerAlbumSearch;
import com.mumuca.mumucabass.dto.response.AlbumDTO;
import com.mumuca.mumucabass.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/v1/albums/search")
    public DeezerAlbumSearch search(@RequestParam("query") String query) {
        return albumService.search(query);
    }

    @GetMapping("/v1/albums/{id}")
    public AlbumDTO getAlbum(@PathVariable("id") long id) {
        return albumService.getAlbum(id);
    }

//    @GetMapping("/{id}/download")
//    public ResponseEntity<String> downloadAlbum(@PathVariable("id") long id) {
//        return albumService.downloadAlbum(id);
//    }

}
