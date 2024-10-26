package com.mumuca.mumufy.controllers;

import com.mumuca.mumufy.apis.deezer.data.DeezerArtist;
import com.mumuca.mumufy.apis.deezer.data.DeezerArtistSearch;
import com.mumuca.mumufy.apis.deezer.data.DeezerArtistTopTracks;
import com.mumuca.mumufy.services.ArtistService;
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
