package com.mumuca.mumufy.controllers;

import com.mumuca.mumufy.apis.deezer.data.DeezerTrack;
import com.mumuca.mumufy.apis.deezer.data.DeezerTrackSearch;
import com.mumuca.mumufy.services.TrackService;
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
