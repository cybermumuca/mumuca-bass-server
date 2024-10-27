package com.mumuca.mumucabass.controllers;

import com.mumuca.mumucabass.apis.deezer.data.DeezerTrack;
import com.mumuca.mumucabass.apis.deezer.data.DeezerTrackSearch;
import com.mumuca.mumucabass.services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tracks")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @GetMapping("/search")
    public DeezerTrackSearch search(@RequestParam("query") String query) {
        return trackService.search(query);
    }

    @GetMapping("/{id}")
    public DeezerTrack getTrack(@PathVariable("id") long id) {
        return trackService.getTrack(id);
    }

    @GetMapping("{id}/stream")
    public String streamTrack(@PathVariable("id") long id) {
        return trackService.streamTrack(id);
    }
}