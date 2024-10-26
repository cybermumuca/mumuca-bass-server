package com.mumuca.mumufy.apis.deezer;

import com.mumuca.mumufy.apis.deezer.data.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "deezerClient", url = "https://api.deezer.com")
public interface DeezerAPI {
    @GetMapping("/search")
    DeezerTrackSearch searchTrack(@RequestParam("q") String query);

    @GetMapping("/track/{id}")
    DeezerTrack getTrack(@PathVariable("id") long id);

    @GetMapping("/search/album")
    DeezerAlbumSearch searchAlbum(@RequestParam("q") String query);

    @GetMapping("/album/{id}")
    DeezerAlbum getAlbum(@PathVariable("id") long id);

    @GetMapping("/search/artist")
    DeezerArtistSearch searchArtist(@RequestParam("q") String query);

    @GetMapping("/artist/{id}")
    DeezerArtist getArtist(@PathVariable("id") long id);

    @GetMapping("/artist/{id}/top")
    DeezerArtistTopTracks getArtistTopTracks(@PathVariable("id") long id);
}
