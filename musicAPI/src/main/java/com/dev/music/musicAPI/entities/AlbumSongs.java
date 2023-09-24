package com.dev.music.musicAPI.entities;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Table(name = "album_songs")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumSongs {
    @EmbeddedId
    private AlbumSongId albumSongId;
}
