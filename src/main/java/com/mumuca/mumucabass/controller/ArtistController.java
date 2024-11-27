package com.mumuca.mumucabass.controller;

import com.mumuca.mumucabass.api.deezer.data.DeezerArtistSearch;
import com.mumuca.mumucabass.dto.response.ArtistDTO;
import com.mumuca.mumucabass.dto.response.TopTrackDTO;
import com.mumuca.mumucabass.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/v1/artists/search")
    public DeezerArtistSearch search(@RequestParam("query") String query) {
        return artistService.search(query);
    }

    @GetMapping("/v1/artists/{id}")
    public ArtistDTO getArtist(@PathVariable("id") long id) {
        return artistService.getArtist(id);
    }

    @GetMapping("/v1/artists/{id}/top-tracks")
    public List<TopTrackDTO> getTopTracks(@PathVariable("id") long id) {
        return artistService.getTopTracks(id);
    }
}
