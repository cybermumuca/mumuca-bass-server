package com.mumuca.mumucabass.controller;

import com.mumuca.mumucabass.api.deezer.data.DeezerArtistSearch;
import com.mumuca.mumucabass.dto.response.ArtistDTO;
import com.mumuca.mumucabass.dto.response.TopTrackDTO;
import com.mumuca.mumucabass.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artists")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/search")
    public DeezerArtistSearch search(@RequestParam("query") String query) {
        return artistService.search(query);
    }

    @GetMapping("/{id}")
    public ArtistDTO getArtist(@PathVariable("id") long id) {
        return artistService.getArtist(id);
    }

    @GetMapping("/{id}/top-tracks")
    public List<TopTrackDTO> getTopTracks(@PathVariable("id") long id) {
        return artistService.getTopTracks(id);
    }
}
