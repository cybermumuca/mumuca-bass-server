package com.mumuca.mumucabass.controller;

import com.mumuca.mumucabass.api.deezer.data.DeezerArtist;
import com.mumuca.mumucabass.api.deezer.data.DeezerArtistSearch;
import com.mumuca.mumucabass.api.deezer.data.DeezerArtistTopTracks;
import com.mumuca.mumucabass.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/search")
    public DeezerArtistSearch search(@RequestParam("query") String query) {
        return artistService.search(query);
    }

    @GetMapping("/{id}")
    public DeezerArtist getArtist(@PathVariable("id") long id) {
        return artistService.getArtist(id);
    }

    @GetMapping("/{id}/top-tracks")
    public DeezerArtistTopTracks getTopTracks(@PathVariable("id") long id) {
        return artistService.getTopTracks(id);
    }
}
