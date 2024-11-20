package com.mumuca.mumucabass.mapper;

import com.mumuca.mumucabass.api.deezer.data.DeezerAlbum;
import com.mumuca.mumucabass.api.deezer.data.DeezerArtist;
import com.mumuca.mumucabass.api.deezer.data.DeezerTrack;
import com.mumuca.mumucabass.dto.response.AlbumDTO;
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

    public static AlbumDTO toAlbum(DeezerAlbum deezerAlbum) {
        long id = deezerAlbum.id();
        String title = deezerAlbum.title();
        String cover = deezerAlbum.cover();
        String coverSmall = deezerAlbum.coverSmall();
        String coverMedium = deezerAlbum.coverMedium();
        String coverBig = deezerAlbum.coverBig();
        String coverXl = deezerAlbum.coverXl();
        String releaseDate = deezerAlbum.releaseDate();
        boolean isExplicit = deezerAlbum.explicitLyrics();
        int duration = deezerAlbum.duration();
        String type = deezerAlbum.recordType();
        int numberOfTracks = deezerAlbum.trackLength();

        var artist = new AlbumDTO.Artist(
                deezerAlbum.artist().id(),
                deezerAlbum.artist().name(),
                deezerAlbum.artist().picture(),
                deezerAlbum.artist().pictureSmall(),
                deezerAlbum.artist().pictureMedium(),
                deezerAlbum.artist().pictureBig(),
                deezerAlbum.artist().pictureXl()
        );


        var tracks = deezerAlbum.tracks().data().stream()
                .map(track -> new AlbumDTO.Track(
                        track.id(),
                        track.title(),
                        track.duration(),
                        track.explicitLyrics()
                ))
                .toList();

        return new AlbumDTO(
                id,
                title,
                cover,
                coverSmall,
                coverMedium,
                coverBig,
                coverXl,
                releaseDate,
                isExplicit,
                duration,
                type,
                numberOfTracks,
                artist,
                tracks
        );
    }
}
