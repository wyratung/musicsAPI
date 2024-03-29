package com.dev.music.musicAPI.dto.converter;

import com.dev.music.musicAPI.dto.response.ArtistResponseType;
import com.dev.music.musicAPI.dto.response.ArtistSongResponseType;
import com.dev.music.musicAPI.dto.response.SongResponseType;
import com.dev.music.musicAPI.entities.ArtistSongId;
import com.dev.music.musicAPI.entities.ArtistSongs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtistSongConverter {
    @Autowired
    ArtistConverter artistConverter;
    @Autowired
    SongConverter songConverter;

    public ArtistSongResponseType convertToDTO(ArtistSongs artistSongs) {
        ArtistSongResponseType artistSongResponseType = new ArtistSongResponseType();
        ArtistSongId artistSongId = artistSongs.getArtistSongId();
        System.out.println("artistSongId" + artistSongId);
        if (null != artistSongId.getArtists()) {
            System.out.println("artists != null");
            artistSongResponseType.setArtists(artistConverter.convertToDTO(artistSongId.getArtists()));
        }
        if (null != artistSongId.getSongs()) {
            System.out.println("songs != null");
            artistSongResponseType.setSongs(songConverter.convertToDTO(artistSongId.getSongs()));
        }
        return artistSongResponseType;
    }

    public ArtistSongs convertToEntity(ArtistSongResponseType artistSongResponseType) {
        ArtistSongs artistSongs = new ArtistSongs();
        ArtistSongId artistSongId = new ArtistSongId();
        ArtistResponseType artists = artistSongResponseType.getArtists();
        SongResponseType song = artistSongResponseType.getSongs();
        if (null != artists) {
            artistSongId.setArtists(artistConverter.convertToEntity(artists));
        }
        if (null != song) {
            artistSongId.setSongs(songConverter.convertToEntity(song));
        }
        artistSongs.setArtistSongId(artistSongId);
        return artistSongs;
    }
}
