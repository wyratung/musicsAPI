package com.dev.music.musicAPI.dto.converter;

import com.dev.music.musicAPI.dto.response.AlbumSongResponseType;
import com.dev.music.musicAPI.entities.AlbumSongId;
import com.dev.music.musicAPI.entities.AlbumSongs;
import com.dev.music.musicAPI.entities.Albums;
import com.dev.music.musicAPI.entities.Songs;
import org.springframework.beans.factory.annotation.Autowired;

public class AlbumSongConverter {
    private final SongConverter songConverter;
    private final AlbumConverter albumConverter;

    @Autowired
    public AlbumSongConverter(SongConverter songConverter, AlbumConverter albumConverter) {
        this.songConverter = songConverter;
        this.albumConverter = albumConverter;
    }

    public AlbumSongResponseType convertToDTO(AlbumSongs albumSongs) {
        AlbumSongResponseType albumSongResponseType = new AlbumSongResponseType();
        Songs songs = albumSongs.getAlbumSongId().getSongs();
        Albums albums = albumSongs.getAlbumSongId().getAlbums();
        if (null != songs) {
            albumSongResponseType.setSongs(songConverter.convertToDTO(songs));
        }
        if (null != albums) {
            albumSongResponseType.setAlbums(albumConverter.convertToDTO(albums));
        }
        return albumSongResponseType;
    }

    public AlbumSongs convertToEntity(AlbumSongResponseType albumSongResponseType) {
        AlbumSongs albumSongs = new AlbumSongs();
        AlbumSongId albumSongId = new AlbumSongId();
        albumSongId.setAlbums(albumConverter.convertToEntity(albumSongResponseType.getAlbums()));
        albumSongId.setSongs(songConverter.convertToEntity(albumSongResponseType.getSongs()));
        albumSongs.setAlbumSongId(albumSongId);
        return albumSongs;
    }
}
