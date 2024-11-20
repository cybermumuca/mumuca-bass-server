package com.mumuca.mumucabass.mapper;

import com.mumuca.mumucabass.api.deezer.data.DeezerArtist;
import com.mumuca.mumucabass.api.deezer.data.DeezerTrack;
import com.mumuca.mumucabass.dto.response.ArtistDTO;
import com.mumuca.mumucabass.dto.response.TrackDTO;

public class DeezerMapper {
    public static TrackDTO toTrack(DeezerTrack deezerTrack) {
        long id = deezerTrack.id();
        String title = deezerTrack.title();
        boolean isExplicit = deezerTrack.explicitLyrics();
        int duration = deezerTrack.duration();
        Float bpm = deezerTrack.bpm();

        var album = new TrackDTO.Album(
                deezerTrack.album().id(),
                deezerTrack.album().title(),
                deezerTrack.album().cover(),
                deezerTrack.album().coverSmall(),
                deezerTrack.album().coverMedium(),
                deezerTrack.album().coverBig(),
                deezerTrack.album().coverXl(),
                deezerTrack.album().releaseDate()
        );

        var artists = deezerTrack.interpreters()
                        .stream()
                        .map(artist -> new TrackDTO.Artist(
                                artist.id(),
                                artist.name(),
                                artist.picture(),
                                artist.pictureSmall(),
                                artist.pictureMedium(),
                                artist.pictureBig(),
                                artist.pictureXl()
                        ))
                        .toList();

        return new TrackDTO(
                id,
                title,
                isExplicit,
                duration,
                bpm,
                album,
                artists
        );
    }

    public static ArtistDTO toArtist(DeezerArtist deezerArtist) {
        long id = deezerArtist.id();
        String name = deezerArtist.name();
        String picture = deezerArtist.picture();
        String pictureSmall = deezerArtist.pictureSmall();
        String pictureMedium = deezerArtist.pictureMedium();
        String pictureBig = deezerArtist.pictureBig();
        String pictureXl = deezerArtist.pictureXl();

        return new ArtistDTO(
                id,
                name,
                picture,
                pictureSmall,
                pictureMedium,
                pictureBig,
                pictureXl
        );
    }
}
