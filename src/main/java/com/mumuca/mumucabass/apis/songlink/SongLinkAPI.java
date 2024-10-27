package com.mumuca.mumucabass.apis.songlink;

import com.mumuca.mumucabass.apis.songlink.data.SongLinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "songLinkClient", url = "https://api.song.link/v1-alpha.1")
public interface SongLinkAPI {
    @GetMapping("/links")
    SongLinkResponse getLinks(@RequestParam("url") String url);
}
