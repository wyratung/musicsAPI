package com.dev.music.musicAPI.entities;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Table(name = "artist_albums")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistAlbums {
    @EmbeddedId
    private ArtistAlbumId artistAlbumId;
}
