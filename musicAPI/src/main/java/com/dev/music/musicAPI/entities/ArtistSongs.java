package com.dev.music.musicAPI.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "artist_songs")
@Entity

public class ArtistSongs {
    @EmbeddedId
    private ArtistSongId artistSongId;
}
