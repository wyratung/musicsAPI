package com.dev.music.musicAPI.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AlbumSongId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "songs_id")
    private Songs songs;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "albums_id")
    private Albums albums;

}